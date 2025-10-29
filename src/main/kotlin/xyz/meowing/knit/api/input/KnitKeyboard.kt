package xyz.meowing.knit.api.input

import org.lwjgl.input.Keyboard

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
        Keyboard.enableRepeatEvents(enabled)
    }

    @JvmStatic
    fun isKeyboardButton(code: Int): Boolean {
        return code in 0..Keyboard.KEYBOARD_SIZE && code != Keyboard.KEY_NONE
    }

    @JvmStatic
    fun isPressed(code: Int): Boolean {
        if (!isKeyboardButton(code)) return false

        return Keyboard.isKeyDown(code)
    }
}