package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.entity.ConePlayer
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.account.LoginC2SPacket
import calebxzhou.craftcone.net.protocol.account.LoginS2CPacket
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-07-17,21:30.
 */
class ConeLoginScreen(val uid:Int,prevSc: Screen) : ConeOkCancelInputScreen(prevSc,"输入密码"),S2CResponsibleScreen<LoginS2CPacket> {

    override fun onSubmit() {
        ConeNetSender.sendPacket(LoginC2SPacket(uid, inputValue))
    }
    override fun onResponse(packet: LoginS2CPacket) {
        if(packet.ok){
            val player = ConePlayer(inputValue.toInt(), Mc.playerName)
            ConePlayer.now = player
            logger.info { "$player 登录成功" }
            Mc.screen= ConeRoomSelectScreen(this)
        }else{
            ConeDialog.show(ConeDialogType.ERR,packet.data)
        }

    }
}