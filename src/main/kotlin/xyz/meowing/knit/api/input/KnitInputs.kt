package xyz.meowing.knit.api.input

import net.minecraft.client.settings.GameSettings

/**
 * @author: Deftu
 */
object KnitInputs {
    fun get(code: Int): KnitInputCode {
        return when {
            KnitKeyboard.isKeyboardButton(code) -> KnitKey(code)
            KnitMouse.isMouseButton(code) -> KnitMouseButton(code)
            else -> throw IllegalArgumentException("Code $code is not a valid key or mouse button")
        }
    }

    @JvmStatic
    @JvmOverloads
    fun getDisplayName(code: Int, scanCode: Int = -1): String {
        val name = GameSettings.getKeyDisplayString(code) ?: return "Unknown"
        if (name.length == 1) return name.uppercase()
        return name
    }
}