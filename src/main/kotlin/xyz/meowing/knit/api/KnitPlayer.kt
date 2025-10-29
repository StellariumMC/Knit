package xyz.meowing.knit.api

import xyz.meowing.knit.api.KnitClient.client
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.item.ItemStack

object KnitPlayer {
    val player: EntityPlayerSP? get() = client.thePlayer

    val name: String?
        get() = player?.name

    val armor: Array<ItemStack?> get() = player?.inventory?.armorInventory ?: arrayOf(null, null, null, null)

    val heldItem: ItemStack? get() = player?.heldItem
}