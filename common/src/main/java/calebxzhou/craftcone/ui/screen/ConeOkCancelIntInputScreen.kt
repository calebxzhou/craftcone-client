package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.Consts
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-08-13,10:47.
 */
abstract class ConeOkCancelIntInputScreen(prevScreen: Screen, screenTitle: String) : ConeOkCancelInputScreen(
    prevScreen,
    screenTitle
) {

    val intValue
        get() = inputValue.toInt()

    override fun tick() {
        okBtn.visible = inputValue.matches(Consts.regexInt)
        super.tick()
    }
}