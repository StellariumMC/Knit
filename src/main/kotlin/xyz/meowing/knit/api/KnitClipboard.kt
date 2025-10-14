package xyz.meowing.knit.api

import net.minecraft.client.gui.GuiScreen

//#if MC <= 1.12.2
//$$ import xyz.meowing.knit.Knit
//#endif

/**
 * @author: Deftu
 */
object KnitClipboard {
    var string: String
        get() {
            //#if MC >= 1.16.5
            //$$ return Knit.client.keyboard.clipboard
            //#else
            return GuiScreen.getClipboardString()
            //#endif
        }
        set(value) {
            //#if MC >= 1.16.5
            //$$ Knit.client.keyboard.clipboard = value
            //#else
            GuiScreen.setClipboardString(value)
            //#endif
        }
}