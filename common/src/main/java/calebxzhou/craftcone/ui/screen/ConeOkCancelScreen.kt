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
    protected lateinit var okBtn : ConeButton
    protected lateinit var cancelBtn : ConeButton

    override fun init() {
        okBtn = ConeButton(width / 2-50,height-20,80,"确定"){onSubmit()}
        cancelBtn = ConeButton(width/2+50,height-20,80,"取消"){onClose()}

        addRenderableWidget(okBtn)
        addRenderableWidget(cancelBtn)
    }

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
    private fun onPressEnterKey() {
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