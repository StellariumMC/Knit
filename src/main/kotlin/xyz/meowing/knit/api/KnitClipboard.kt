package xyz.meowing.knit.api

import net.minecraft.client.gui.GuiScreen

/**
 * @author: Deftu
 */
object KnitClipboard {
    var string: String
        get() {
            return GuiScreen.getClipboardString()
        }

        set(value) {
            GuiScreen.setClipboardString(value)
        }
}