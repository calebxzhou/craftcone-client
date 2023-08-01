package calebxzhou.craftcone.ui.overlay

import calebxzhou.libertorch.MC
import calebxzhou.libertorch.ui.DefaultColors
import calebxzhou.libertorch.ui.LtColor
import com.mojang.blaze3d.platform.InputConstants
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Overlay

/**
 * Created  on 2023-07-23,22:05.
 */
enum class ConeDialogType(val color: LtColor) {
    INFO(DefaultColors.KleinBlue.color),
    OK(DefaultColors.OliveGreen.color),
    WARN(DefaultColors.LightYellow.color),
    ERR(DefaultColors.LightRed.color)
}

class ConeDialog private constructor(val type: ConeDialogType, val msg: String) : Overlay() {
    companion object {
        fun show(type: ConeDialogType, msg: String) {
            MC.overlay = ConeDialog(type, msg)
        }
    }

    override fun render(poseStack: PoseStack, i: Int, j: Int, f: Float) {
        val w = MC.window.guiScaledWidth
        val h = MC.window.guiScaledHeight
        val baseH = h / 5
        fill(poseStack, 0, baseH * 2, w, baseH * 3, type.color.opaque)
        drawCenteredString(
            poseStack, MC.font, msg,
            w / 2,
            h / 2 - 20,
            DefaultColors.White.color.opaque
        )
        drawCenteredString(
            poseStack, MC.font,
            "(L+R)Alt = 明白",
            w / 2,
            h / 2 + 10,
            DefaultColors.White.color.opaque
        )
        val handle = Minecraft.getInstance().window.window
        if (InputConstants.isKeyDown(handle, InputConstants.KEY_LALT) && InputConstants.isKeyDown(
                handle,
                InputConstants.KEY_RALT
            )
        ) {
            MC.overlay = null
        }
    }

}