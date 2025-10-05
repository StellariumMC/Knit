package xyz.meowing.knit.api.render

//#if MC != 1.16.5
import xyz.meowing.knit.Knit.client

//#if MC == 1.8.9
import net.minecraft.client.gui.ScaledResolution
private typealias ScreenResolution = ScaledResolution
//#endif

object KnitResolution {
    object Window {
        val width: Int
            get() {
                //#if MC >= 1.16.5
                //$$ return client.window.width
                //#else
                return client.displayWidth
                //#endif
            }

        val height: Int
            get() {
                //#if MC >= 1.16.5
                //$$ return client.window.height
                //#else
                return client.displayHeight
                //#endif
            }
    }


    object Viewport {
        val width: Int
            get() {
                //#if MC >= 1.16.5
                //$$ return client.window.framebufferWidth
                //#else
                return client.displayWidth
                //#endif
            }

        val height: Int
            get() {
                //#if MC >= 1.16.5
                //$$ return client.window.framebufferHeight
                //#else
                return client.displayHeight
                //#endif
            }
    }

    object Scaled {
        //#if MC == 1.8.9
        private data class CachedScaledResolution(val width: Int, val height: Int, val scale: Int, val isUnicode: Boolean)

        private var cachedScaledRes: CachedScaledResolution? = null
        private var scaledRes: ScreenResolution? = null
        //#endif

        val width: Int
            get() {
                //#if MC >= 1.16.5
                //$$ return client.window.scaledWidth
                //#else
                return getScaledRes().scaledWidth
                //#endif
            }

        val height: Int
            get() {
                //#if MC >= 1.16.5
                //$$ return client.window.scaledHeight
                //#else
                return getScaledRes().scaledHeight
                //#endif
            }

        val scaleFactor: Double
            get() {
                //#if MC >= 1.16.5
                //$$ return client.window.scaleFactor
                    //#if MC >= 1.21.6
                    //$$ .toDouble()
                    //#endif
                //#else
                return getScaledRes().scaleFactor.toDouble()
                //#endif
            }

        //#if MC == 1.8.9
        private fun getScaledRes(): ScreenResolution {
            val cached = CachedScaledResolution(
                client.displayWidth,
                client.displayHeight,
                client.gameSettings.guiScale,
                client.isUnicode
            )

            if (cached != cachedScaledRes) {
                cachedScaledRes = cached
                scaledRes = ScreenResolution(client)
            }

            return scaledRes!!
        }
        //#endif
    }


    @JvmStatic
    val windowWidth: Int get() = Window.width

    @JvmStatic
    val windowHeight: Int get() = Window.height

    @JvmStatic
    val viewportWidth: Int get() = Viewport.width

    @JvmStatic
    val viewportHeight: Int get() = Viewport.height

    @JvmStatic
    val scaledWidth: Int get() = Scaled.width

    @JvmStatic
    val scaledHeight: Int get() = Scaled.height

    @JvmStatic
    val scaleFactor: Double get() = Scaled.scaleFactor

    //#if MC >= 1.16.5
    //$$ inline val window: net.minecraft.client.util.Window get() = client.window
    //$$ inline val windowHandle: Long get() = client.window.handle
    //#endif
}
//#endif