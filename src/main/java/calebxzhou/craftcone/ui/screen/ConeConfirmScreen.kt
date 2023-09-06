package calebxzhou.craftcone.ui.screen

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-08-30,8:25.
 */
class ConeConfirmScreen(prevScreen: Screen, val text: String, val doOnSubmit: () -> Unit) :
    ConeOkCancelScreen(prevScreen, "") {
    override fun onSubmit() {
        doOnSubmit()
    }

    override fun doRender(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        guiGraphics.drawCenteredString(
            font,
            text,
            w / 2, h / 2, textColor
        )
    }
}
