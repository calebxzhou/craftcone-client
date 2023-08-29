package calebxzhou.craftcone.ui.screen

import net.minecraft.client.gui.screens.Screen
import org.bson.types.ObjectId

/**
 * Created  on 2023-08-13,10:47.
 */
abstract class ConeOkCancelIdInputScreen(prevScreen: Screen, screenTitle: String) : ConeOkCancelInputScreen(
    prevScreen,
    screenTitle
) {

    val idValue
        get() = ObjectId(inputValue)

    override fun tick() {
        super.tick()
        okBtn.visible = inputValue.length == 24
    }
}
