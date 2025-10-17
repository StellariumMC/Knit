package xyz.meowing.knit.api.text.internal

//#if MC < 1.20.1 && MC != 1.16.5
import net.minecraft.util.EnumChatFormatting
import xyz.meowing.knit.api.text.core.ColorCodes
import kotlin.math.sqrt

object ColorMapper {
    private data class ColorEntry(val name: String, val value: Int, val formatting: Any)

    private val COLORS = listOf(
        ColorEntry("BLACK", ColorCodes.BLACK, EnumChatFormatting.BLACK),
        ColorEntry("DARK_BLUE", ColorCodes.DARK_BLUE, EnumChatFormatting.DARK_BLUE),
        ColorEntry("DARK_GREEN", ColorCodes.DARK_GREEN, EnumChatFormatting.DARK_GREEN),
        ColorEntry("DARK_AQUA", ColorCodes.DARK_AQUA, EnumChatFormatting.DARK_AQUA),
        ColorEntry("DARK_RED", ColorCodes.DARK_RED, EnumChatFormatting.DARK_RED),
        ColorEntry("DARK_PURPLE", ColorCodes.DARK_PURPLE, EnumChatFormatting.DARK_PURPLE),
        ColorEntry("GOLD", ColorCodes.GOLD, EnumChatFormatting.GOLD),
        ColorEntry("GRAY", ColorCodes.GRAY, EnumChatFormatting.GRAY),
        ColorEntry("DARK_GRAY", ColorCodes.DARK_GRAY, EnumChatFormatting.DARK_GRAY),
        ColorEntry("BLUE", ColorCodes.BLUE, EnumChatFormatting.BLUE),
        ColorEntry("GREEN", ColorCodes.GREEN, EnumChatFormatting.GREEN),
        ColorEntry("AQUA", ColorCodes.AQUA, EnumChatFormatting.AQUA),
        ColorEntry("RED", ColorCodes.RED, EnumChatFormatting.RED),
        ColorEntry("LIGHT_PURPLE", ColorCodes.LIGHT_PURPLE, EnumChatFormatting.LIGHT_PURPLE),
        ColorEntry("YELLOW", ColorCodes.YELLOW, EnumChatFormatting.YELLOW),
        ColorEntry("WHITE", ColorCodes.WHITE, EnumChatFormatting.WHITE)
    )

    fun findNearest(rgb: Int): EnumChatFormatting {
        var nearest = COLORS.last().formatting as EnumChatFormatting
        var minDistance = Double.MAX_VALUE

        val r = (rgb shr 16) and 0xFF
        val g = (rgb shr 8) and 0xFF
        val b = rgb and 0xFF

        for (entry in COLORS) {
            val fr = (entry.value shr 16) and 0xFF
            val fg = (entry.value shr 8) and 0xFF
            val fb = entry.value and 0xFF

            val distance = sqrt(
                ((r - fr) * (r - fr) + (g - fg) * (g - fg) + (b - fb) * (b - fb)).toDouble()
            )

            if (distance < minDistance) {
                minDistance = distance
                nearest = entry.formatting as EnumChatFormatting
            }
        }

        return nearest
    }
}
//#endif