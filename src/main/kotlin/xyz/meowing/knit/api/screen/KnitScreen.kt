package xyz.meowing.knit.api.screen

//#if MC != 1.16.5

import xyz.meowing.knit.api.input.KnitMouse

//#if MC == 1.8.9
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Mouse
//#endif

//#if MC >= 1.20.1
//#if FORGE-LIKE
//$$ import net.minecraft.network.chat.Component
//$$ import net.minecraft.client.gui.screens.Screen
//$$ import net.minecraft.client.gui.GuiGraphics
//$$ import net.minecraft.client.Minecraft
//#else
//$$ import net.minecraft.text.Text
//$$ import net.minecraft.client.gui.screen.Screen
//$$ import net.minecraft.client.gui.DrawContext
//$$ import net.minecraft.client.MinecraftClient
//#endif
//#endif

//#if MC >= 1.21.9
//#if FORGE-LIKE
//$$ import net.minecraft.client.input.KeyEvent
//$$ import net.minecraft.client.input.CharacterEvent
//$$ import net.minecraft.client.input.MouseButtonEvent
//#else
//$$ import net.minecraft.client.input.KeyInput
//$$ import net.minecraft.client.input.CharInput
//$$ import net.minecraft.client.gui.Click
//#endif
//#endif

@Suppress("UNUSED")
abstract class KnitScreen(screenName: String = "Knit-Screen")
    //#if MC > 1.16.5
    //#if FORGE-LIKE
    //$$ : Screen(Component.literal(screenName))
    //#else
    //$$ : Screen(Text.literal(screenName))
    //#endif
    //#elseif MC == 1.8.9
    : GuiScreen()
    //#endif
{
    private var lastX: Double = -1.0
    private var lastY: Double = -1.0

    open fun onInitGui() {}

    open fun onCloseGui() {}

    open fun onResizeGui() {}

    //#if MC >= 1.20.1
    //#if FORGE-LIKE
    //$$ open fun onRender(context: GuiGraphics, mouseX: Int, mouseY: Int, deltaTicks: Float) {}
    //#else
    //$$ open fun onRender(context: DrawContext?, mouseX: Int, mouseY: Int, deltaTicks: Float) {}
    //#endif
    //#else
    open fun onRender(mouseX: Int, mouseY: Int, deltaTicks: Float) {}
    //#endif

    open fun onMouseClick(mouseX: Int, mouseY: Int, button: Int) {}

    open fun onMouseRelease(mouseX: Int, mouseY: Int, button: Int) {}

    open fun onMouseScroll(horizontal: Double, vertical: Double) {}

    open fun onMouseMove(mouseX: Int, mouseY: Int) {}

    open fun onKeyType(typedChar: Char, keyCode: Int, scanCode: Int) {}

    //#if MC >= 1.20.1
    //$$ final override fun init() {
    //$$     onInitGui()
    //$$     super.init()
    //$$ }
    //#elseif MC == 1.8.9
    final override fun initGui() {
        onInitGui()
        super.initGui()
    }
    //#endif

    //#if MC >= 1.20.1
    //#if FORGE-LIKE
    //$$ override fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, deltaTicks: Float) {
    //$$    onRender(context, mouseX, mouseY, deltaTicks)
    //#else
    //$$ override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, deltaTicks: Float) {
    //$$    onRender(context, mouseX, mouseY, deltaTicks)
    //#endif
    //#elseif MC == 1.8.9
    override fun drawScreen(mouseX: Int, mouseY: Int, deltaTicks: Float) {
        onRender(mouseX, mouseY, deltaTicks)
    //#endif

        val newX = KnitMouse.Raw.x
        val newY = KnitMouse.Raw.y
        if (newX != lastX || newY != lastY) {
            onMouseMove(newX.toInt(), newY.toInt())
            lastX = newX
            lastY = newY
        }
    }

    //#if MC >= 1.21.9
        //#if FORGE-LIKE
        //$$ override fun mouseClicked(click: MouseButtonEvent, doubled: Boolean): Boolean {
        //#else
        //$$ override fun mouseClicked(click: Click?, doubled: Boolean): Boolean {
        //#endif
    //$$     if (click == null) return super.mouseClicked(click, doubled)
    //$$     onMouseClick(click.x.toInt(), click.y.toInt(), click.button())
    //$$     return super.mouseClicked(click, doubled)
    //$$ }
    //#elseif MC >= 1.20.1
    //$$ override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
    //$$     onMouseClick(mouseX.toInt(), mouseY.toInt(), button)
    //$$     return super.mouseClicked(mouseX, mouseY, button)
    //$$ }
    //#elseif MC == 1.8.9
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        onMouseClick(mouseX, mouseY, mouseButton)
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }
    //#endif

    //#if MC >= 1.21.9
        //#if FORGE-LIKE
        //$$ override fun mouseReleased(click: MouseButtonEvent): Boolean {
        //#else
        //$$ override fun mouseReleased(click: Click?): Boolean {
        //#endif
    //$$     if (click == null) return super.mouseReleased(click)
    //$$     onMouseRelease(click.x.toInt(), click.y.toInt(), click.button())
    //$$     return super.mouseReleased(click)
    //$$ }
    //#elseif MC >= 1.20.1
    //$$ override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
    //$$     onMouseRelease(mouseX.toInt(), mouseY.toInt(), button)
    //$$     return super.mouseReleased(mouseX, mouseY, button)
    //$$ }
    //#elseif MC == 1.8.9
    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        onMouseRelease(mouseX, mouseY, Mouse.getEventButton())
        super.mouseReleased(mouseX, mouseY, state)
    }
    //#endif

    //#if MC >= 1.21.9
        //#if FORGE-LIKE
        //$$ override fun keyPressed(input: KeyEvent): Boolean {
        //#else
        //$$ override fun keyPressed(input: KeyInput?): Boolean {
        //#endif
    //$$     if (input == null) return super.keyPressed(input)
    //$$     onKeyType('\u0000', input.key, input.scancode)
    //$$     return super.keyPressed(input)
    //$$ }
    //$$
        //#if FORGE-LIKE
        //$$ override fun charTyped(input: CharacterEvent): Boolean {
        //#else
        //$$ override fun charTyped(input: CharInput?): Boolean {
        //#endif
    //$$     if (input == null) return super.charTyped(input)
    //$$     onKeyType(input.codepoint.toChar(), 0, 0)
    //$$     return super.charTyped(input)
    //$$ }
    //#elseif MC >= 1.20.1
    //$$ override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
    //$$     onKeyType('\u0000', keyCode, scanCode)
    //$$     return super.keyPressed(keyCode, scanCode, modifiers)
    //$$ }
    //$$
    //$$ override fun charTyped(chr: Char, modifiers: Int): Boolean {
    //$$      onKeyType(chr, 0, 0)
    //$$     return super.charTyped(chr, modifiers)
    //$$ }
    //#elseif MC == 1.8.9
    override fun keyTyped(typedChar: Char, keyCode: Int) {
        onKeyType(typedChar, keyCode, 0)
        super.keyTyped(typedChar, keyCode)
    }
    //#endif

    //#if MC >= 1.21.5
    //$$ override fun mouseScrolled(mouseX: Double, mouseY: Double, horizontalAmount: Double, verticalAmount: Double): Boolean {
    //$$     onMouseScroll(horizontalAmount, verticalAmount)
    //$$     return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)
    //$$ }
    //#elseif MC == 1.20.1
    //$$ override fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double): Boolean {
    //$$    onMouseScroll(0.0, amount)
    //$$    return super.mouseScrolled(mouseX, mouseY, amount)
    //$$ }
    //#elseif MC == 1.8.9
    override fun handleMouseInput() {
        val dWheel = Mouse.getEventDWheel()

        if (dWheel != 0) {
            val verticalScroll = if (dWheel > 0) 1.0 else -1.0
            onMouseScroll(0.0, verticalScroll)
        }
        super.handleMouseInput()
    }
    //#endif

    //#if MC >= 1.20.1
    //#if FORGE-LIKE
    //$$ override fun onClose() {
    //$$    onCloseGui()
    //$$    super.onClose()
    //$$ }
    //#else
    //$$ override fun close() {
    //$$     onCloseGui()
    //$$     super.close()
    //$$ }
    //#endif
    //#elseif MC == 1.8.9
    override fun onGuiClosed() {
        onCloseGui()
        super.onGuiClosed()
    }
    //#endif

    //#if MC >= 1.20.1
    //#if FORGE-LIKE
    //$$ override fun resize(client: Minecraft, width: Int, height: Int) {
    //#else
    //$$ override fun resize(client: MinecraftClient?, width: Int, height: Int) {
    //#endif
    //$$     onResizeGui()
    //$$     super.resize(client, width, height)
    //$$ }
    //#elseif MC == 1.8.9
    override fun onResize(mcIn: Minecraft, w: Int, h: Int) {
        onResizeGui()
        super.onResize(mcIn, w, h)
    }
    //#endif
}
//#endif