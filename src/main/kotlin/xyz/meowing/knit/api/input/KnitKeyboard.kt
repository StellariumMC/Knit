package xyz.meowing.knit.api.input

//#if MC != 1.16.5
//#if MC >= 1.16.5
//$$ import xyz.meowing.knit.api.render.KnitResolution.windowHandle
//$$ import org.lwjgl.glfw.GLFW
//#else
import org.lwjgl.input.Keyboard
//#endif

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
    fun allowRepeatEvents(@Suppress("UNUSED_PARAMETER") enabled: Boolean) {
        //#if MC >= 1.19.3
        // This function was removed in 1.19.3. Repeat events are permanently enabled.
        //#elseif MC >= 1.16.5
        //$$ client.keyboardHandler.setSendRepeatsToGui(enabled)
        //#else
        //$$ Keyboard.enableRepeatEvents(enabled)
        //#endif
    }

    @JvmStatic
    fun isKeyboardButton(code: Int): Boolean {
        //#if MC >= 1.16.5
        //$$ return code in 0 until GLFW.GLFW_KEY_LAST
        //#else
        return code in 0..Keyboard.KEYBOARD_SIZE && code != Keyboard.KEY_NONE
        //#endif
    }

    @JvmStatic
    fun isPressed(code: Int): Boolean {
        if (!isKeyboardButton(code)) return false

        //#if MC >= 1.16.5
        //$$ val state = GLFW.glfwGetKey(windowHandle, code)
        //$$ return state == GLFW.GLFW_PRESS || state == GLFW.GLFW_REPEAT
        //#else
        return Keyboard.isKeyDown(code)
        //#endif
    }
}
//#endif