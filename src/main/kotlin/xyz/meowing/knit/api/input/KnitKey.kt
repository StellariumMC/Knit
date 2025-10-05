package xyz.meowing.knit.api.input

//#if MC != 1.16.5
data class KnitKey(override val code: Int) : KnitInputCode {
    override val isPressed: Boolean get() = KnitKeyboard.isPressed(code)
    override fun hashCode(): Int = code.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is KnitKey) return false
        if (code != other.code) return false

        return true
    }
}
//#endif