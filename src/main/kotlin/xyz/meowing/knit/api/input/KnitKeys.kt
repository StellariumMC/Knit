package xyz.meowing.knit.api.input

//#if MC != 1.16.5
//#if MC >= 1.16.5
//$$ import org.lwjgl.glfw.GLFW
//$$
//$$ object KnitKeys {
//$$     // --- Special / Unknown ---
//$$     val KEY_NONE: KnitKey = KnitKey(GLFW.GLFW_KEY_UNKNOWN)
//$$
//$$     // --- Function Keys ---
//$$     val KEY_ESCAPE: KnitKey = KnitKey(GLFW.GLFW_KEY_ESCAPE)
//$$     val KEY_F1: KnitKey = KnitKey(GLFW.GLFW_KEY_F1)
//$$     val KEY_F2: KnitKey = KnitKey(GLFW.GLFW_KEY_F2)
//$$     val KEY_F3: KnitKey = KnitKey(GLFW.GLFW_KEY_F3)
//$$     val KEY_F4: KnitKey = KnitKey(GLFW.GLFW_KEY_F4)
//$$     val KEY_F5: KnitKey = KnitKey(GLFW.GLFW_KEY_F5)
//$$     val KEY_F6: KnitKey = KnitKey(GLFW.GLFW_KEY_F6)
//$$     val KEY_F7: KnitKey = KnitKey(GLFW.GLFW_KEY_F7)
//$$     val KEY_F8: KnitKey = KnitKey(GLFW.GLFW_KEY_F8)
//$$     val KEY_F9: KnitKey = KnitKey(GLFW.GLFW_KEY_F9)
//$$     val KEY_F10: KnitKey = KnitKey(GLFW.GLFW_KEY_F10)
//$$     val KEY_F11: KnitKey = KnitKey(GLFW.GLFW_KEY_F11)
//$$     val KEY_F12: KnitKey = KnitKey(GLFW.GLFW_KEY_F12)
//$$     val KEY_F13: KnitKey = KnitKey(GLFW.GLFW_KEY_F13)
//$$     val KEY_F14: KnitKey = KnitKey(GLFW.GLFW_KEY_F14)
//$$     val KEY_F15: KnitKey = KnitKey(GLFW.GLFW_KEY_F15)
//$$
//$$     // --- Modifiers ---
//$$     val KEY_LEFT_SHIFT: KnitKey = KnitKey(GLFW.GLFW_KEY_LEFT_SHIFT)
//$$     val KEY_RIGHT_SHIFT: KnitKey = KnitKey(GLFW.GLFW_KEY_RIGHT_SHIFT)
//$$     val KEY_LEFT_CONTROL: KnitKey = KnitKey(GLFW.GLFW_KEY_LEFT_CONTROL)
//$$     val KEY_RIGHT_CONTROL: KnitKey = KnitKey(GLFW.GLFW_KEY_RIGHT_CONTROL)
//$$     val KEY_LEFT_ALT: KnitKey = KnitKey(GLFW.GLFW_KEY_LEFT_ALT)
//$$     val KEY_RIGHT_ALT: KnitKey = KnitKey(GLFW.GLFW_KEY_RIGHT_ALT)
//$$     val KEY_LEFT_SUPER: KnitKey = KnitKey(GLFW.GLFW_KEY_LEFT_SUPER)
//$$     val KEY_RIGHT_SUPER: KnitKey = KnitKey(GLFW.GLFW_KEY_RIGHT_SUPER)
//$$     val KEY_CAPS_LOCK: KnitKey = KnitKey(GLFW.GLFW_KEY_CAPS_LOCK)
//$$     val KEY_NUM_LOCK: KnitKey = KnitKey(GLFW.GLFW_KEY_NUM_LOCK)
//$$     val KEY_SCROLL_LOCK: KnitKey = KnitKey(GLFW.GLFW_KEY_SCROLL_LOCK)
//$$
//$$     // --- Control / Editing ---
//$$     val KEY_TAB: KnitKey = KnitKey(GLFW.GLFW_KEY_TAB)
//$$     val KEY_ENTER: KnitKey = KnitKey(GLFW.GLFW_KEY_ENTER)
//$$     val KEY_BACKSPACE: KnitKey = KnitKey(GLFW.GLFW_KEY_BACKSPACE)
//$$     val KEY_DELETE: KnitKey = KnitKey(GLFW.GLFW_KEY_DELETE)
//$$     val KEY_INSERT: KnitKey = KnitKey(GLFW.GLFW_KEY_INSERT)
//$$     val KEY_PAGE_UP: KnitKey = KnitKey(GLFW.GLFW_KEY_PAGE_UP)
//$$     val KEY_PAGE_DOWN: KnitKey = KnitKey(GLFW.GLFW_KEY_PAGE_DOWN)
//$$     val KEY_HOME: KnitKey = KnitKey(GLFW.GLFW_KEY_HOME)
//$$     val KEY_END: KnitKey = KnitKey(GLFW.GLFW_KEY_END)
//$$
//$$     // --- Arrows ---
//$$     val KEY_LEFT: KnitKey = KnitKey(GLFW.GLFW_KEY_LEFT)
//$$     val KEY_RIGHT: KnitKey = KnitKey(GLFW.GLFW_KEY_RIGHT)
//$$     val KEY_UP: KnitKey = KnitKey(GLFW.GLFW_KEY_UP)
//$$     val KEY_DOWN: KnitKey = KnitKey(GLFW.GLFW_KEY_DOWN)
//$$
//$$     // --- Numbers / Letters ---
//$$     val KEY_0: KnitKey = KnitKey(GLFW.GLFW_KEY_0)
//$$     val KEY_1: KnitKey = KnitKey(GLFW.GLFW_KEY_1)
//$$     val KEY_2: KnitKey = KnitKey(GLFW.GLFW_KEY_2)
//$$     val KEY_3: KnitKey = KnitKey(GLFW.GLFW_KEY_3)
//$$     val KEY_4: KnitKey = KnitKey(GLFW.GLFW_KEY_4)
//$$     val KEY_5: KnitKey = KnitKey(GLFW.GLFW_KEY_5)
//$$     val KEY_6: KnitKey = KnitKey(GLFW.GLFW_KEY_6)
//$$     val KEY_7: KnitKey = KnitKey(GLFW.GLFW_KEY_7)
//$$     val KEY_8: KnitKey = KnitKey(GLFW.GLFW_KEY_8)
//$$     val KEY_9: KnitKey = KnitKey(GLFW.GLFW_KEY_9)
//$$
//$$     val KEY_A: KnitKey = KnitKey(GLFW.GLFW_KEY_A)
//$$     val KEY_B: KnitKey = KnitKey(GLFW.GLFW_KEY_B)
//$$     val KEY_C: KnitKey = KnitKey(GLFW.GLFW_KEY_C)
//$$     val KEY_D: KnitKey = KnitKey(GLFW.GLFW_KEY_D)
//$$     val KEY_E: KnitKey = KnitKey(GLFW.GLFW_KEY_E)
//$$     val KEY_F: KnitKey = KnitKey(GLFW.GLFW_KEY_F)
//$$     val KEY_G: KnitKey = KnitKey(GLFW.GLFW_KEY_G)
//$$     val KEY_H: KnitKey = KnitKey(GLFW.GLFW_KEY_H)
//$$     val KEY_I: KnitKey = KnitKey(GLFW.GLFW_KEY_I)
//$$     val KEY_J: KnitKey = KnitKey(GLFW.GLFW_KEY_J)
//$$     val KEY_K: KnitKey = KnitKey(GLFW.GLFW_KEY_K)
//$$     val KEY_L: KnitKey = KnitKey(GLFW.GLFW_KEY_L)
//$$     val KEY_M: KnitKey = KnitKey(GLFW.GLFW_KEY_M)
//$$     val KEY_N: KnitKey = KnitKey(GLFW.GLFW_KEY_N)
//$$     val KEY_O: KnitKey = KnitKey(GLFW.GLFW_KEY_O)
//$$     val KEY_P: KnitKey = KnitKey(GLFW.GLFW_KEY_P)
//$$     val KEY_Q: KnitKey = KnitKey(GLFW.GLFW_KEY_Q)
//$$     val KEY_R: KnitKey = KnitKey(GLFW.GLFW_KEY_R)
//$$     val KEY_S: KnitKey = KnitKey(GLFW.GLFW_KEY_S)
//$$     val KEY_T: KnitKey = KnitKey(GLFW.GLFW_KEY_T)
//$$     val KEY_U: KnitKey = KnitKey(GLFW.GLFW_KEY_U)
//$$     val KEY_V: KnitKey = KnitKey(GLFW.GLFW_KEY_V)
//$$     val KEY_W: KnitKey = KnitKey(GLFW.GLFW_KEY_W)
//$$     val KEY_X: KnitKey = KnitKey(GLFW.GLFW_KEY_X)
//$$     val KEY_Y: KnitKey = KnitKey(GLFW.GLFW_KEY_Y)
//$$     val KEY_Z: KnitKey = KnitKey(GLFW.GLFW_KEY_Z)
//$$
//$$     // --- Symbols ---
//$$     val KEY_SPACE: KnitKey = KnitKey(GLFW.GLFW_KEY_SPACE)
//$$     val KEY_APOSTROPHE: KnitKey = KnitKey(GLFW.GLFW_KEY_APOSTROPHE)
//$$     val KEY_COMMA: KnitKey = KnitKey(GLFW.GLFW_KEY_COMMA)
//$$     val KEY_MINUS: KnitKey = KnitKey(GLFW.GLFW_KEY_MINUS)
//$$     val KEY_PERIOD: KnitKey = KnitKey(GLFW.GLFW_KEY_PERIOD)
//$$     val KEY_SLASH: KnitKey = KnitKey(GLFW.GLFW_KEY_SLASH)
//$$     val KEY_SEMICOLON: KnitKey = KnitKey(GLFW.GLFW_KEY_SEMICOLON)
//$$     val KEY_EQUAL: KnitKey = KnitKey(GLFW.GLFW_KEY_EQUAL)
//$$     val KEY_LEFT_BRACKET: KnitKey = KnitKey(GLFW.GLFW_KEY_LEFT_BRACKET)
//$$     val KEY_BACKSLASH: KnitKey = KnitKey(GLFW.GLFW_KEY_BACKSLASH)
//$$     val KEY_RIGHT_BRACKET: KnitKey = KnitKey(GLFW.GLFW_KEY_RIGHT_BRACKET)
//$$     val KEY_GRAVE_ACCENT: KnitKey = KnitKey(GLFW.GLFW_KEY_GRAVE_ACCENT)
//$$
//$$     // --- Keypad ---
//$$     val KEY_NUMPAD_0: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_0)
//$$     val KEY_NUMPAD_1: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_1)
//$$     val KEY_NUMPAD_2: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_2)
//$$     val KEY_NUMPAD_3: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_3)
//$$     val KEY_NUMPAD_4: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_4)
//$$     val KEY_NUMPAD_5: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_5)
//$$     val KEY_NUMPAD_6: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_6)
//$$     val KEY_NUMPAD_7: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_7)
//$$     val KEY_NUMPAD_8: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_8)
//$$     val KEY_NUMPAD_9: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_9)
//$$
//$$     val KEY_NUMPAD_DECIMAL: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_DECIMAL)
//$$     val KEY_NUMPAD_DIVIDE: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_DIVIDE)
//$$     val KEY_NUMPAD_MULTIPLY: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_MULTIPLY)
//$$     val KEY_NUMPAD_SUBTRACT: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_SUBTRACT)
//$$     val KEY_NUMPAD_ADD: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_ADD)
//$$     val KEY_NUMPAD_ENTER: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_ENTER)
//$$     val KEY_NUMPAD_EQUAL: KnitKey = KnitKey(GLFW.GLFW_KEY_KP_EQUAL)
//$$
//$$     // --- Miscellaneous ---
//$$     val KEY_PRINT_SCREEN: KnitKey = KnitKey(GLFW.GLFW_KEY_PRINT_SCREEN)
//$$     val KEY_PAUSE: KnitKey = KnitKey(GLFW.GLFW_KEY_PAUSE)
//$$     val KEY_MENU: KnitKey = KnitKey(GLFW.GLFW_KEY_MENU)
//$$ }
//#else
import org.lwjgl.input.Keyboard

