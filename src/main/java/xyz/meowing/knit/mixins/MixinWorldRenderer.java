package xyz.meowing.knit.mixins;

//#if FABRIC
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.meowing.knit.WorldRendererAccess;
import xyz.meowing.knit.api.KnitClient;
import xyz.meowing.knit.api.render.world.RenderContext;

//#if MC >= 1.21.5
import net.minecraft.client.util.ObjectAllocator;
//#endif

//#if MC >= 1.21.9
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//$$ import net.minecraft.client.render.state.OutlineRenderState;
//$$ import net.minecraft.client.render.state.WorldRenderState;
//#endif

//#if MC >= 1.21.7
//$$ import org.joml.Vector4f;
//$$ import com.mojang.blaze3d.buffers.GpuBufferSlice;
//#endif

//#if MC <= 1.20.1
//$$ import net.minecraft.entity.Entity;
//$$ import net.minecraft.client.gl.PostEffectProcessor;
//#endif

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer implements WorldRendererAccess {
    @Shadow
    private ClientWorld world;

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private BufferBuilderStorage bufferBuilders;

    //#if MC >= 1.21.5
    @Shadow
    @Final
    private DefaultFramebufferSet framebufferSet;
    //#else
    //$$ @Shadow
    //$$ private PostEffectProcessor transparencyPostProcessor;
    //#endif

    @Unique
    private final RenderContext knit$context = new RenderContext();

    @Unique
    private boolean knit$isRendering = false;

    //#if MC <= 1.20.1
    //$$ @Unique
    //$$ private boolean knit$didRenderParticles = false;
    //#endif

    @Inject(method = "render", at = @At("HEAD"))
    private void knit$beforeRender(
            //#if MC >= 1.21.9
            //$$ ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, Matrix4f positionMatrix, Matrix4f matrix4f, Matrix4f projectionMatrix, GpuBufferSlice fogBuffer, Vector4f fogColor, boolean renderSky, CallbackInfo ci
            //#elseif MC >= 1.21.7
            //$$ ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, Matrix4f positionMatrix, Matrix4f projectionMatrix, GpuBufferSlice fog, Vector4f fogColor, boolean shouldRenderSky, CallbackInfo ci
            //#elseif MC >= 1.21.5
            ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci
            //#else
            //$$ MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci
            //#endif
    ) {
        knit$context.prepare(
                (WorldRenderer) (Object) this,
                //#if MC >= 1.21.5
                tickCounter,
                //#else
                //$$ new RenderTickCounter(tickDelta, limitTime),
                //#endif
                renderBlockOutline,
                camera,
                KnitClient.getClient().gameRenderer,
                //#if MC >= 1.21.5
                positionMatrix,
                projectionMatrix,
                //#else
                //$$ matrix4f,
                //$$ matrix4f,
                //#endif
                this.bufferBuilders.getEntityVertexConsumers(),
                MinecraftClient.isFabulousGraphicsOrBetter(),
                this.world
        );
        knit$isRendering = true;
        //#if MC <= 1.20.1
        //$$ knit$didRenderParticles = false;
        //#endif
        knit$context.postStart();
    }

    //#if MC >= 1.21.9
    //$$ @Inject(method = "setupFrustum", at = @At("RETURN"))
    //$$ private void zen$onSetupFrustum(Matrix4f posMatrix, Matrix4f projMatrix, Vec3d pos, CallbackInfoReturnable<Frustum> cir) {
    //$$     knit$context.setFrustum(cir.getReturnValue());
    //$$ }
    //#else
    @Inject(method = "setupTerrain", at = @At("RETURN"))
    private void knit$afterTerrainSetup(Camera camera, Frustum frustum, boolean hasForcedFrustum, boolean spectator, CallbackInfo ci) {
        knit$context.setFrustum(frustum);
        //#if MC <= 1.20.1
        //$$ knit$context.postAfterSetup();
        //#endif
    }
    //#endif

    //#if MC >= 1.21.5
    @Inject(
            method = "method_62214",
            at = @At(
                    value = "INVOKE_STRING",
                    target = "Lnet/minecraft/util/profiler/Profiler;push(Ljava/lang/String;)V",
                    args = "ldc=terrain",
                    shift = At.Shift.AFTER
            )
    )
    private void knit$beforeTerrainSolid(CallbackInfo ci) {
        knit$context.postAfterSetup();
    }
    //#endif

    //#if MC >= 1.21.9
    //$$ @Inject(
    //$$         method = "render",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/client/render/WorldRenderer;fillEntityRenderStates(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/Frustum;Lnet/minecraft/client/render/RenderTickCounter;Lnet/minecraft/client/render/state/WorldRenderState;)V",
    //$$                 shift = At.Shift.AFTER
    //$$         )
    //$$ )
    //#elseif MC >= 1.21.5
    @Inject(
            method = "method_62214",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;renderEntities(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/RenderTickCounter;Ljava/util/List;)V"
            )
    )
    //#else
    //$$ @Inject(
    //$$         method = "render",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/client/render/WorldRenderer;renderLayer(Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack;DDDLorg/joml/Matrix4f;)V",
    //$$                 ordinal = 2,
    //$$                 shift = At.Shift.AFTER
    //$$         )
    //$$ )
    //#endif
    private void knit$afterTerrainSolid(CallbackInfo ci) {
        knit$context.postBeforeEntities();
    }

    //#if MC >= 1.21.5
    @ModifyExpressionValue(method = "method_62214", at = @At(value = "NEW", target = "net/minecraft/client/util/math/MatrixStack"))
    private MatrixStack knit$setMatrixStack(MatrixStack matrixStack) {
        knit$context.setMatrixStack(matrixStack);
        return matrixStack;
    }
    //#endif

    //#if MC >= 1.21.9
    //$$ @Inject(method = "pushEntityRenders", at = @At("HEAD"))
    //#elseif MC >= 1.21.5
    @Inject(method = "method_62214", at = @At(value = "CONSTANT", args = "stringValue=blockentities", ordinal = 0))
    //#else
    //$$ @Inject(method = "render", at = @At(value = "CONSTANT", args = "stringValue=blockentities", ordinal = 0))
    //#endif
    private void knit$afterEntities(CallbackInfo ci) {
        knit$context.postAfterEntities();
    }

    //#if MC >= 1.21.5
    @Inject(method = "renderTargetBlockOutline", at = @At("HEAD"))
    //#else
    //$$ @Inject(
    //$$         method = "render",
    //$$         at = @At(
    //$$                 value = "FIELD",
    //$$                 target = "Lnet/minecraft/client/MinecraftClient;crosshairTarget:Lnet/minecraft/util/hit/HitResult;",
    //$$                 shift = At.Shift.AFTER,
    //$$                 ordinal = 1
    //$$         )
    //$$ )
    //#endif

    //#if MC >= 1.21.9
    //$$ private void knit$beforeRenderOutline(VertexConsumerProvider.Immediate immediate, MatrixStack matrices, boolean translucent, WorldRenderState renderStates, CallbackInfo ci) {
    //#elseif MC >= 1.21.5
    private void knit$beforeRenderOutline(Camera camera, VertexConsumerProvider.Immediate vertexConsumers, MatrixStack matrices, boolean translucent, CallbackInfo ci) {
        //#else
        //$$ private void knit$beforeRenderOutline(CallbackInfo ci) {
        //#endif

        //#if MC >= 1.21.5
        knit$context.setTranslucentBlockOutline(translucent);
        //#endif
        knit$context.postBeforeBlockOutline(this.client.crosshairTarget);
    }

    //#if MC >= 1.21.9
    //$$ @Inject(
    //$$         method = "renderTargetBlockOutline",
    //$$         at = @At("HEAD"),
    //$$         cancellable = true
    //$$ )
    //$$ private void knit$onDrawBlockOutline(
    //$$         VertexConsumerProvider.Immediate immediate,
    //$$         MatrixStack matrices,
    //$$         boolean renderBlockOutline,
    //$$         WorldRenderState renderStates,
    //$$         CallbackInfo ci
    //$$ ) {
    //$$     if (!knit$context.shouldRenderBlockOutline()) {
    //$$         ci.cancel();
    //$$         return;
    //$$     }
    //$$
    //$$     OutlineRenderState outlineRenderState = renderStates.outlineRenderState;
    //$$     if (outlineRenderState != null) {
    //$$         Vec3d cameraPos = renderStates.cameraRenderState.pos;
    //$$         BlockPos blockPos = outlineRenderState.pos();
    //$$         BlockState blockState = this.world.getBlockState(blockPos);
    //$$
    //$$         knit$context.prepareBlockOutline(
    //$$                 this.client.gameRenderer.getCamera().getFocusedEntity(),
    //$$                 cameraPos.x,
    //$$                 cameraPos.y,
    //$$                 cameraPos.z,
    //$$                 blockPos,
    //$$                 blockState
    //$$         );
    //$$
    //$$         if (!knit$context.postBlockOutline()) {
    //$$             immediate.drawCurrentLayer();
    //$$             ci.cancel();
    //$$         }
    //$$     } else {
    //$$         ci.cancel();
    //$$     }
    //$$ }
    //#elseif MC >= 1.21.5
    @Inject(
            method = "renderTargetBlockOutline",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getHighContrastBlockOutline()Lnet/minecraft/client/option/SimpleOption;"),
            cancellable = true
    )
    private void knit$onDrawBlockOutline(
            Camera camera,
            VertexConsumerProvider.Immediate vertexConsumers,
            MatrixStack matrices,
            boolean translucent,
            CallbackInfo ci,
            @Local BlockPos blockPos,
            @Local BlockState blockState,
            @Local Vec3d cameraPos
    ) {
        if (!knit$context.shouldRenderBlockOutline()) {
            ci.cancel();
            return;
        }

        knit$context.prepareBlockOutline(camera.getFocusedEntity(), cameraPos.x, cameraPos.y, cameraPos.z, blockPos, blockState);

        if (!knit$context.postBlockOutline()) {
            vertexConsumers.drawCurrentLayer();
            ci.cancel();
        }
    }
    //#else
    //$$ @Inject(method = "drawBlockOutline", at = @At("HEAD"), cancellable = true)
    //$$ private void knit$onDrawBlockOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, Entity entity, double cameraX, double cameraY, double cameraZ, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
    //$$     if (!knit$context.shouldRenderBlockOutline()) {
    //$$         ci.cancel();
    //$$     } else {
    //$$         knit$context.prepareBlockOutline(entity, cameraX, cameraY, cameraZ, blockPos, blockState);
    //$$
    //$$         if (!knit$context.postBlockOutline()) {
    //$$             ci.cancel();
    //$$         }
    //$$
    //$$         knit$context.consumers().getBuffer(RenderLayer.getLines());
    //$$     }
    //$$ }
    //#endif

    //#if MC >= 1.21.5
    @Inject(
            method = "method_62214",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 1.21.9
                    //$$ target = "Lnet/minecraft/client/render/debug/DebugRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/Frustum;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;DDDZ)V",
                    //#else
                    target = "Lnet/minecraft/client/render/debug/DebugRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/Frustum;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;DDD)V",
                    //#endif
                    ordinal = 0
            )
    )
    //#else
    //$$ @Inject(
    //$$         method = "render",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/client/render/debug/DebugRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;DDD)V",
    //$$                 ordinal = 0
    //$$         )
    //$$ )
    //#endif
    private void knit$beforeDebugRender(CallbackInfo ci) {
        knit$context.postBeforeDebugRender();
    }

    //#if MC >= 1.21.5
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getCloudRenderModeValue()Lnet/minecraft/client/option/CloudRenderMode;"))
    private void knit$beforeClouds(CallbackInfo ci, @Local FrameGraphBuilder frameGraphBuilder) {
        FramePass afterTranslucentPass = frameGraphBuilder.createPass("afterTranslucent");
        this.framebufferSet.mainFramebuffer = afterTranslucentPass.transfer(this.framebufferSet.mainFramebuffer);
        afterTranslucentPass.setRenderer(knit$context::postAfterTranslucent);
    }

    @Inject(method = "method_62214", at = @At("RETURN"))
    private void knit$onFinishWritingFramebuffer(CallbackInfo ci) {
        knit$context.postLast();
    }
    //#else
    //$$ @Inject(
    //$$         method = "render",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/client/particle/ParticleManager;renderParticles(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/client/render/Camera;F)V"
    //$$         )
    //$$ )
    //$$ private void knit$onRenderParticles(CallbackInfo ci) {
    //$$     knit$didRenderParticles = true;
    //$$ }
    //$$
    //$$ @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V"))
    //$$ private void knit$beforeClouds(CallbackInfo ci) {
    //$$     if (knit$didRenderParticles) {
    //$$         knit$didRenderParticles = false;
    //$$         knit$context.postAfterTranslucent();
    //$$     }
    //$$ }
    //$$
    //$$ @Inject(
    //$$         method = "render",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 target = "Lnet/minecraft/client/render/WorldRenderer;renderChunkDebugInfo(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/Camera;)V"
    //$$         )
    //$$ )
    //$$ private void knit$onChunkDebugRender(CallbackInfo ci) {
    //$$     knit$context.postLast();
    //$$ }
    //#endif

    @Inject(method = "render", at = @At("RETURN"))
    private void knit$afterRender(CallbackInfo ci) {
        knit$context.postEnd();
        knit$isRendering = false;
    }

    @Inject(method = "reload()V", at = @At("HEAD"))
    private void knit$onReload(CallbackInfo ci) {
        knit$context.postInvalidateRenderState();
    }

    //#if MC >= 1.21.9
    //$$ @Inject(at = @At("HEAD"), method = "renderWeather", cancellable = true)
    //$$ private void knit$renderWeather(FrameGraphBuilder frameGraphBuilder, Vec3d cameraPos, GpuBufferSlice fogBuffer, CallbackInfo ci) {
    //#elseif MC >= 1.21.7
    //$$ @Inject(at = @At("HEAD"), method = "renderWeather", cancellable = true)
    //$$ private void knit$renderWeather(FrameGraphBuilder frameGraphBuilder, Vec3d cameraPos, float tickProgress, GpuBufferSlice fog, CallbackInfo ci) {
    //#elseif MC >= 1.21.5
    @Inject(at = @At("HEAD"), method = "renderWeather", cancellable = true)
    private void knit$renderWeather(FrameGraphBuilder frameGraphBuilder, Vec3d cameraPos, float tickProgress, Fog fog, CallbackInfo ci) {
        //#else
        //$$ @Inject(at = @At("HEAD"), method = "renderWeather", cancellable = true)
        //$$ private void knit$renderWeather(LightmapTextureManager manager, float tickDelta, double x, double y, double z, CallbackInfo ci) {
        //#endif
        if (knit$context.postRenderWeather()) {
            ci.cancel();
        }
    }

    //#if MC >= 1.21.5
    @Inject(at = @At("HEAD"), method = "renderClouds", cancellable = true)
    private void knit$renderClouds(FrameGraphBuilder frameGraphBuilder, CloudRenderMode cloudRenderMode, Vec3d vec3d, float f, int i, float g, CallbackInfo ci) {
        //#else
        //$$ @Inject(at = @At("HEAD"), method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FDDD)V", cancellable = true)
        //$$ private void knit$renderClouds(MatrixStack matrices, Matrix4f matrix4f, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
        //#endif
        if (knit$context.postRenderClouds()) {
            ci.cancel();
        }
    }

    //#if MC >= 1.21.9
    //$$ @Inject(at = @At(value = "HEAD"), method = "renderSky", cancellable = true)
    //$$ private void knit$renderSky(FrameGraphBuilder frameGraphBuilder, Camera camera, GpuBufferSlice fogBuffer, CallbackInfo ci) {
    //#elseif MC >= 1.21.7
    //$$ @Inject(at = @At(value = "HEAD"), method = "renderSky", cancellable = true)
    //$$ private void knit$renderSky(FrameGraphBuilder frameGraphBuilder, Camera camera, float tickProgress, GpuBufferSlice fog, CallbackInfo ci) {
    //#elseif MC >= 1.21.5
    @Inject(at = @At(value = "HEAD"), method = "renderSky", cancellable = true)
    private void knit$renderSky(FrameGraphBuilder frameGraphBuilder, Camera camera, float tickProgress, Fog fog, CallbackInfo ci) {
        //#else
        //$$ @Inject(at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V", shift = At.Shift.AFTER, ordinal = 0), method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", cancellable = true)
        //$$ private void knit$renderSky(MatrixStack matrices, Matrix4f matrix4f, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {
        //#endif
        if (knit$context.postRenderSky()) {
            ci.cancel();
        }
    }

    @Override
    public RenderContext knit$getContext() {
        if (!knit$isRendering) throw new IllegalStateException("WorldRenderer is not rendering");
        return knit$context;
    }
}
//#else
//$$ @org.spongepowered.asm.mixin.Mixin(net.minecraft.client.renderer.LevelRenderer.class)
//$$ public class MixinWorldRenderer {
//$$    // No support for NeoForge/Forge yet.
//$$ }
//#endif