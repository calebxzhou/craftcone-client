package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.entity.Room
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.room.GetRoomInfoC2SPacket
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.craftcone.ui.overlay.coneDialog
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiComponent
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-08-13,15:21.
 */
class ConeRoomInfoScreen(prevScreen: Screen, private val rid: Int) :ConeOkCancelScreen(prevScreen, "房间$rid 的信息"), S2CResponsibleScreen<Room> {
    var room :Room?=null
    init {
        ConeNetSender.sendPacket(GetRoomInfoC2SPacket(rid))
    }

    override fun tick() {
        okBtn.visible= room!=null

        super.tick()
    }


    override fun doRender(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        room?.let { room->
            GuiComponent.drawCenteredString(poseStack,font,"名称：${room.name}",width/2,20,fontColor)
            GuiComponent.drawCenteredString(poseStack,font,"mc版本：${room.mcVersion}",width/2,20*2,fontColor)
            GuiComponent.drawCenteredString(poseStack,font,"模式：${if(room.isCreative)"创造" else "生存"}",width/2,20*3,fontColor)
            GuiComponent.drawCenteredString(poseStack,font,"创建时间：${room.createTimeStr}",width/2,20*4,fontColor)
            GuiComponent.drawCenteredString(poseStack,font,"mod加载器：${if(room.isFabric)"Fabric" else "Forge "}",width/2,20*5,fontColor)
        }
    }
    override fun onSubmit() {
        room?.let {
            screenTitle="加入房间$rid 中。。。。。。"
            Room.load(it)
        }?:let{
            coneDialog(ConeDialogType.ERR){
                "没有获取到房间信息，不可加入此房间"
            }
            return
        }
    }

    override fun onResponse(packet: Room) {
        room=packet
    }
}