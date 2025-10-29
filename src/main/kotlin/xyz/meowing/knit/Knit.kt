package xyz.meowing.knit

import net.minecraft.client.Minecraft

object Knit {
    @Deprecated("Use KnitClient.client")
    val client: Minecraft = Minecraft.getMinecraft()
}