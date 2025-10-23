package xyz.meowing.knit.api.text.internal

import xyz.meowing.knit.api.text.KnitText

//#if FORGE-LIKE
//$$ import net.minecraft.network.chat.Component as VanillaText
//#else
import net.minecraft.text.Text as VanillaText
//#endif

class ChainBuilder {
    private val parts = mutableListOf<TextBuilder>()

    fun text(content: String, builder: TextBuilder.() -> Unit = {}): ChainBuilder {
        parts.add(KnitText.literal(content).apply(builder))
        return this
    }

    fun append(builder: TextBuilder): ChainBuilder {
        parts.add(builder)
        return this
    }

    fun newLine(): ChainBuilder {
        parts.add(KnitText.literal("\n"))
        return this
    }

    fun space(): ChainBuilder {
        parts.add(KnitText.literal(" "))
        return this
    }

    fun build(): TextBuilder {
        if (parts.isEmpty()) return KnitText.empty()
        val result = parts.first()
        for (i in 1 until parts.size) {
            result.append(parts[i])
        }
        return result
    }

    fun toVanilla(): VanillaText {
        return build().toVanilla()
    }
}