package xyz.meowing.knit.api.text

//#if MC != 1.16.5

import xyz.meowing.knit.api.text.internal.ChainBuilder
import xyz.meowing.knit.api.text.internal.TextBuilder
import xyz.meowing.knit.api.text.core.FormattingCodes

//#if MC >= 1.20.1
//$$ import net.minecraft.text.Text as VanillaText
//#else
import net.minecraft.util.IChatComponent
//#endif

object KnitText {
    @JvmStatic
    fun literal(text: String): TextBuilder = TextBuilder(text)

    @JvmStatic
    fun empty(): TextBuilder = TextBuilder("")

    @JvmStatic
    fun fromVanilla(
        //#if MC >= 1.20.1
        //$$ text: VanillaText
        //#else
        text: IChatComponent
        //#endif
    ): TextBuilder {
        val builder = TextBuilder("")
        builder.vanilla = text
        return builder
    }

    @JvmStatic
    fun fromFormatted(text: String): TextBuilder {
        val parts = text.split(FormattingCodes.SECTION)
        if (parts.size <= 1) return literal(text)

        val root = empty()
        var current = literal("")

        for (i in parts.indices) {
            if (i == 0 && parts[i].isNotEmpty()) {
                root.append(literal(parts[i]))
                continue
            }

            val part = parts[i]
            if (part.isEmpty()) continue

            val code = part[0].lowercaseChar()
            val content = if (part.length > 1) part.substring(1) else ""

            when (code) {
                in '0'..'9', in 'a'..'f' -> {
                    if (current.text.isNotEmpty()) root.append(current)
                    current = literal(content)
                    FormattingCodes.codeToColor(code)?.let { current.color(it) }
                }
                'r' -> {
                    if (current.text.isNotEmpty()) root.append(current)
                    current = literal(content)
                }
                'l' -> {
                    current.bold()
                    current.text += content
                }
                'o' -> {
                    current.italic()
                    current.text += content
                }
                'n' -> {
                    current.underlined()
                    current.text += content
                }
                'm' -> {
                    current.strikethrough()
                    current.text += content
                }
                'k' -> {
                    current.obfuscated()
                    current.text += content
                }
                else -> current.text += FormattingCodes.SECTION + part
            }
        }

        if (current.text.isNotEmpty()) root.append(current)
        return root
    }

    @JvmStatic
    fun builder(): ChainBuilder = ChainBuilder()
}

fun String.asText(): TextBuilder = KnitText.literal(this)

fun String.asFormattedText(): TextBuilder = KnitText.fromFormatted(this)

//#if MC >= 1.20.1
//$$ fun VanillaText.asKnit(): TextBuilder = KnitText.fromVanilla(this)
//$$ fun VanillaText.toKnit(): TextBuilder = KnitText.fromVanilla(this)
//#else
fun IChatComponent.asKnit(): TextBuilder = KnitText.fromVanilla(this)
fun IChatComponent.toKnit(): TextBuilder = KnitText.fromVanilla(this)
//#endif

fun text(content: String, builder: TextBuilder.() -> Unit = {}): TextBuilder {
    return KnitText.literal(content).apply(builder)
}

fun buildText(builder: ChainBuilder.() -> Unit): TextBuilder {
    return KnitText.builder().apply(builder).build()
}
//#endif