/**
 * @author: Deftu
 */
object KnitKeys {
    // --- Special / Unknown ---
    val KEY_NONE: KnitKey = KnitKey(Keyboard.KEY_NONE)

    // --- Function Keys ---
    val KEY_ESCAPE: KnitKey = KnitKey(Keyboard.KEY_ESCAPE)
    val KEY_F1: KnitKey = KnitKey(Keyboard.KEY_F1)
    val KEY_F2: KnitKey = KnitKey(Keyboard.KEY_F2)
    val KEY_F3: KnitKey = KnitKey(Keyboard.KEY_F3)
    val KEY_F4: KnitKey = KnitKey(Keyboard.KEY_F4)
    val KEY_F5: KnitKey = KnitKey(Keyboard.KEY_F5)
    val KEY_F6: KnitKey = KnitKey(Keyboard.KEY_F6)
    val KEY_F7: KnitKey = KnitKey(Keyboard.KEY_F7)
    val KEY_F8: KnitKey = KnitKey(Keyboard.KEY_F8)
    val KEY_F9: KnitKey = KnitKey(Keyboard.KEY_F9)
    val KEY_F10: KnitKey = KnitKey(Keyboard.KEY_F10)
    val KEY_F11: KnitKey = KnitKey(Keyboard.KEY_F11)
    val KEY_F12: KnitKey = KnitKey(Keyboard.KEY_F12)
    val KEY_F13: KnitKey = KnitKey(Keyboard.KEY_F13)
    val KEY_F14: KnitKey = KnitKey(Keyboard.KEY_F14)
    val KEY_F15: KnitKey = KnitKey(Keyboard.KEY_F15)

