package xyz.meowing.knit.api.input

import xyz.meowing.knit.api.render.KnitResolution.windowHandle
import org.lwjgl.glfw.GLFW

/**
 * @author: Deftu
 */
object KnitKeyboard {
    @JvmStatic
    val isShiftKeyPressed: Boolean
        get() = KnitKeys.KEY_LEFT_SHIFT.isPressed || KnitKeys.KEY_RIGHT_SHIFT.isPressed

    @JvmStatic
    val isCtrlKeyPressed: Boolean
        get() = KnitKeys.KEY_LEFT_CONTROL.isPressed || KnitKeys.KEY_RIGHT_CONTROL.isPressed

    @JvmStatic
    val isAltKeyPressed: Boolean
        get() = KnitKeys.KEY_LEFT_ALT.isPressed || KnitKeys.KEY_RIGHT_ALT.isPressed

    @JvmStatic
    val isSuperKeyPressed: Boolean
        get() = KnitKeys.KEY_LEFT_SUPER.isPressed || KnitKeys.KEY_RIGHT_SUPER.isPressed

    @JvmStatic
    fun isKeyboardButton(code: Int): Boolean {
        return code in 0 until GLFW.GLFW_KEY_LAST
    }

    @JvmStatic
    fun isPressed(code: Int): Boolean {
        if (!isKeyboardButton(code)) return false
        val state = GLFW.glfwGetKey(windowHandle, code)
        return state == GLFW.GLFW_PRESS || state == GLFW.GLFW_REPEAT
    }
}