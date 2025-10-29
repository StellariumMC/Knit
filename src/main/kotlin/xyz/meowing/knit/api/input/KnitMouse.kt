package xyz.meowing.knit.api.input

import xyz.meowing.knit.api.render.KnitResolution
import kotlin.math.max
import org.lwjgl.input.Mouse

/**
 * @author: Deftu
 */
object KnitMouse {
    object Raw {
        val x: Double
            get() {
                return Mouse.getX().toDouble()
            }

        val y: Double
            get() {
                return KnitResolution.windowHeight - Mouse.getY().toDouble() - 1
            }
    }

    object Scaled {
        val x: Double
            get() = Raw.x * KnitResolution.scaledWidth / max(1, KnitResolution.windowWidth)
        val y: Double
            get() = Raw.y * KnitResolution.scaledWidth / max(1, KnitResolution.windowWidth)
    }

    var isCursorGrabbed: Boolean
        get() {
            return Mouse.isGrabbed()
        }

        set(value) {
            Mouse.setGrabbed(value)
        }

    fun isMouseButton(code: Int): Boolean {
        return code in 0..8
    }

    fun isPressed(code: Int): Boolean {
        if (!isMouseButton(code)) return false

        return Mouse.isButtonDown(code + 100)
    }
}