    // --- Modifiers ---
    val KEY_LEFT_SHIFT: KnitKey = KnitKey(Keyboard.KEY_LSHIFT)
    val KEY_RIGHT_SHIFT: KnitKey = KnitKey(Keyboard.KEY_RSHIFT)
    val KEY_LEFT_CONTROL: KnitKey = KnitKey(Keyboard.KEY_LCONTROL)
    val KEY_RIGHT_CONTROL: KnitKey = KnitKey(Keyboard.KEY_RCONTROL)
    val KEY_LEFT_ALT: KnitKey = KnitKey(Keyboard.KEY_LMENU)
    val KEY_RIGHT_ALT: KnitKey = KnitKey(Keyboard.KEY_RMENU)
    val KEY_LEFT_SUPER: KnitKey = KnitKey(Keyboard.KEY_LMETA)
    val KEY_RIGHT_SUPER: KnitKey = KnitKey(Keyboard.KEY_RMETA)
    val KEY_CAPS_LOCK: KnitKey = KnitKey(Keyboard.KEY_CAPITAL)
    val KEY_NUM_LOCK: KnitKey = KnitKey(Keyboard.KEY_NUMLOCK)
    val KEY_SCROLL_LOCK: KnitKey = KnitKey(Keyboard.KEY_SCROLL)

