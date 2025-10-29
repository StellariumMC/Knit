package xyz.meowing.knit.api

import xyz.meowing.knit.api.KnitClient.client
import xyz.meowing.knit.api.KnitPlayer.player
import net.minecraftforge.client.ClientCommandHandler
import net.minecraft.util.ChatComponentText
import kotlin.math.roundToInt

object KnitChat {
    fun sendMessage(message: String) {
        player?.sendChatMessage(message)
    }

    fun sendCommand(command: String) {
        player?.sendChatMessage("/" + command.removePrefix("/"))
    }

    fun sendClientCommand(command: String) {
        ClientCommandHandler.instance.executeCommand(player, "/" + command.removePrefix("/"))
    }

    fun fakeMessage(message: ChatComponentText) {
        player?.addChatMessage(message)
    }

    fun fakeMessage(message: String) {
        fakeMessage(ChatComponentText(message))
    }

    fun getChatBreak(): String {
        val chatWidth = client.ingameGUI?.chatGUI?.chatWidth ?: return ""
        val dashWidth = client.fontRendererObj.getStringWidth("-")

        val repeatCount = chatWidth / dashWidth
        return "-".repeat(repeatCount)
    }

    fun getCenteredText(text: String): String {
        val chatWidth = client.ingameGUI?.chatGUI?.chatWidth ?: return text
        val textRenderer = client.fontRendererObj
        val textWidth = textRenderer.getStringWidth(text)
        if (textWidth >= chatWidth) return text
        val spaceWidth = textRenderer.getStringWidth(" ")

        val padding = ((chatWidth - textWidth) / 2f / spaceWidth).roundToInt()
        return " ".repeat(padding) + text
    }
}