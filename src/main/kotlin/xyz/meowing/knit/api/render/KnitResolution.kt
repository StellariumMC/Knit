package xyz.meowing.knit.api.render

import xyz.meowing.knit.api.KnitClient.client
import net.minecraft.client.gui.ScaledResolution
private typealias ScreenResolution = ScaledResolution

/**
 * @author: Deftu
 */
object KnitResolution {
    object Window {
        val width: Int
            get() {
                return client.displayWidth
            }

        val height: Int
            get() {
                return client.displayHeight
            }
    }


    object Viewport {
        val width: Int
            get() {
                return client.displayWidth
            }

        val height: Int
            get() {
                return client.displayHeight
            }
    }

    object Scaled {
        private data class CachedScaledResolution(val width: Int, val height: Int, val scale: Int, val isUnicode: Boolean)

        private var cachedScaledRes: CachedScaledResolution? = null
        private var scaledRes: ScreenResolution? = null

        val width: Int
            get() {
                return getScaledRes().scaledWidth
            }

        val height: Int
            get() {
                return getScaledRes().scaledHeight
            }

        val scaleFactor: Double
            get() {
                return getScaledRes().scaleFactor.toDouble()
            }

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
}