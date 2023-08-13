package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.account.RegisterC2SPacket
import calebxzhou.craftcone.net.protocol.account.RegisterS2CPacket
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.libertorch.MC
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.TitleScreen

/**
 * Created  on 2023-07-17,21:30.
 */
class ConeRegisterScreen(prevSc: Screen) : ConeOkCancelInputScreen(prevSc,"注册${Mc.playerName}·请设置密码"), S2CResponsibleScreen<RegisterS2CPacket> {

    override fun onSubmit() {
        ConeNetSender.sendPacket(RegisterC2SPacket(Mc.playerName,value))
    }

    override fun onResponse(packet: RegisterS2CPacket) {
        if (packet.ok) {
            MC.setScreen(ConeLoginScreen(TitleScreen()))
            ConeDialog.show(ConeDialogType.INFO, "注册成功，请牢记你的UID：${packet.data}")
        } else {
            ConeDialog.show(ConeDialogType.ERR, packet.data)
        }
    }
}