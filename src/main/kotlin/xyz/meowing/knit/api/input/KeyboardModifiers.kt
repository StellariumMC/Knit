package xyz.meowing.knit.api.input

//#if MC != 1.16.5
//#if MC >= 1.16.5
//$$ import org.lwjgl.glfw.GLFW
//#endif

/**
 * @author: Deftu
 */
data class KeyboardModifiers(
    val isShift: Boolean,
    val isCtrl: Boolean,
    val isAlt: Boolean,
    val isSuper: Boolean,
    val isCapsLock: Boolean = false,
    val isNumLock: Boolean = false
) {
    companion object {
        val current: KeyboardModifiers
            get() {
                return KeyboardModifiers(
                    isShift = KnitKeyboard.isShiftKeyPressed,
                    isCtrl = KnitKeyboard.isCtrlKeyPressed,
                    isAlt = KnitKeyboard.isAltKeyPressed,
                    isSuper = KnitKeyboard.isSuperKeyPressed,
                )
            }

        //#if MC >= 1.16.5
        //$$ fun wrap(mods: Int): KeyboardModifiers {
        //$$     return KeyboardModifiers(
        //$$         isShift = (mods and GLFW.GLFW_MOD_SHIFT) != 0,
        //$$         isCtrl = (mods and GLFW.GLFW_MOD_CONTROL) != 0,
        //$$         isAlt = (mods and GLFW.GLFW_MOD_ALT) != 0,
        //$$         isSuper = (mods and GLFW.GLFW_MOD_SUPER) != 0,
        //$$     )
        //$$ }
        //#endif
    }

    //#if MC >= 1.16.5
    //$$ fun toMods(): Int {
    //$$     return listOf(
    //$$         isShift to GLFW.GLFW_MOD_SHIFT,
    //$$         isCtrl to GLFW.GLFW_MOD_CONTROL,
    //$$         isAlt to GLFW.GLFW_MOD_ALT,
    //$$         isSuper to GLFW.GLFW_MOD_SUPER,
    //$$         isCapsLock to GLFW.GLFW_MOD_CAPS_LOCK,
    //$$         isNumLock to GLFW.GLFW_MOD_NUM_LOCK
    //$$     ).sumOf { (value, mod) ->
    //$$         if (value) mod else 0
    //$$     }
    //$$ }
    //#endif
}
//#endif