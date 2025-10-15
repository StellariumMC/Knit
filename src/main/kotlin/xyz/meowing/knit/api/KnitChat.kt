package xyz.meowing.knit.api

//#if MC != 1.16.5
import xyz.meowing.knit.api.KnitClient.client
import xyz.meowing.knit.api.KnitPlayer.player
import kotlin.math.roundToInt

//#if MC >= 1.20.1
//$$ import net.minecraft.text.Text
//#else
import net.minecraftforge.client.ClientCommandHandler
import net.minecraft.util.ChatComponentText
//#endif

object KnitChat {
    fun sendMessage(message: String) {
        //#if MC >= 1.20.1
        //$$ player?.networkHandler?.sendChatMessage(message)
        //#else
        player?.sendChatMessage(message)
        //#endif
    }

    fun sendCommand(command: String) {
        //#if MC >= 1.20.1
        //$$ player.networkHandler?.sendChatCommand(command)
        //#else
        player?.sendChatMessage("/" + command.removePrefix("/"))
        //#endif
    }

    //#if MC < 1.16.5
    fun sendClientCommand(command: String) {
        ClientCommandHandler.instance.executeCommand(player, "/" + command.removePrefix("/"))
    }
    //#endif

    //#if MC >= 1.20.1
    //$$ fun fakeMessage(message: Text) {
    //$$    client.inGameHud.chatHud.addMessage(message)
    //$$ }
    //#else
    fun fakeMessage(message: ChatComponentText) {
        player?.addChatMessage(message)
    }
    //#endif

    fun fakeMessage(message: String) {
        //#if MC >= 1.20.1
        //$$ fakeMessage(Text.literal(message))
        //#else
        fakeMessage(ChatComponentText(message))
        //#endif
    }

    fun getChatBreak(): String {
        //#if MC >= 1.20.1
        //$$ val chatWidth = client.inGameHud?.chatHud?.width ?: return ""
        //$$ val textRenderer = client.textRenderer
        //$$ val dashWidth = textRenderer.getWidth("-")
        //#else
        val chatWidth = client.ingameGUI?.chatGUI?.chatWidth ?: return ""
        val dashWidth = client.fontRendererObj.getStringWidth("-")
        //#endif

        val repeatCount = chatWidth / dashWidth
        return "-".repeat(repeatCount)
    }

    fun getCenteredText(text: String): String {
        //#if MC >= 1.20.1
        //$$ val chatWidth = client.inGameHud?.chatHud?.width ?: return text
        //$$ val textRenderer = client.textRenderer
        //$$ val textWidth = textRenderer.getWidth(text)
        //$$ if (textWidth >= chatWidth) return text
        //$$ val spaceWidth = textRenderer.getWidth(" ")
        //#else
        val chatWidth = client.ingameGUI?.chatGUI?.chatWidth ?: return text
        val textRenderer = client.fontRendererObj
        val textWidth = textRenderer.getStringWidth(text)
        if (textWidth >= chatWidth) return text
        val spaceWidth = textRenderer.getStringWidth(" ")
        //#endif

        val padding = ((chatWidth - textWidth) / 2f / spaceWidth).roundToInt()
        return " ".repeat(padding) + text
    }
}
//#endif