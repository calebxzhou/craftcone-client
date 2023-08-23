package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.ui.components.ConeEditBox
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-08-12,17:14.
 */
abstract class ConeOkCancelInputScreen(prevScreen: Screen, screenTitle: String) :
    ConeOkCancelScreen(prevScreen, screenTitle) {
    val editBox = ConeEditBox(w / 2 - 100, h / 2, 200).also { registerWidget(it) }
    var inputValue: String
        set(str) {
            editBox.value = str
        }
        get() = editBox.value.trim()

    override fun tick() {
        super.tick()
        okBtn.visible = editBox.value.isNotBlank()
    }
}
