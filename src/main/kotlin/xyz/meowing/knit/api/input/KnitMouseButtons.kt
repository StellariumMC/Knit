package xyz.meowing.knit.api.input

//#if MC != 1.16.5
//#if MC >= 1.16.5
//$$ import org.lwjgl.glfw.GLFW
//$$
//$$ object KnitMouseButtons {
//$$     val LEFT: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_LEFT)
//$$     val RIGHT: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_RIGHT)
//$$     val MIDDLE: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_MIDDLE)
//$$     val BACK: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_4)
//$$     val FORWARD: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_5)
//$$     val BUTTON6: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_6)
//$$     val BUTTON7: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_7)
//$$     val BUTTON8: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_8)
//$$ }
//#else
/**
 * @author: Deftu
 */
object KnitMouseButtons {
    val LEFT: KnitMouseButton = KnitMouseButton(0)
    val RIGHT: KnitMouseButton = KnitMouseButton(1)
    val MIDDLE: KnitMouseButton = KnitMouseButton(2)
    val BACK: KnitMouseButton = KnitMouseButton(3)
    val FORWARD: KnitMouseButton = KnitMouseButton(4)
    val BUTTON6: KnitMouseButton = KnitMouseButton(5)
    val BUTTON7: KnitMouseButton = KnitMouseButton(6)
    val BUTTON8: KnitMouseButton = KnitMouseButton(7)
}
//#endif
//#endif