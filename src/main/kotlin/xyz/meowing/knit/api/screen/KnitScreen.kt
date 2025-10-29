package xyz.meowing.knit.api.screen

import xyz.meowing.knit.api.input.KnitMouse
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Mouse

@Suppress("UNUSED")
abstract class KnitScreen(screenName: String = "Knit-Screen") : GuiScreen() {
    private var lastX: Double = -1.0
    private var lastY: Double = -1.0

    open fun onInitGui() {}

    open fun onCloseGui() {}

    open fun onResizeGui() {}

    open fun onRender(mouseX: Int, mouseY: Int, deltaTicks: Float) {}

    open fun onMouseClick(mouseX: Int, mouseY: Int, button: Int) {}

    open fun onMouseRelease(mouseX: Int, mouseY: Int, button: Int) {}

    open fun onMouseScroll(horizontal: Double, vertical: Double) {}

    open fun onMouseMove(mouseX: Int, mouseY: Int) {}

    open fun onKeyType(typedChar: Char, keyCode: Int, scanCode: Int) {}

    final override fun initGui() {
        onInitGui()
        super.initGui()
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, deltaTicks: Float) {
        onRender(mouseX, mouseY, deltaTicks)

        val newX = KnitMouse.Raw.x
        val newY = KnitMouse.Raw.y
        if (newX != lastX || newY != lastY) {
            onMouseMove(newX.toInt(), newY.toInt())
            lastX = newX
            lastY = newY
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        onMouseClick(mouseX, mouseY, mouseButton)
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        onMouseRelease(mouseX, mouseY, Mouse.getEventButton())
        super.mouseReleased(mouseX, mouseY, state)
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        onKeyType(typedChar, keyCode, 0)
        super.keyTyped(typedChar, keyCode)
    }

    override fun handleMouseInput() {
        val dWheel = Mouse.getEventDWheel()

        if (dWheel != 0) {
            val verticalScroll = if (dWheel > 0) 1.0 else -1.0
            onMouseScroll(0.0, verticalScroll)
        }
        super.handleMouseInput()
    }

    override fun onGuiClosed() {
        onCloseGui()
        super.onGuiClosed()
    }

    override fun onResize(mcIn: Minecraft, w: Int, h: Int) {
        onResizeGui()
        super.onResize(mcIn, w, h)
    }
}