package xyz.meowing.knit.api.text.core

import xyz.meowing.knit.api.text.internal.TextBuilder
import net.minecraft.item.ItemStack

sealed interface HoverEvent {
    data class ShowText(val text: TextBuilder) : HoverEvent
    data class ShowItem( val stack: ItemStack) : HoverEvent
}