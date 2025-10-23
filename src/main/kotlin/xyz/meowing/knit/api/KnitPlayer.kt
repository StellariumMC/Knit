package xyz.meowing.knit.api

import xyz.meowing.knit.api.KnitClient.client
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.item.ItemStack

object KnitPlayer {
    val player: ClientPlayerEntity? get() = client.player

    val name: String? get() = player?.name?.string

    val armor: Array<ItemStack?>
        get() {
            val inv = player?.inventory ?: return arrayOf(null, null, null, null)
            return arrayOf(inv.getStack(36), inv.getStack(37), inv.getStack(38), inv.getStack(39))
        }

    val heldItem: ItemStack? get() = player?.mainHandStack
}