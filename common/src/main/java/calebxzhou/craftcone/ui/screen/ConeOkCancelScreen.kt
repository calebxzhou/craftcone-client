package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.ui.components.ConeButton
import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-08-12,16:49.
 */
abstract class ConeOkCancelScreen(
    //前画面
    val prevScreen: Screen,
    screenTitle: String) : ConeScreen(screenTitle) {

    val okBtn = ConeButton(0,h-20,100,"确定"){onSubmit()}.also { registerWidget(it) }
    val cancelBtn = ConeButton(w-100,h-20,100,"取消"){onClose()}.also { registerWidget(it) }

    override fun tick() {
        when {
            //当按下回车键
            InputConstants.isKeyDown(hwnd, InputConstants.KEY_RETURN) || InputConstants.isKeyDown(hwnd,
                InputConstants.KEY_NUMPADENTER
            ) -> {
                onPressEnterKey()
            }
        }

        super.tick()
    }
    open fun onPressEnterKey() {
        if(okBtn.visible)
            onSubmit()
    }
    abstract fun onSubmit()

    override fun shouldCloseOnEsc(): Boolean {
        return true
    }

    override fun onClose() {
        Mc.screen = prevScreen
    }

}