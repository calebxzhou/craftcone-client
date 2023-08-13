package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.room.PlayerCreateRoomC2SPacket
import calebxzhou.craftcone.net.protocol.room.PlayerCreateRoomS2CPacket
import calebxzhou.craftcone.ui.components.ConeButton
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.craftcone.utils.blockStateAmount
import calebxzhou.libertorch.MC
import com.mojang.blaze3d.vertex.PoseStack
import dev.architectury.platform.Platform
import net.minecraft.SharedConstants
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-07-24,22:43.
 */
class ConeRoomCreateScreen(prevSc: Screen): ConeOkCancelInputScreen(prevSc,"创建房间 填写房间名称"),S2CResponsibleScreen<PlayerCreateRoomS2CPacket> {
    private lateinit var gameModeBtn :Button
    private val modAmount = Platform.getMods().size
    private val isFabric = Platform.isFabric()
    private var isCreative = false
    override fun init() {
        gameModeBtn = ConeButton(250,40,80,"游戏模式：生存") { onChangeGameMode() }
        inputValue = "${Mc.playerName}的房间"
        addRenderableWidget(gameModeBtn)
        super.init()
    }

    override fun onSubmit() {
        ConeNetSender.sendPacket(
            PlayerCreateRoomC2SPacket(inputValue,SharedConstants.VERSION_STRING,isCreative,isFabric, blockStateAmount))
    }

    override fun doRender(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        font.draw(poseStack,
            "${if(isFabric)"Fabric" else "Forge"}mod数量$modAmount，方块状态数量$blockStateAmount",
            50f,80f,fontColor)
    }

    private fun onChangeGameMode() = if(!isCreative){
        gameModeBtn.message= Component.literal("游戏模式：创造")
        isCreative=true
    }else{
        gameModeBtn.message= Component.literal("游戏模式：生存")
        isCreative=false
    }

    override fun onResponse(packet: PlayerCreateRoomS2CPacket) {
        if(packet.ok){
            val rid = packet.data
            ConeDialog.show(ConeDialogType.OK,"成功！请牢记房间ID：${rid}（已自动复制。建议截图保存）")
            MC.keyboardHandler.clipboard =  rid
            onClose()
        }else{
            ConeDialog.show(ConeDialogType.ERR,packet.data)
        }

    }
}