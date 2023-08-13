package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.ui.components.ConeButton
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-06-20,10:00.
 */
class ConeRoomSelectScreen(prevSc: Screen) : ConeOkCancelIntInputScreen(prevSc,"加入房间ID"){
    private val createBtn = ConeButton(w-50,h-60,100,"创建房间"){ Mc.screen = ConeRoomCreateScreen(this)}
    override fun init() {
        super.init()
        addRenderableWidget(createBtn)
    }

    override fun onSubmit() {
        Mc.screen = ConeRoomInfoScreen(this,intValue)
    }


}