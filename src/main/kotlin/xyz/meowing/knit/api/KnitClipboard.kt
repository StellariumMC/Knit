package xyz.meowing.knit.api

//#if MC != 1.16.5

//#if MC >= 1.16.5
//$$ import xyz.meowing.knit.Knit
//#else
import net.minecraft.client.gui.GuiScreen
//#endif

/**
 * @author: Deftu
 */
object KnitClipboard {
    var string: String
        get() {
            //#if MC >= 1.16.5
            //$$ return KnitClient.client.keyboard.clipboard
            //#else
            return GuiScreen.getClipboardString()
            //#endif
        }
        set(value) {
            //#if MC >= 1.16.5
            //$$ KnitClient.client.keyboard.clipboard = value
            //#else
            GuiScreen.setClipboardString(value)
            //#endif
        }
}
//#endif