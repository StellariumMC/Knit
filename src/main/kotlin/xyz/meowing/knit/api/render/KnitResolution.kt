package xyz.meowing.knit.api.render

import xyz.meowing.knit.api.KnitClient.client

/**
 * @author: Deftu
 */
object KnitResolution {
    object Window {
        val width: Int
            get() {
                return client.window.width
            }

        val height: Int
            get() {
                return client.window.height
            }
    }


    object Viewport {
        val width: Int
            get() {
                return client.window.framebufferWidth
            }

        val height: Int
            get() {
                return client.window.framebufferHeight
            }
    }

    object Scaled {
        val width: Int
            get() {
                return client.window.scaledWidth
            }

        val height: Int
            get() {
                return client.window.scaledHeight
            }

        val scaleFactor: Double
            get() {
                return client.window.scaleFactor
                    //#if MC >= 1.21.6
                    //$$ .toDouble()
                    //#endif
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

    @JvmStatic
    val window: net.minecraft.client.util.Window get() = client.window

    @JvmStatic
    val windowHandle: Long get() = client.window.handle
}