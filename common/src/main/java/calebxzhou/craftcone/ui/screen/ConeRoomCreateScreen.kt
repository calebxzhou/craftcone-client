package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.model.ConeUser
import calebxzhou.craftcone.net.ConeNetManager
import calebxzhou.craftcone.net.protocol.room.PlayerCreateRoomC2SPacket
import calebxzhou.craftcone.net.protocol.room.PlayerCreateRoomS2CPacket
import calebxzhou.craftcone.ui.components.ConeButton
import calebxzhou.craftcone.ui.components.ConeEditBox
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.libertorch.MC
import calebxzhou.libertorch.mc.gui.LtScreen
import com.mojang.blaze3d.platform.InputConstants
import com.mojang.blaze3d.vertex.PoseStack
import dev.architectury.platform.Platform
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Block

/**
 * Created  on 2023-07-24,22:43.
 */
class ConeRoomCreateScreen(val prevScreen: Screen): LtScreen("填写房间信息，然后按下LRCtrl键创建房间。"),S2CResponsibleScreen<PlayerCreateRoomS2CPacket> {
    private lateinit var gameModeBtn :Button
    private lateinit var rNameBox :EditBox
    private val modAmount = Platform.getMods().size
    private val blockStateAmount = Block.BLOCK_STATE_REGISTRY.size()
    private val isFabric = Platform.isFabric()
    private var isCreative = false
    override fun init() {

        rNameBox = ConeEditBox(100,40,150)
        gameModeBtn = ConeButton(250,40,80,"游戏模式：生存",::onChangeGameMode)
        rNameBox.value = ConeUser.now?.pName+"的房间"
        addRenderableWidget(gameModeBtn)
        addRenderableWidget(rNameBox)
        super.init()
    }


    override fun tick() {
        when {
            InputConstants.isKeyDown(hwnd, InputConstants.KEY_LCONTROL) && InputConstants.isKeyDown(hwnd,
                InputConstants.KEY_RCONTROL
            ) -> {
                ConeNetManager.sendPacket(PlayerCreateRoomC2SPacket(ConeUser.now!!.pid,isCreative,isFabric,blockStateAmount))
            }
        }
        super.tick()
    }
    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBg()
        font.draw(poseStack,"房间名称",50f,40f,fontColor)
        font.draw(poseStack,
            "你安装了$modAmount 个${if(isFabric)"Fabric" else "Forge"}mod，有$blockStateAmount 种方块状态。",
            50f,80f,fontColor)
        font.draw(poseStack,"房间创建完成后禁止改动任何Mod，否则存档将损坏！",50f,100f,fontColor)
        super.render(poseStack, mouseX, mouseY, partialTick)
    }

    private fun onChangeGameMode(button: Button) {
        if(!isCreative){
            gameModeBtn.message= Component.literal("游戏模式：创造")
            isCreative=true
        }else{
            gameModeBtn.message= Component.literal("游戏模式：生存")
            isCreative=false
        }
    }

    override fun onClose() {
        MC.setScreen(prevScreen)
    }

    override fun onResponse(packet: PlayerCreateRoomS2CPacket) {
        if(packet.isSuccess){
            ConeDialog.show(ConeDialogType.OK,"成功！请牢记房间ID：${packet.rid}（已自动复制。建议截图保存）")
            MC.keyboardHandler.clipboard = packet.rid.toString()
            MC.setScreen(ConeRoomJoinScreen())
        }else{
            ConeDialog.show(ConeDialogType.ERR,packet.msg)
        }

    }
}