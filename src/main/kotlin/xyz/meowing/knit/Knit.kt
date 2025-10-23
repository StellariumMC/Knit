package xyz.meowing.knit

import net.minecraft.client.MinecraftClient
import xyz.meowing.knit.api.events.EventBus

object Knit {
    @Deprecated("Use KnitClient.client")
    val client: MinecraftClient = MinecraftClient.getInstance()

    @JvmStatic
    val EventBus = EventBus()
}