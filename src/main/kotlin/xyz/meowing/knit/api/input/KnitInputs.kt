package xyz.meowing.knit.api.input

import org.lwjgl.glfw.GLFW

//#if MC < 1.21.9 || FABRIC
import net.minecraft.client.util.InputUtil
//#endif

//#if MC >= 1.21.9 && FORGE-LIKE
//$$ import com.mojang.blaze3d.platform.InputConstants
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
            //#if FORGE-LIKE
            //$$ val name = (if (code == -1) {
            //$$     InputConstants.Type.SCANCODE.getOrCreate(scanCode)
            //$$ } else {
            //$$     InputConstants.Type.KEYSYM.getOrCreate(code)
            //$$ }).displayName.string
            //#else
            //$$ val name = (if (code == -1) {
            //$$     InputUtil.Type.SCANCODE.createFromCode(scanCode)
            //$$ } else {
            //$$     InputUtil.Type.KEYSYM.createFromCode(code)
            //$$ }).localizedText.string
            //#endif
        //#else
        val keyName = GLFW.glfwGetKeyName(code, scanCode)
        if (keyName != null) {
            if (keyName.length == 1) return keyName.uppercase()
            return keyName
        }
        val name = InputUtil.fromKeyCode(code, scanCode).localizedText.string
        //#endif
        if (name.length == 1) return name.uppercase()
        return name
    }
}