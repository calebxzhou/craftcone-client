package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.mc.Mc.blockStateAmount
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.MsgLevel
import calebxzhou.craftcone.net.protocol.MsgType
import calebxzhou.craftcone.net.protocol.room.CreateRoomC2SPacket
import calebxzhou.craftcone.ui.components.ConeButton
import calebxzhou.craftcone.ui.coneMsg
import net.minecraft.SharedConstants
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import org.quiltmc.loader.api.QuiltLoader

/**
 * Created  on 2023-07-24,22:43.
 */
class ConeRoomCreateScreen(prevSc: Screen) : ConeOkCancelInputScreen(prevSc, "创建房间 填写房间名称"),
    OkResponseScreen {
    private val gameModeBtn: ConeButton = ConeButton(w / 2, 40, 80, "游戏模式：生存") { onChangeGameMode() }
    private val modAmount = QuiltLoader.getAllMods().size
    private val isFabric = true
    private var isCreative = false
    override fun init() {
        super.init()
        inputValue = "${Mc.playerName}的房间"
        addRenderableWidget(gameModeBtn)
    }

    override fun onSubmit() {
        ConeNetSender.sendPacket(
            CreateRoomC2SPacket(inputValue, SharedConstants.VERSION_STRING, isCreative, isFabric, blockStateAmount)
        )
    }

    override fun doRender(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        guiGraphics.drawCenteredString(
            font,
            "你安装了${if (isFabric) "Fabric" else "Forge"} Mod$modAmount 个，方块状态$blockStateAmount 个。",
            width / 2, 100, textColor
        )
    }

    private fun onChangeGameMode() = if (!isCreative) {
        gameModeBtn.message = Component.literal("游戏模式：创造")
        isCreative = true
    } else {
        gameModeBtn.message = Component.literal("游戏模式：生存")
        isCreative = false
    }

    override fun onOk(data: FriendlyByteBuf) {
        val rid = data.readVarInt()
        coneMsg(MsgType.Dialog, MsgLevel.Ok, "成功创建房间ID=${rid}（请牢记此ID。已自动复制）")
        Mc.copyClipboard(rid.toString())
        onClose()
    }
}
