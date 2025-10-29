package xyz.meowing.knit.api.input

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
    }
}