    // --- Control / Editing ---
    val KEY_TAB: KnitKey = KnitKey(Keyboard.KEY_TAB)
    val KEY_ENTER: KnitKey = KnitKey(Keyboard.KEY_RETURN)
    val KEY_BACKSPACE: KnitKey = KnitKey(Keyboard.KEY_BACK)
    val KEY_DELETE: KnitKey = KnitKey(Keyboard.KEY_DELETE)
    val KEY_INSERT: KnitKey = KnitKey(Keyboard.KEY_INSERT)
    val KEY_PAGE_UP: KnitKey = KnitKey(Keyboard.KEY_PRIOR)
    val KEY_PAGE_DOWN: KnitKey = KnitKey(Keyboard.KEY_NEXT)
    val KEY_HOME: KnitKey = KnitKey(Keyboard.KEY_HOME)
    val KEY_END: KnitKey = KnitKey(Keyboard.KEY_END)

    // --- Arrows ---
    val KEY_LEFT: KnitKey = KnitKey(Keyboard.KEY_LEFT)
    val KEY_RIGHT: KnitKey = KnitKey(Keyboard.KEY_RIGHT)
    val KEY_UP: KnitKey = KnitKey(Keyboard.KEY_UP)
    val KEY_DOWN: KnitKey = KnitKey(Keyboard.KEY_DOWN)

