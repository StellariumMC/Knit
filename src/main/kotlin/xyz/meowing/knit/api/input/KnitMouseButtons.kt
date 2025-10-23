package xyz.meowing.knit.api.input

import org.lwjgl.glfw.GLFW

/**
 * @author: Deftu
 */
object KnitMouseButtons {
    val LEFT: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_LEFT)
    val RIGHT: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_RIGHT)
    val MIDDLE: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_MIDDLE)
    val BACK: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_4)
    val FORWARD: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_5)
    val BUTTON6: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_6)
    val BUTTON7: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_7)
    val BUTTON8: KnitMouseButton = KnitMouseButton(GLFW.GLFW_MOUSE_BUTTON_8)
}