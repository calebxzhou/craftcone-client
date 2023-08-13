package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.ui.components.ConeEditBox
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-08-12,17:14.
 */
abstract class ConeOkCancelInputScreen(prevScreen: Screen, screenTitle: String) : ConeOkCancelScreen(prevScreen, screenTitle) {
    protected lateinit var editBox: ConeEditBox
    var value: String
        set(str) {
            editBox.value = str
        }
        get() = editBox.value.trim()
    override fun init() {
        editBox = ConeEditBox(width/2-50,height/2,100)
        addRenderableWidget(editBox)
        super.init()
    }

    override fun tick() {
        okBtn.visible = editBox.value.isNotBlank()
        super.tick()
    }
}