    // --- Numbers / Letters ---
    val KEY_0: KnitKey = KnitKey(Keyboard.KEY_0)
    val KEY_1: KnitKey = KnitKey(Keyboard.KEY_1)
    val KEY_2: KnitKey = KnitKey(Keyboard.KEY_2)
    val KEY_3: KnitKey = KnitKey(Keyboard.KEY_3)
    val KEY_4: KnitKey = KnitKey(Keyboard.KEY_4)
    val KEY_5: KnitKey = KnitKey(Keyboard.KEY_5)
    val KEY_6: KnitKey = KnitKey(Keyboard.KEY_6)
    val KEY_7: KnitKey = KnitKey(Keyboard.KEY_7)
    val KEY_8: KnitKey = KnitKey(Keyboard.KEY_8)
    val KEY_9: KnitKey = KnitKey(Keyboard.KEY_9)

    val KEY_A: KnitKey = KnitKey(Keyboard.KEY_A)
    val KEY_B: KnitKey = KnitKey(Keyboard.KEY_B)
    val KEY_C: KnitKey = KnitKey(Keyboard.KEY_C)
    val KEY_D: KnitKey = KnitKey(Keyboard.KEY_D)
    val KEY_E: KnitKey = KnitKey(Keyboard.KEY_E)
    val KEY_F: KnitKey = KnitKey(Keyboard.KEY_F)
    val KEY_G: KnitKey = KnitKey(Keyboard.KEY_G)
    val KEY_H: KnitKey = KnitKey(Keyboard.KEY_H)
    val KEY_I: KnitKey = KnitKey(Keyboard.KEY_I)
    val KEY_J: KnitKey = KnitKey(Keyboard.KEY_J)
    val KEY_K: KnitKey = KnitKey(Keyboard.KEY_K)
    val KEY_L: KnitKey = KnitKey(Keyboard.KEY_L)
    val KEY_M: KnitKey = KnitKey(Keyboard.KEY_M)
    val KEY_N: KnitKey = KnitKey(Keyboard.KEY_N)
    val KEY_O: KnitKey = KnitKey(Keyboard.KEY_O)
    val KEY_P: KnitKey = KnitKey(Keyboard.KEY_P)
    val KEY_Q: KnitKey = KnitKey(Keyboard.KEY_Q)
    val KEY_R: KnitKey = KnitKey(Keyboard.KEY_R)
    val KEY_S: KnitKey = KnitKey(Keyboard.KEY_S)
    val KEY_T: KnitKey = KnitKey(Keyboard.KEY_T)
    val KEY_U: KnitKey = KnitKey(Keyboard.KEY_U)
    val KEY_V: KnitKey = KnitKey(Keyboard.KEY_V)
    val KEY_W: KnitKey = KnitKey(Keyboard.KEY_W)
    val KEY_X: KnitKey = KnitKey(Keyboard.KEY_X)
    val KEY_Y: KnitKey = KnitKey(Keyboard.KEY_Y)
    val KEY_Z: KnitKey = KnitKey(Keyboard.KEY_Z)

