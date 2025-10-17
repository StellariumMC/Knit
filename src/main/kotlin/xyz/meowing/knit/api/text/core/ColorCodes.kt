package xyz.meowing.knit.api.text.core

object ColorCodes {
    const val BLACK: Int = 0x000000
    const val DARK_BLUE: Int = 0x0000AA
    const val DARK_GREEN: Int = 0x00AA00
    const val DARK_AQUA: Int = 0x00AAAA
    const val DARK_RED: Int = 0xAA0000
    const val DARK_PURPLE: Int = 0xAA00AA
    const val GOLD: Int = 0xFFAA00
    const val GRAY: Int = 0xAAAAAA
    const val DARK_GRAY: Int = 0x555555
    const val BLUE: Int = 0x5555FF
    const val GREEN: Int = 0x55FF55
    const val AQUA: Int = 0x55FFFF
    const val RED: Int = 0xFF5555
    const val LIGHT_PURPLE: Int = 0xFF55FF
    const val YELLOW: Int = 0xFFFF55
    const val WHITE: Int = 0xFFFFFF

    @JvmStatic
    fun hex(hex: String): Int = hex.removePrefix("#").toInt(16)
}