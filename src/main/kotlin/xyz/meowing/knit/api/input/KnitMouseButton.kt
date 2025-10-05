package xyz.meowing.knit.api.input

//#if MC != 1.16.5
data class KnitMouseButton(override val code: Int) : KnitInputCode {
    override val isPressed: Boolean get() = KnitMouse.isPressed(code)
}
//#endif