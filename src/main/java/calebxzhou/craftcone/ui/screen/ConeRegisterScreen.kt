package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.MsgLevel
import calebxzhou.craftcone.net.protocol.MsgType
import calebxzhou.craftcone.net.protocol.account.RegisterC2SPacket
import calebxzhou.craftcone.ui.components.ConeEditBox
import calebxzhou.craftcone.ui.coneMsg
import calebxzhou.craftcone.utils.ByteBufUt.readObjectId
import io.netty.buffer.ByteBuf
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-07-17,21:30.
 */
class ConeRegisterScreen(prevSc: Screen) : ConeOkCancelInputScreen(prevSc, "注册${Mc.playerName}·请设置密码与邮箱"), OkResponseScreen {
    private val emailBox =
        ConeEditBox(w / 2 - 50, h/3, 100) .also { registerWidget(it) }


    override fun onSubmit() {
        ConeNetSender.sendPacket(RegisterC2SPacket(Mc.playerName, inputValue,emailBox.value))
    }

    override fun onOk(data: ByteBuf) {
        onClose()
        coneMsg(MsgType.Dialog, MsgLevel.Ok, "注册成功，你的UID：${data.readObjectId().toHexString()}")
    }
}
