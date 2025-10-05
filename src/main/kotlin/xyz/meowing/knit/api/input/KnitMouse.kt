package xyz.meowing.knit.api.input

//#if MC != 1.16.5
import xyz.meowing.knit.api.render.KnitResolution
import kotlin.math.max

//#if MC >= 1.16.5
//$$ import xyz.meowing.knit.api.render.KnitResolution.windowHandle
//$$ import org.lwjgl.glfw.GLFW
//$$ import xyz.meowing.knit.Knit.client
//#elseif MC <= 1.12.2
import org.lwjgl.input.Mouse
//#endif

object KnitMouse {
    object Raw {
        inline val x: Double
            get() {
                //#if MC >= 1.14
                //$$ return client.mouse.x
                //#else
                return Mouse.getX().toDouble()
                //#endif
            }

        inline val y: Double
            get() {
                //#if MC >= 1.14
                //$$ return client.mouse.y
                //#else
                return KnitResolution.windowHeight - Mouse.getY().toDouble() - 1
                //#endif
            }
    }

    object Scaled {
        inline val x: Double
            get() = Raw.x * KnitResolution.scaledWidth / max(1, KnitResolution.windowWidth)
        inline val y: Double
            get() = Raw.y * KnitResolution.scaledWidth / max(1, KnitResolution.windowWidth)
    }

    var isCursorGrabbed: Boolean
        get() {
            //#if MC >= 1.16.5
            //$$ return client.mouse.isCursorLocked
            //#else
            return Mouse.isGrabbed()
            //#endif
        }
        set(value) {
            //#if MC >= 1.16.5
            //$$ if (value) client.mouse.lockCursor() else client.mouse.unlockCursor()
            //#else
            Mouse.setGrabbed(value)
            //#endif
        }

    fun isMouseButton(code: Int): Boolean {
        //#if MC >= 1.16.5
        //$$ return code in GLFW.GLFW_MOUSE_BUTTON_1..GLFW.GLFW_MOUSE_BUTTON_8
        //#else
        return code in 0..8
        //#endif
    }

    fun isPressed(code: Int): Boolean {
        if (!isMouseButton(code)) return false

        //#if MC >= 1.16.5
        //$$ val state = GLFW.glfwGetMouseButton(windowHandle, code)
        //$$ return state == GLFW.GLFW_PRESS || state == GLFW.GLFW_REPEAT
        //#else
        return Mouse.isButtonDown(code + 100)
        //#endif
    }
}
//#endif