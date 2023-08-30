package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.room.DelRoomC2SPacket
import calebxzhou.craftcone.net.protocol.room.GetMyRoomC2SPacket
import calebxzhou.craftcone.net.protocol.room.GetRoomC2SPacket
import calebxzhou.craftcone.ui.components.ConeButton
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-06-20,10:00.
 */
class ConeRoomSelectScreen(prevSc: Screen) : ConeOkCancelIdInputScreen(prevSc, "加入房间ID") {
    private val myRoomBtn =
        ConeButton(w / 2 - 90, h - 20, 60, "我的房间") { ConeNetSender.sendPacket(GetMyRoomC2SPacket()) }
    private val createBtn = ConeButton(w / 2 - 30, h - 20, 60, "创建房间") { Mc.screen = ConeRoomCreateScreen(this) }
    private val delBtn = ConeButton(w / 2 + 30, h - 20, 60, "删除房间") {
        Mc.screen = ConeConfirmScreen(this, "确定删除你的房间吗？此操作不可恢复！") {
            ConeNetSender.sendPacket(DelRoomC2SPacket())
        }
    }
    override fun init() {
        super.init()
        addRenderableWidget(createBtn)
        addRenderableWidget(delBtn)
        addRenderableWidget(myRoomBtn)
    }

    override fun onSubmit() {
        ConeNetSender.sendPacket(GetRoomC2SPacket(idValue))
    }


}
