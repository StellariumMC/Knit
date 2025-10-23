package xyz.meowing.knit.api.input

/**
 * @author: Deftu
 */
data class KnitMouseButton(override val code: Int) : KnitInputCode {
    override val isPressed: Boolean get() = KnitMouse.isPressed(code)
}