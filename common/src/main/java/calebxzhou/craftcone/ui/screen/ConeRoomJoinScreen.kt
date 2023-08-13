package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.entity.Room
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.room.PlayerJoinRoomC2SPacket
import calebxzhou.craftcone.ui.components.ConeButton
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.craftcone.utils.blockStateAmount
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-06-20,10:00.
 */
class ConeRoomJoinScreen(prevSc: Screen) : ConeOkCancelIntInputScreen(prevSc,"加入房间ID"),S2CResponsibleScreen<Room> {
    private lateinit var createBtn: ConeButton
    private lateinit var myBtn: ConeButton
    override fun init() {
        createBtn = ConeButton(width-70,height-30,80,"创建房间"){ Mc.screen = ConeRoomCreateScreen(this)}
        myBtn = ConeButton(width-70,height-50,80,"我的房间"){Mc.screen = ConeRoomMineScreen(this)}
        addRenderableWidget(createBtn)
        addRenderableWidget(myBtn)
    }
    override fun onResponse(room: Room) {
        if (blockStateAmount != room.blockStateAmount) {
            ConeDialog.show(ConeDialogType.ERR,"方块状态数量不一致：您${blockStateAmount}个/房间${room.blockStateAmount}个。检查Mod列表！")
            return
        }
        Room.load(room)
    }


    override fun onSubmit() {
        ConeNetSender.sendPacket(PlayerJoinRoomC2SPacket(intValue))
    }


}