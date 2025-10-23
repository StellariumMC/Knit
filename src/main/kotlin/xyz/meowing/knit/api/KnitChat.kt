package xyz.meowing.knit.api

import net.minecraft.text.Text
import xyz.meowing.knit.api.KnitClient.client
import xyz.meowing.knit.api.KnitPlayer.player
import xyz.meowing.knit.api.text.internal.TextBuilder
import kotlin.math.roundToInt

object KnitChat {
    fun sendMessage(message: String) {
        player?.networkHandler?.sendChatMessage(message)
    }

    fun sendCommand(command: String) {
        player?.networkHandler?.sendChatCommand(command.removePrefix("/"))
    }

    fun fakeMessage(message: TextBuilder) {
        client.inGameHud.chatHud.addMessage(message.toVanilla())
    }

    fun fakeMessage(message: Text) {
        client.inGameHud.chatHud.addMessage(message)
    }

    fun fakeMessage(message: String) {
        fakeMessage(Text.literal(message))
    }

    fun getChatBreak(): String {
        val chatWidth = client.inGameHud?.chatHud?.width ?: return ""
        val textRenderer = client.textRenderer
        val dashWidth = textRenderer.getWidth("-")

        val repeatCount = chatWidth / dashWidth
        return "-".repeat(repeatCount)
    }

    fun getCenteredText(text: String): String {
        val chatWidth = client.inGameHud?.chatHud?.width ?: return text
        val textRenderer = client.textRenderer
        val textWidth = textRenderer.getWidth(text)
        if (textWidth >= chatWidth) return text
        val spaceWidth = textRenderer.getWidth(" ")

        val padding = ((chatWidth - textWidth) / 2f / spaceWidth).roundToInt()
        return " ".repeat(padding) + text
    }
}