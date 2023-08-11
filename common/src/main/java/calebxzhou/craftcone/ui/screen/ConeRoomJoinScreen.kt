package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.misc.Room
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.room.PlayerJoinRoomC2SPacket
import calebxzhou.craftcone.net.protocol.room.RoomInfoS2CPacket
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.craftcone.utils.blockStateAmount
import calebxzhou.libertorch.MC
import calebxzhou.libertorch.mc.gui.LtScreen
import calebxzhou.libertorch.mc.gui.UuidEditBox
import com.mojang.blaze3d.platform.InputConstants
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import java.util.*

/**
 * Created  on 2023-06-20,10:00.
 */
class ConeRoomJoinScreen() : LtScreen("输入房间ID"),S2CResponsibleScreen<RoomInfoS2CPacket> {
    private lateinit var createBtn: Button
    private lateinit var myBtn: Button
    private lateinit var roomIdBox : UuidEditBox
    override fun init() {
        createBtn = Button(180,height-30,80,20,Component.literal("创建房间"),::onCreateRoom)
        myBtn = Button(260,height-30,80,20,Component.literal("我的房间"),::onMyRoom)
        //00000000-0000-0000-0000-000000000000
        roomIdBox  = UuidEditBox(width/4,50,250,20)
        addRenderableWidget(createBtn)
        addRenderableWidget(roomIdBox)
    }

    private fun onMyRoom(button: Button) {
        MC.setScreen(ConeRoomMyScreen(this))
    }

    private fun onCreateRoom(button: Button) {
        MC.setScreen(ConeRoomCreateScreen(this))
    }

    private fun onJoinRoom() {
        ConeNetSender.sendPacket(PlayerJoinRoomC2SPacket(UUID.fromString(roomIdBox.value)))

    }
    override fun onResponse(packet: RoomInfoS2CPacket) {

        /*if(blockStateAmount==0){
            ConeRoomManager.initialize(roomIdBox.value)
            return
        }*/
        if (blockStateAmount != packet.blockStateAmount) {
            ConeDialog.show(ConeDialogType.ERR,"方块状态数量不一致：您${blockStateAmount}个/房间${packet.blockStateAmount}个。检查Mod列表！")
            return
        }
        Room.now = packet
        Room.loadRoom(roomIdBox.value,packet.isCreative,packet.seed)

    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBg()

        super.render(poseStack, mouseX, mouseY, partialTick)

    }



    override fun tick() {
        comment = if(roomIdBox.isValueValid){
            "按Enter加入此房间"
        }else{
            "输入房间ID"
        }
        val handle = Minecraft.getInstance().window.window
        when {
            InputConstants.isKeyDown(handle, InputConstants.KEY_RETURN) || InputConstants.isKeyDown(
                handle,
                InputConstants.KEY_NUMPADENTER
            ) -> {
                if(roomIdBox.isValueValid)
                    onJoinRoom()
            }
        }
        super.tick()
    }

    override fun shouldCloseOnEsc(): Boolean {
        return true
    }



}