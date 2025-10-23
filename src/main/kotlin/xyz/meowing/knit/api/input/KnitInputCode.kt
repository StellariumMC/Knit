package xyz.meowing.knit.api.input

/**
 * @author: Deftu
 */
sealed interface KnitInputCode {
    val code: Int
    val isPressed: Boolean
    val displayName: String get() = KnitInputs.getDisplayName(code)
}