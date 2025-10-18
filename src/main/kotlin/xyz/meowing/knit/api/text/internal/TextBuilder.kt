package xyz.meowing.knit.api.text.internal

//#if MC != 1.16.5

import xyz.meowing.knit.api.text.core.ClickEvent
import xyz.meowing.knit.api.text.core.HoverEvent
import xyz.meowing.knit.api.text.core.ColorCodes

//#if MC >= 1.20.1
    //#if FORGE-LIKE
    //$$ import net.minecraft.network.chat.Component as VanillaText
    //#else
    //$$ import net.minecraft.text.Text as VanillaText
    //#endif
//$$ import net.minecraft.text.ClickEvent as ModernClickEvent
//$$ import net.minecraft.text.HoverEvent as ModernHoverEvent
//$$ import net.minecraft.text.Style
//$$ import net.minecraft.item.ItemStack
//#else
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent
import net.minecraft.event.ClickEvent as VanillaClickEvent
import net.minecraft.event.HoverEvent as VanillaHoverEvent
//#endif

//#if MC >= 1.21.5
    //#if FORGE-LIKE
    //$$ import net.minecraft.network.chat.TextColor
    //#else
    //$$ import net.minecraft.text.TextColor
    //#endif
//#endif

import java.net.URI
import xyz.meowing.knit.api.text.KnitText

