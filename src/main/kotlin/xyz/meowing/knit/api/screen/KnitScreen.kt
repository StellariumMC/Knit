package xyz.meowing.knit.api.screen

import xyz.meowing.knit.api.input.KnitMouse
import xyz.meowing.knit.api.text.KnitText

//#if MC >= 1.20.1
    //#if FORGE-LIKE
    //$$ import net.minecraft.client.gui.screens.Screen
    //$$ import net.minecraft.client.gui.GuiGraphics
    //$$ import net.minecraft.client.Minecraft
    //#else
    import net.minecraft.client.gui.screen.Screen
    import net.minecraft.client.gui.DrawContext
    import net.minecraft.client.MinecraftClient
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
abstract class KnitScreen(screenName: String = "Knit-Screen") : Screen(KnitText.literal(screenName).toVanilla()) {
    private var lastX: Double = -1.0
    private var lastY: Double = -1.0

    open fun onInitGui() {}

    open fun onCloseGui() {}

    open fun onResizeGui() {}

    //#if FORGE-LIKE
    //$$ open fun onRender(context: GuiGraphics, mouseX: Int, mouseY: Int, deltaTicks: Float) {}
    //#else
    open fun onRender(context: DrawContext?, mouseX: Int, mouseY: Int, deltaTicks: Float) {}
    //#endif

    open fun onMouseClick(mouseX: Int, mouseY: Int, button: Int) {}

    open fun onMouseRelease(mouseX: Int, mouseY: Int, button: Int) {}

    open fun onMouseScroll(horizontal: Double, vertical: Double) {}

    open fun onMouseMove(mouseX: Int, mouseY: Int) {}

    open fun onKeyType(typedChar: Char, keyCode: Int, scanCode: Int) {}

    final override fun init() {
        onInitGui()
        super.init()
    }

    //#if FORGE-LIKE
    //$$ override fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, deltaTicks: Float) {
    //$$    onRender(context, mouseX, mouseY, deltaTicks)
    //#else
    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, deltaTicks: Float) {
       onRender(context, mouseX, mouseY, deltaTicks)
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
    //#else
    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        onMouseClick(mouseX.toInt(), mouseY.toInt(), button)
        return super.mouseClicked(mouseX, mouseY, button)
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
    //#else
    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        onMouseRelease(mouseX.toInt(), mouseY.toInt(), button)
        return super.mouseReleased(mouseX, mouseY, button)
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
    //#else
    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        onKeyType('\u0000', keyCode, scanCode)
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun charTyped(chr: Char, modifiers: Int): Boolean {
        onKeyType(chr, 0, 0)
        return super.charTyped(chr, modifiers)
    }
    //#endif

    //#if MC >= 1.21.5
    override fun mouseScrolled(mouseX: Double, mouseY: Double, horizontalAmount: Double, verticalAmount: Double): Boolean {
        onMouseScroll(horizontalAmount, verticalAmount)
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)
    }
    //#elseif MC == 1.20.1
    //$$ override fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double): Boolean {
    //$$    onMouseScroll(0.0, amount)
    //$$    return super.mouseScrolled(mouseX, mouseY, amount)
    //$$ }
    //#endif

    //#if FORGE-LIKE
    //$$ override fun onClose() {
    //$$    onCloseGui()
    //$$    super.onClose()
    //$$ }
    //#else
    override fun close() {
        onCloseGui()
        super.close()
    }
    //#endif

    //#if FORGE-LIKE
    //$$ override fun resize(client: Minecraft, width: Int, height: Int) {
    //#else
    override fun resize(client: MinecraftClient?, width: Int, height: Int) {
    //#endif
        onResizeGui()
        super.resize(client, width, height)
    }
}