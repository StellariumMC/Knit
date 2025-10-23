package xyz.meowing.knit.internal.events

//#if FABRIC
import net.minecraft.util.hit.HitResult
import xyz.meowing.knit.api.events.CancellableEvent
import xyz.meowing.knit.api.events.Event
import xyz.meowing.knit.api.render.world.RenderContext

sealed class WorldRenderEvent {
    class Start(val context: RenderContext) : Event()

    class AfterSetup(val context: RenderContext) : Event()

    class BeforeEntities(val context: RenderContext) : Event()

    class AfterEntities(val context: RenderContext) : Event()

    class BeforeBlockOutline(val context: RenderContext, val hitResult: HitResult?) : CancellableEvent()

    class BlockOutline(val context: RenderContext) : CancellableEvent()

    class BeforeDebugRender(val context: RenderContext) : Event()

    class AfterTranslucent(val context: RenderContext) : Event()

    class Last(val context: RenderContext) : Event()

    class End(val context: RenderContext) : Event()

    class InvalidateRenderState : Event()

    class RenderWeather(val context: RenderContext) : CancellableEvent()

    class RenderClouds(val context: RenderContext) : CancellableEvent()

    class RenderSky(val context: RenderContext) : CancellableEvent()
}
//#endif