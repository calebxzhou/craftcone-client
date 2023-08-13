package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.ui.components.ConeButton
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-06-20,10:00.
 */
class ConeRoomSelectScreen(prevSc: Screen) : ConeOkCancelIntInputScreen(prevSc,"加入房间ID"){
    private lateinit var createBtn: ConeButton
    override fun init() {
        createBtn = ConeButton(width-70,height-30,80,"创建房间"){ Mc.screen = ConeRoomCreateScreen(this)}
        addRenderableWidget(createBtn)
    }

    override fun onSubmit() {
        Mc.screen = ConeRoomInfoScreen(this,intValue)
    }


}