package calebxzhou.craftcone.ui.overlay

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.protocol.MsgLevel
import calebxzhou.craftcone.ui.DefaultColors
import com.mojang.blaze3d.platform.InputConstants
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.Overlay

/**
 * Created  on 2023-07-23,22:05.
 */
class ConeDialog private constructor(val type: MsgLevel, val msg: String) : Overlay() {
    companion object {
        fun show(type: MsgLevel, msg: String) {
            Mc.overlay = ConeDialog(type, msg)
        }
    }

    override fun render(poseStack: PoseStack, i: Int, j: Int, f: Float) {
        val w = Mc.windowWidth
        val h = Mc.windowHeight
        val baseH = h / 5
        fill(poseStack, 0, baseH * 2, w, baseH * 3, type.color.opaque)
        drawCenteredString(
            poseStack, Mc.font, msg,
            w / 2,
            h / 2 - 20,
            DefaultColors.White.color.opaque
        )
        drawCenteredString(
            poseStack, Mc.font,
            "(L+R)Alt = 明白",
            w / 2,
            h / 2 + 10,
            DefaultColors.White.color.opaque
        )
        val handle = Mc.hwnd
        if (InputConstants.isKeyDown(handle, InputConstants.KEY_LALT) && InputConstants.isKeyDown(
                handle,
                InputConstants.KEY_RALT
            )
        ) {
            Mc.overlay = null
        }
    }

}