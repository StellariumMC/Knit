package xyz.meowing.knit.api.text.core

//#if MC != 1.16.5

import xyz.meowing.knit.api.text.internal.TextBuilder

//#if MC >= 1.20.1
//$$ import net.minecraft.item.ItemStack
//#else
import net.minecraft.util.IChatComponent
//#endif

sealed interface HoverEvent {
    data class ShowText(val text: TextBuilder) : HoverEvent
    data class ShowItem(
        //#if MC >= 1.20.1
        //$$ val stack: ItemStack
        //#else
        val stack: IChatComponent
        //#endif
    ) : HoverEvent
}

//#endif