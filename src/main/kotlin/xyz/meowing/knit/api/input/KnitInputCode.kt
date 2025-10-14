package xyz.meowing.knit.api.input

//#if MC != 1.16.5
/**
 * @author: Deftu
 */
sealed interface KnitInputCode {
    val code: Int
    val isPressed: Boolean
    val displayName: String get() = KnitInputs.getDisplayName(code)
}
//#endif