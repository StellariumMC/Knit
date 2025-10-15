package xyz.meowing.knit.api

//#if MC != 1.16.5
//#if MC >= 1.20.1
//$$ import net.minecraft.client.MinecraftClient
//$$ import net.minecraft.client.world.ClientWorld
//$$ import net.minecraft.client.network.ClientPlayerEntity
//#else
import net.minecraft.client.Minecraft
import net.minecraft.world.World
import net.minecraft.client.entity.EntityPlayerSP
//#endif

object KnitClient {
    //#if MC >= 1.20.1
    //$$ val client: MinecraftClient = MinecraftClient.getInstance()
    //#else
    val client: Minecraft = Minecraft.getMinecraft()
    //#endif

    //#if MC >= 1.20.1
    //$$ val world: ClientWorld? get() = client.world
    //#else
    val world: World? get() = client.theWorld
    //#endif

    //#if MC >= 1.20.1
    //$$ val player: ClientPlayerEntity? get() = KnitPlayer.player
    //#else
    val player: EntityPlayerSP? get() = KnitPlayer.player
    //#endif
}
//#endif