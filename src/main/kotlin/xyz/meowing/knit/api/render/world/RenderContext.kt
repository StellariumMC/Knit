@file:Suppress("UNUSED")

//#if FABRIC
package xyz.meowing.knit.api.render.world

import net.minecraft.block.BlockState
import net.minecraft.client.render.*
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import org.joml.Matrix4f
import xyz.meowing.knit.Knit.EventBus
import xyz.meowing.knit.internal.events.WorldRenderEvent

class RenderContext {
    private lateinit var worldRenderer: WorldRenderer
    private lateinit var tickCounter: RenderTickCounter
    private var blockOutlines: Boolean = false
    private lateinit var camera: Camera
    private lateinit var gameRenderer: GameRenderer
    private lateinit var positionMatrix: Matrix4f
    private lateinit var projectionMatrix: Matrix4f
    private lateinit var consumers: VertexConsumerProvider
    private var advancedTranslucency: Boolean = false
    private lateinit var world: ClientWorld

    private var frustum: Frustum? = null
    private var matrixStack: MatrixStack? = null
    private var translucentBlockOutline: Boolean = false

    private var entity: Entity? = null
    private var cameraX: Double = 0.0
    private var cameraY: Double = 0.0
    private var cameraZ: Double = 0.0
    private var blockPos: BlockPos? = null
    private var blockState: BlockState? = null

    private var renderBlockOutline: Boolean = true

    fun prepare(
        worldRenderer: WorldRenderer,
        tickCounter: RenderTickCounter,
        blockOutlines: Boolean,
        camera: Camera,
        gameRenderer: GameRenderer,
        positionMatrix: Matrix4f,
        projectionMatrix: Matrix4f,
        consumers: VertexConsumerProvider,
        advancedTranslucency: Boolean,
        world: ClientWorld
    ) {
        this.worldRenderer = worldRenderer
        this.tickCounter = tickCounter
        this.blockOutlines = blockOutlines
        this.camera = camera
        this.gameRenderer = gameRenderer
        this.positionMatrix = positionMatrix
        this.projectionMatrix = projectionMatrix
        this.consumers = consumers
        this.advancedTranslucency = advancedTranslucency
        this.world = world

        frustum = null
        matrixStack = null
    }

    fun setFrustum(frustum: Frustum) {
        this.frustum = frustum
    }

    fun setMatrixStack(matrixStack: MatrixStack) {
        this.matrixStack = matrixStack
    }

    fun setTranslucentBlockOutline(translucent: Boolean) {
        this.translucentBlockOutline = translucent
    }

    fun prepareBlockOutline(
        entity: Entity?,
        cameraX: Double,
        cameraY: Double,
        cameraZ: Double,
        blockPos: BlockPos,
        blockState: BlockState
    ) {
        this.entity = entity
        this.cameraX = cameraX
        this.cameraY = cameraY
        this.cameraZ = cameraZ
        this.blockPos = blockPos
        this.blockState = blockState
    }

    fun shouldRenderBlockOutline() = renderBlockOutline

    fun postStart() {
        EventBus.post(WorldRenderEvent.Start(this))
    }

    fun postAfterSetup() {
        EventBus.post(WorldRenderEvent.AfterSetup(this))
    }

    fun postBeforeEntities() {
        EventBus.post(WorldRenderEvent.BeforeEntities(this))
    }

    fun postAfterEntities() {
        EventBus.post(WorldRenderEvent.AfterEntities(this))
    }

    fun postBeforeBlockOutline(hitResult: HitResult?) {
        renderBlockOutline = true
        val event = WorldRenderEvent.BeforeBlockOutline(this, hitResult)
        EventBus.post(event)
        if (event.cancelled) {
            renderBlockOutline = false
        }
    }

    fun postBlockOutline(): Boolean {
        val event = WorldRenderEvent.BlockOutline(this)
        EventBus.post(event)
        return !event.cancelled
    }

    fun postBeforeDebugRender() {
        EventBus.post(WorldRenderEvent.BeforeDebugRender(this))
    }

    fun postAfterTranslucent() {
        EventBus.post(WorldRenderEvent.AfterTranslucent(this))
    }

    fun postLast() {
        EventBus.post(WorldRenderEvent.Last(this))
    }

    fun postEnd() {
        EventBus.post(WorldRenderEvent.End(this))
    }

    fun postInvalidateRenderState() {
        EventBus.post(WorldRenderEvent.InvalidateRenderState())
    }

    fun postRenderWeather(): Boolean {
        val event = WorldRenderEvent.RenderWeather(this)
        EventBus.post(event)
        return event.cancelled
    }

    fun postRenderClouds(): Boolean {
        val event = WorldRenderEvent.RenderClouds(this)
        EventBus.post(event)
        return event.cancelled
    }

    fun postRenderSky(): Boolean {
        val event = WorldRenderEvent.RenderSky(this)
        EventBus.post(event)
        return event.cancelled
    }

    fun worldRenderer() = worldRenderer
    fun tickCounter() = tickCounter
    fun blockOutlines() = blockOutlines
    fun camera() = camera
    fun gameRenderer() = gameRenderer
    fun positionMatrix() = positionMatrix
    fun projectionMatrix() = projectionMatrix
    fun world() = world
    fun advancedTranslucency() = advancedTranslucency
    fun consumers() = consumers
    fun frustum() = frustum
    fun matrixStack() = matrixStack
    fun translucentBlockOutline() = translucentBlockOutline
    fun entity() = entity
    fun cameraX() = cameraX
    fun cameraY() = cameraY
    fun cameraZ() = cameraZ
    fun blockPos() = blockPos
    fun blockState() = blockState
}
//#endif