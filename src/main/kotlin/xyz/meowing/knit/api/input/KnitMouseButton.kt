package xyz.meowing.knit.api.input

//#if MC != 1.16.5
/**
 * @author: Deftu
 */
data class KnitMouseButton(override val code: Int) : KnitInputCode {
    override val isPressed: Boolean get() = KnitMouse.isPressed(code)
}
//#endif