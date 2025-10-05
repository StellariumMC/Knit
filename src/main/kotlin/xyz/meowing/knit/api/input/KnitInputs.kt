package xyz.meowing.knit.api.input

//#if MC != 1.16.5
//#if MC >= 1.16.5
//$$ import net.minecraft.client.util.InputUtil
//#else
import net.minecraft.client.settings.GameSettings
//#endif

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
        val name =
            //#if MC >= 1.21.9
            //$$ (if (code == -1) {
            //$$     InputUtil.Type.SCANCODE.createFromCode(scanCode)
            //$$ } else {
            //$$     InputUtil.Type.KEYSYM.createFromCode(code)
            //$$ }).translationKey
            //#elseif MC >= 1.16.5
            //$$ InputUtil.fromKeyCode(code, scanCode).translationKey.toString()
            //#else
            GameSettings.getKeyDisplayString(code) ?: return "Unknown"
            //#endif

        if (name.length == 1) return name.first().uppercase()
        return name
    }
}
//#endif