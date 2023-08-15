package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.room.RoomInfoC2SPacket
import calebxzhou.craftcone.ui.components.ConeButton
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-06-20,10:00.
 */
class ConeRoomSelectScreen(prevSc: Screen) : ConeOkCancelIntInputScreen(prevSc,"加入房间ID"){
    private val createBtn = ConeButton(w/2-50,h-20,100,"创建房间"){ Mc.screen = ConeRoomCreateScreen(this)}
    override fun init() {
        super.init()
        addRenderableWidget(createBtn)
    }

    override fun onSubmit() {
        ConeNetSender.sendPacket(RoomInfoC2SPacket(intValue))
    }


}