class TextBuilder internal constructor(
    internal var text: String
) {
    internal var vanilla:
    //#if MC >= 1.20.1
    //$$ VanillaText
    //#else
            IChatComponent
            //#endif
            ? = null

    private val siblings = mutableListOf<TextBuilder>()

    private var color: Int? = null
    private var bold: Boolean? = null
    private var italic: Boolean? = null
    private var underlined: Boolean? = null
    private var strikethrough: Boolean? = null
    private var obfuscated: Boolean? = null
    private var clickEvent: ClickEvent? = null
    private var hoverEvent: HoverEvent? = null
    private var insertion: String? = null

    fun color(hex: String): TextBuilder {
        color = hex.removePrefix("#").toInt(16)
        return this
    }

    fun color(rgb: Int): TextBuilder {
        color = rgb
        return this
    }

    fun black(): TextBuilder = color(ColorCodes.BLACK)
    fun darkBlue(): TextBuilder = color(ColorCodes.DARK_BLUE)
    fun darkGreen(): TextBuilder = color(ColorCodes.DARK_GREEN)
    fun darkAqua(): TextBuilder = color(ColorCodes.DARK_AQUA)
    fun darkRed(): TextBuilder = color(ColorCodes.DARK_RED)
    fun darkPurple(): TextBuilder = color(ColorCodes.DARK_PURPLE)
    fun gold(): TextBuilder = color(ColorCodes.GOLD)
    fun gray(): TextBuilder = color(ColorCodes.GRAY)
    fun darkGray(): TextBuilder = color(ColorCodes.DARK_GRAY)
    fun blue(): TextBuilder = color(ColorCodes.BLUE)
    fun green(): TextBuilder = color(ColorCodes.GREEN)
    fun aqua(): TextBuilder = color(ColorCodes.AQUA)
    fun red(): TextBuilder = color(ColorCodes.RED)
    fun lightPurple(): TextBuilder = color(ColorCodes.LIGHT_PURPLE)
    fun yellow(): TextBuilder = color(ColorCodes.YELLOW)
    fun white(): TextBuilder = color(ColorCodes.WHITE)

    fun bold(value: Boolean = true): TextBuilder {
        bold = value
        return this
    }

    fun italic(value: Boolean = true): TextBuilder {
        italic = value
        return this
    }

    fun underlined(value: Boolean = true): TextBuilder {
        underlined = value
        return this
    }

    fun strikethrough(value: Boolean = true): TextBuilder {
        strikethrough = value
        return this
    }

    fun obfuscated(value: Boolean = true): TextBuilder {
        obfuscated = value
        return this
    }

    fun reset(): TextBuilder {
        color = null
        bold = null
        italic = null
        underlined = null
        strikethrough = null
        obfuscated = null
        return this
    }

    fun onClick(event: ClickEvent): TextBuilder {
        clickEvent = event
        return this
    }

    fun onClick(url: String): TextBuilder {
        clickEvent = ClickEvent.OpenUrl(URI.create(url))
        return this
    }

    fun onHover(event: HoverEvent): TextBuilder {
        hoverEvent = event
        return this
    }

    fun onHover(text: String): TextBuilder {
        hoverEvent = HoverEvent.ShowText(KnitText.literal(text))
        return this
    }

    fun onHover(builder: TextBuilder): TextBuilder {
        hoverEvent = HoverEvent.ShowText(builder)
        return this
    }

    fun insertion(text: String): TextBuilder {
        insertion = text
        return this
    }

    fun append(builder: TextBuilder): TextBuilder {
        siblings.add(builder)
        return this
    }

    fun append(text: String): TextBuilder {
        siblings.add(KnitText.literal(text))
        return this
    }

    fun appendLine(): TextBuilder {
        siblings.add(KnitText.literal("\n"))
        return this
    }

    fun appendLine(text: String): TextBuilder {
        siblings.add(KnitText.literal(text))
        siblings.add(KnitText.literal("\n"))
        return this
    }

    fun appendLine(builder: TextBuilder): TextBuilder {
        siblings.add(builder)
        siblings.add(KnitText.literal("\n"))
        return this
    }

    fun suggestCommand(command: String): TextBuilder {
        clickEvent = ClickEvent.SuggestCommand(command)
        return this
    }

    fun runCommand(command: String): TextBuilder {
        clickEvent = ClickEvent.RunCommand(command)
        return this
    }

    fun copyToClipboard(text: String): TextBuilder {
        clickEvent = ClickEvent.CopyToClipboard(text)
        return this
    }

    fun openUrl(url: String): TextBuilder {
        clickEvent = ClickEvent.OpenUrl(URI.create(url))
        return this
    }

    fun changePage(page: Int): TextBuilder {
        clickEvent = ClickEvent.ChangePage(page)
        return this
    }

    fun build():
    //#if MC >= 1.20.1
    //$$ VanillaText
    //#else
            IChatComponent
    //#endif
    {
        vanilla?.let { return it }

        //#if MC >= 1.20.1
        //$$ val base = VanillaText.literal(text)
        //$$ var style = base.style
        //$$ color?.let {
        //#if MC >= 1.21.5
        //$$ style = style.withColor(TextColor.fromRgb(it))
        //#else
        //$$ style = style.withColor(it)
        //#endif
        //$$ }
        //$$ bold?.let { style = style.withBold(it) }
        //$$ italic?.let { style = style.withItalic(it) }
        //$$ underlined?.let { style = style.withUnderline(it) }
        //$$ strikethrough?.let { style = style.withStrikethrough(it) }
        //$$ obfuscated?.let { style = style.withObfuscated(it) }
        //$$ insertion?.let { style = style.withInsertion(it) }
        //$$
        //$$ clickEvent?.let {
        //$$     style = style.withClickEvent(when (it) {
        //#if MC >= 1.21.5
        //$$ is ClickEvent.OpenUrl -> ModernClickEvent.OpenUrl(it.url)
        //$$ is ClickEvent.RunCommand -> ModernClickEvent.RunCommand(it.command)
        //$$ is ClickEvent.SuggestCommand -> ModernClickEvent.SuggestCommand(it.command)
        //$$ is ClickEvent.CopyToClipboard -> ModernClickEvent.CopyToClipboard(it.text)
        //$$ is ClickEvent.ChangePage -> ModernClickEvent.ChangePage(it.page)
        //#else
        //$$ is ClickEvent.OpenUrl -> ModernClickEvent(ModernClickEvent.Action.OPEN_URL, it.url.toString())
        //$$ is ClickEvent.RunCommand -> ModernClickEvent(ModernClickEvent.Action.RUN_COMMAND, it.command)
        //$$ is ClickEvent.SuggestCommand -> ModernClickEvent(ModernClickEvent.Action.SUGGEST_COMMAND, it.command)
        //$$ is ClickEvent.CopyToClipboard -> ModernClickEvent(ModernClickEvent.Action.COPY_TO_CLIPBOARD, it.text)
        //$$ is ClickEvent.ChangePage -> ModernClickEvent(ModernClickEvent.Action.CHANGE_PAGE, it.page.toString())
        //#endif
        //$$     })
        //$$ }
        //$$
        //$$ hoverEvent?.let {
        //$$     style = style.withHoverEvent(when (it) {
        //#if MC >= 1.21.5
        //$$ is HoverEvent.ShowText -> ModernHoverEvent.ShowText(it.text.build())
        //$$ is HoverEvent.ShowItem -> ModernHoverEvent.ShowItem(it.stack)
        //#else
        //$$ is HoverEvent.ShowText -> ModernHoverEvent(ModernHoverEvent.Action.SHOW_TEXT, it.text.build())
        //$$ is HoverEvent.ShowItem -> ModernHoverEvent(ModernHoverEvent.Action.SHOW_ITEM, ModernHoverEvent.ItemStackContent(it.stack))
        //#endif
        //$$     })
        //$$ }
        //$$
        //$$ val result = base.copy().setStyle(style)
        //$$ siblings.forEach { result.append(it.build()) }
        //$$ return result
        //#else
        val base = ChatComponentText(text)
        val style = base.chatStyle

        color?.let {
            val nearest = ColorMapper.findNearest(it)
            style.setColor(nearest)
        }
        bold?.let { style.setBold(it) }
        italic?.let { style.setItalic(it) }
        underlined?.let { style.setUnderlined(it) }
        strikethrough?.let { style.setStrikethrough(it) }
        obfuscated?.let { style.setObfuscated(it) }
        insertion?.let { style.setChatClickEvent(VanillaClickEvent(VanillaClickEvent.Action.SUGGEST_COMMAND, it)) }

        clickEvent?.let {
            style.setChatClickEvent(when (it) {
                is ClickEvent.OpenUrl -> VanillaClickEvent(VanillaClickEvent.Action.OPEN_URL, it.url.toString())
                is ClickEvent.RunCommand -> VanillaClickEvent(VanillaClickEvent.Action.RUN_COMMAND, it.command)
                is ClickEvent.SuggestCommand -> VanillaClickEvent(VanillaClickEvent.Action.SUGGEST_COMMAND, it.command)
                is ClickEvent.CopyToClipboard -> VanillaClickEvent(VanillaClickEvent.Action.SUGGEST_COMMAND, it.text)
                is ClickEvent.ChangePage -> VanillaClickEvent(VanillaClickEvent.Action.CHANGE_PAGE, it.page.toString())
            })
        }

        hoverEvent?.let {
            style.setChatHoverEvent(when (it) {
                is HoverEvent.ShowText -> VanillaHoverEvent(VanillaHoverEvent.Action.SHOW_TEXT, it.text.build())
                is HoverEvent.ShowItem -> VanillaHoverEvent(VanillaHoverEvent.Action.SHOW_ITEM, it.stack)
            })
        }

        base.chatStyle = style
        siblings.forEach { base.appendSibling(it.build()) }
        return base
        //#endif
    }

    fun toVanilla():
    //#if MC >= 1.20.1
    //$$ VanillaText
    //#else
            IChatComponent
    //#endif
    {
        return build()
    }

    fun string(): String {
        //#if MC >= 1.20.1
        //$$ return build().string
        //#else
        return build().unformattedText
        //#endif
    }

    fun formatted(): String {
        //#if MC >= 1.20.1
        //$$ return build().string
        //#else
        return build().formattedText
        //#endif
    }

    operator fun plus(other: TextBuilder): TextBuilder {
        return this.append(other)
    }

    operator fun plus(other: String): TextBuilder {
        return this.append(other)
    }
}
//#endif