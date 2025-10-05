package xyz.meowing.knit.api.screen

//#if MC != 1.16.5
import xyz.meowing.knit.api.input.KnitMouse
//#if MC == 1.8.9
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import org.lwjgl.input.Mouse
//#endif

//#if MC >= 1.20.1
//$$ import net.minecraft.text.Text
//$$ import net.minecraft.client.gui.screen.Screen
//$$ import net.minecraft.client.gui.DrawContext
//$$ import net.minecraft.client.MinecraftClient
//#endif

//#if MC >= 1.21.9
//$$ import net.minecraft.client.input.KeyInput
//$$ import net.minecraft.client.input.CharInput
//$$ import net.minecraft.client.gui.Click
//#endif

@Suppress("UNUSED")
abstract class KnitScreen
    //#if MC > 1.16.5
    //$$ : Screen(Text.of(""))
    //#elseif MC == 1.8.9
    : GuiScreen()
    //#endif
{
    private var lastX: Double = -1.0
    private var lastY: Double = -1.0

    open fun onInitGui() {}

    open fun onCloseGui() {}

    open fun onResizeGui() {}

    open fun onRender() {}

    open fun onMouseClick(mouseX: Int, mouseY: Int, button: Int) {}

    open fun onMouseRelease(mouseX: Int, mouseY: Int, button: Int) {}

    open fun onMouseScroll(horizontal: Double, vertical: Double) {}

    open fun onMouseMove() {}

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
    //$$ override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, deltaTicks: Float) {
    //#elseif MC == 1.8.9
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
    //#endif
        onRender()

        if (KnitMouse.Raw.y != lastX || KnitMouse.Raw.x != lastY) {
            onMouseMove()
            lastX = KnitMouse.Raw.x
            lastY = KnitMouse.Raw.y
        }
    }

    //#if MC >= 1.21.9
    //$$ override fun mouseClicked(click: Click?, doubled: Boolean): Boolean {
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
    //$$ override fun mouseReleased(click: Click?): Boolean {
    //$$     if (click == null) return super.mouseReleased(click)
    //$$     onMouseClick(click.x.toInt(), click.y.toInt(), click.button())
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
    //$$ override fun keyPressed(input: KeyInput?): Boolean {
    //$$     if (input == null) return super.keyPressed(input)
    //$$     onKeyType('\u0000', input.keycode, input.scancode)
    //$$     return super.keyPressed(input)
    //$$ }
    //$$
    //$$ override fun charTyped(input: CharInput?): Boolean {
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
    //$$ override fun close() {
    //$$     onCloseGui()
    //$$     super.close()
    //$$ }
    //#elseif MC == 1.8.9
    override fun onGuiClosed() {
        onCloseGui()
        super.onGuiClosed()
    }
    //#endif

    //#if MC >= 1.20.1
    //$$ override fun resize(client: MinecraftClient?, width: Int, height: Int) {
    //$$     onResizeGui()
    //$$     super.resize(client, width, height)
    //$$ }
    //#elseif MC == 1.8.9
    override fun onResize(mcIn: Minecraft?, w: Int, h: Int) {
        onResizeGui()
        super.onResize(mcIn, w, h)
    }
    //#endif
}
//#endif