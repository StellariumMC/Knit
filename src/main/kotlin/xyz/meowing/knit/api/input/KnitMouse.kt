package xyz.meowing.knit.api.input

import xyz.meowing.knit.api.render.KnitResolution
import xyz.meowing.knit.api.render.KnitResolution.windowHandle
import xyz.meowing.knit.api.KnitClient.client
import org.lwjgl.glfw.GLFW
import kotlin.math.max

/**
 * @author: Deftu
 */
object KnitMouse {
    object Raw {
        val x: Double
            get() {
                return client.mouse.x
            }

        val y: Double
            get() {
                return client.mouse.y
            }
    }

    object Scaled {
        val x: Double
            get() = Raw.x * KnitResolution.scaledWidth / max(1, KnitResolution.windowWidth)
        val y: Double
            get() = Raw.y * KnitResolution.scaledWidth / max(1, KnitResolution.windowWidth)
    }

    var isCursorGrabbed: Boolean
        get() {
            return client.mouse.isCursorLocked
        }
        set(value) {
            if (value) client.mouse.lockCursor() else client.mouse.unlockCursor()
        }

    fun isMouseButton(code: Int): Boolean {
        return code in GLFW.GLFW_MOUSE_BUTTON_1..GLFW.GLFW_MOUSE_BUTTON_8
    }

    fun isPressed(code: Int): Boolean {
        if (!isMouseButton(code)) return false

        val state = GLFW.glfwGetMouseButton(windowHandle, code)
        return state == GLFW.GLFW_PRESS || state == GLFW.GLFW_REPEAT
    }
}