package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.MsgLevel
import calebxzhou.craftcone.net.protocol.MsgType
import calebxzhou.craftcone.net.protocol.account.RegisterC2SPacket
import calebxzhou.craftcone.ui.coneMsg
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-17,21:30.
 */
class ConeRegisterScreen(prevSc: Screen) : ConeOkCancelInputScreen(prevSc, "注册${Mc.playerName}·请设置密码"),
    OkResponseScreen {

    override fun onSubmit() {
        ConeNetSender.sendPacket(RegisterC2SPacket(Mc.playerName, inputValue))
    }

    override fun onOk(data: FriendlyByteBuf) {
        onClose()
        coneMsg(MsgType.Dialog, MsgLevel.Info, "注册成功，请牢记你的UID：${data.readVarInt()}")
    }
}
