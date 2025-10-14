package xyz.meowing.knit.api.input

//#if MC != 1.16.5
//#if MC >= 1.16.5
//$$ import net.minecraft.client.util.InputUtil
//$$ import org.lwjgl.glfw.GLFW
//#else
import net.minecraft.client.settings.GameSettings
//#endif

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
        //#if MC >= 1.21.9
        //$$ val keyName = GLFW.glfwGetKeyName(code, scanCode)
        //$$ if (keyName != null) {
        //$$     if (keyName.length == 1) return keyName.uppercase()
        //$$     return keyName
        //$$ }
        //$$ val name = (if (code == -1) {
        //$$     InputUtil.Type.SCANCODE.createFromCode(scanCode)
        //$$ } else {
        //$$     InputUtil.Type.KEYSYM.createFromCode(code)
        //$$ }).translationKey
        //#elseif MC >= 1.16.5
        //$$ val keyName = GLFW.glfwGetKeyName(code, scanCode)
        //$$ if (keyName != null) {
        //$$     if (keyName.length == 1) return keyName.uppercase()
        //$$     return keyName
        //$$ }
        //$$ val name = InputUtil.fromKeyCode(code, scanCode).translationKey.toString()
        //#else
        val name = GameSettings.getKeyDisplayString(code) ?: return "Unknown"
        //#endif
        if (name.length == 1) return name.uppercase()
        return name
    }
}
//#endif