    // --- Symbols ---
    val KEY_SPACE: KnitKey = KnitKey(Keyboard.KEY_SPACE)
    val KEY_APOSTROPHE: KnitKey = KnitKey(Keyboard.KEY_APOSTROPHE)
    val KEY_COMMA: KnitKey = KnitKey(Keyboard.KEY_COMMA)
    val KEY_MINUS: KnitKey = KnitKey(Keyboard.KEY_MINUS)
    val KEY_PERIOD: KnitKey = KnitKey(Keyboard.KEY_PERIOD)
    val KEY_SLASH: KnitKey = KnitKey(Keyboard.KEY_SLASH)
    val KEY_SEMICOLON: KnitKey = KnitKey(Keyboard.KEY_SEMICOLON)
    val KEY_EQUAL: KnitKey = KnitKey(Keyboard.KEY_EQUALS)
    val KEY_LEFT_BRACKET: KnitKey = KnitKey(Keyboard.KEY_LBRACKET)
    val KEY_BACKSLASH: KnitKey = KnitKey(Keyboard.KEY_BACKSLASH)
    val KEY_RIGHT_BRACKET: KnitKey = KnitKey(Keyboard.KEY_RBRACKET)
    val KEY_GRAVE_ACCENT: KnitKey = KnitKey(Keyboard.KEY_GRAVE)

    // --- Keypad ---
    val KEY_NUMPAD_0: KnitKey = KnitKey(Keyboard.KEY_NUMPAD0)
    val KEY_NUMPAD_1: KnitKey = KnitKey(Keyboard.KEY_NUMPAD1)
    val KEY_NUMPAD_2: KnitKey = KnitKey(Keyboard.KEY_NUMPAD2)
    val KEY_NUMPAD_3: KnitKey = KnitKey(Keyboard.KEY_NUMPAD3)
    val KEY_NUMPAD_4: KnitKey = KnitKey(Keyboard.KEY_NUMPAD4)
    val KEY_NUMPAD_5: KnitKey = KnitKey(Keyboard.KEY_NUMPAD5)
    val KEY_NUMPAD_6: KnitKey = KnitKey(Keyboard.KEY_NUMPAD6)
    val KEY_NUMPAD_7: KnitKey = KnitKey(Keyboard.KEY_NUMPAD7)
    val KEY_NUMPAD_8: KnitKey = KnitKey(Keyboard.KEY_NUMPAD8)
    val KEY_NUMPAD_9: KnitKey = KnitKey(Keyboard.KEY_NUMPAD9)

    val KEY_NUMPAD_DECIMAL: KnitKey = KnitKey(Keyboard.KEY_DECIMAL)
    val KEY_NUMPAD_DIVIDE: KnitKey = KnitKey(Keyboard.KEY_DIVIDE)
    val KEY_NUMPAD_MULTIPLY: KnitKey = KnitKey(Keyboard.KEY_MULTIPLY)
    val KEY_NUMPAD_SUBTRACT: KnitKey = KnitKey(Keyboard.KEY_SUBTRACT)
    val KEY_NUMPAD_ADD: KnitKey = KnitKey(Keyboard.KEY_ADD)
    val KEY_NUMPAD_ENTER: KnitKey = KnitKey(Keyboard.KEY_NUMPADENTER)
    val KEY_NUMPAD_EQUAL: KnitKey = KnitKey(Keyboard.KEY_NUMPADEQUALS)

    // --- Miscellaneous ---
    val KEY_PRINT_SCREEN: KnitKey = KnitKey(Keyboard.KEY_SYSRQ)
    val KEY_PAUSE: KnitKey = KnitKey(Keyboard.KEY_PAUSE)
    val KEY_MENU: KnitKey = KnitKey(Keyboard.KEY_APPS)
}
//#endif
//#endif