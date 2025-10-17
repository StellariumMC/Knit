package xyz.meowing.knit.api.text.internal

//#if MC != 1.16.5

import xyz.meowing.knit.api.text.KnitText

//#if MC >= 1.20.1
//$$ import net.minecraft.text.Text as VanillaText
//#else
import net.minecraft.util.IChatComponent
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

    fun toVanilla():
    //#if MC >= 1.20.1
    //$$ VanillaText
    //#else
            IChatComponent
    //#endif
    {
        return build().toVanilla()
    }
}

//#endif