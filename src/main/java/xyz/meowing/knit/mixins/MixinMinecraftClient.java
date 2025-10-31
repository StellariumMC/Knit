package xyz.meowing.knit.mixins;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.meowing.knit.Knit;
import xyz.meowing.knit.internal.events.ClientEvent;
import xyz.meowing.knit.internal.events.TickEvent;

/**
 * Adapted from Fabric API's Implementation.
 */
@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "tick", at = @At("HEAD"))
    private void zen$onStartTick(CallbackInfo info) {
        Knit.getEventBus().post(new TickEvent.Client.Start());
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void zen$onEndTick(CallbackInfo info) {
        Knit.getEventBus().post(new TickEvent.Client.End());
    }

    @Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;thread:Ljava/lang/Thread;", shift = At.Shift.AFTER, ordinal = 0), method = "run")
    private void zen$onClientStart(CallbackInfo ci) {
        Knit.getEventBus().post(new ClientEvent.Start());
    }

    @Inject(at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;info(Ljava/lang/String;)V", shift = At.Shift.AFTER), method = "stop")
    private void zen$onClientStop(CallbackInfo ci) {
        Knit.getEventBus().post(new ClientEvent.Stop());
    }
}
