package xyz.meowing.knit.api

import xyz.meowing.knit.Knit.client
//#if MC >= 1.20.1
//$$ import net.minecraft.client.network.ClientPlayerEntity
//$$ import net.minecraft.item.ItemStack
//#else
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.item.ItemStack
//#endif

object KnitPlayer {
    //#if MC >= 1.20.1
    //$$ inline val player: ClientPlayerEntity?
    //$$     get() = client.player
    //#else
    inline val player: EntityPlayerSP?
        get() = client.thePlayer
    //#endif

    inline val name: String?
        //#if MC >= 1.20.1
        //$$ get() = player?.name?.string
        //#else
        get() = player?.name
        //#endif

    inline val armor: Array<ItemStack?>
        get() {
            //#if MC >= 1.20.1
            //$$ val inv = player?.inventory ?: return arrayOf(null, null, null, null)
            //$$ return arrayOf(inv.getStack(36), inv.getStack(37), inv.getStack(38), inv.getStack(39))
            //#else
            return player?.inventory?.armorInventory ?: arrayOf(null, null, null, null)
            //#endif
        }

    inline val heldItem: ItemStack?
        //#if MC >= 1.20.1
        //$$ get() = player?.mainHandStack
        //#else
        get() = player?.heldItem
        //#endif
}