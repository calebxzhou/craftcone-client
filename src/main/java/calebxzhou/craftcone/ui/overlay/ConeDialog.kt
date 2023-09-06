package calebxzhou.craftcone.ui.overlay

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.protocol.MsgLevel
import calebxzhou.craftcone.ui.DefaultColors
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Overlay
import org.lwjgl.glfw.GLFW.*

/**
 * Created  on 2023-07-23,22:05.
 */
class ConeDialog private constructor(val type: MsgLevel, val msg: String) : Overlay() {
    companion object {
        fun show(type: MsgLevel, msg: String) {
            Mc.overlay = ConeDialog(type, msg)
        }
    }

    override fun render(guiGraphics: GuiGraphics, i: Int, j: Int, f: Float) {
        val w = Mc.windowWidth
        val h = Mc.windowHeight
        val baseH = h / 5
        guiGraphics.fill(0, baseH * 2, w, baseH * 3, type.color.opaque)
        guiGraphics.drawCenteredString(
            Mc.font, msg,
            w / 2,
            h / 2 - 20,
            DefaultColors.White.color.opaque
        )
        guiGraphics.drawCenteredString(
            Mc.font,
            "<右键点击：明白>",
            w / 2,
            h / 2 + 10,
            DefaultColors.White.color.opaque
        )
        val handle = Mc.hwnd
        if (glfwGetMouseButton(handle, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS) {
            Mc.overlay = null
        }
    }

}
