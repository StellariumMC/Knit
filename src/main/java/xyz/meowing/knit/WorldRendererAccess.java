package xyz.meowing.knit;

//#if FABRIC
import xyz.meowing.knit.api.render.world.RenderContext;

public interface WorldRendererAccess {
    RenderContext knit$getContext();
}
//#else
//$$ public interface WorldRendererAccess {
//$$    // No support for NeoForge/Forge yet.
//$$ }
//#endif