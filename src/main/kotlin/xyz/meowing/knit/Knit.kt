package xyz.meowing.knit


//#if MC >= 1.20.1
//$$ import net.minecraft.client.MinecraftClient
//#else
import net.minecraft.client.Minecraft
//#endif

object Knit {
    //#if MC >= 1.20.1
    //$$ val client: MinecraftClient = MinecraftClient.getInstance()
    //#else
    val client: Minecraft = Minecraft.getMinecraft()
    //#endif
}