package xyz.meowing.knit.api.text.core

object FormattingCodes {
    const val SECTION = '§'
    private val stripRegex = "$SECTION[0-9a-fk-or]".toRegex(RegexOption.IGNORE_CASE)

    @JvmStatic
    fun codeToColor(code: Char): Int? = when (code.lowercaseChar()) {
        '0' -> ColorCodes.BLACK
        '1' -> ColorCodes.DARK_BLUE
        '2' -> ColorCodes.DARK_GREEN
        '3' -> ColorCodes.DARK_AQUA
        '4' -> ColorCodes.DARK_RED
        '5' -> ColorCodes.DARK_PURPLE
        '6' -> ColorCodes.GOLD
        '7' -> ColorCodes.GRAY
        '8' -> ColorCodes.DARK_GRAY
        '9' -> ColorCodes.BLUE
        'a' -> ColorCodes.GREEN
        'b' -> ColorCodes.AQUA
        'c' -> ColorCodes.RED
        'd' -> ColorCodes.LIGHT_PURPLE
        'e' -> ColorCodes.YELLOW
        'f' -> ColorCodes.WHITE
        else -> null
    }

    @JvmStatic
    fun strip(text: String): String = text.replace(stripRegex, "")

    @JvmStatic
    fun translateAlternate(altChar: Char, text: String): String {
        val chars = text.toCharArray()
        for (i in 0 until chars.size - 1) {
            if (chars[i] == altChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(chars[i + 1]) > -1) {
                chars[i] = SECTION
                chars[i + 1] = chars[i + 1].lowercaseChar()
            }
        }
        return String(chars)
    }
}