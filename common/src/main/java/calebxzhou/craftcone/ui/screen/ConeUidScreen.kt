package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.account.CheckPlayerExistC2SPacket
import calebxzhou.craftcone.net.protocol.account.CheckPlayerExistS2CPacket
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-08-13,10:04.
 */
class ConeUidScreen(prevScreen: Screen) : ConeOkCancelIntInputScreen(prevScreen,"输入UID登录（输入-1注册）"),S2CResponsibleScreen<CheckPlayerExistS2CPacket> {
    override fun onSubmit() {
        ConeNetSender.sendPacket(CheckPlayerExistC2SPacket(intValue))
    }

    override fun onResponse(packet: CheckPlayerExistS2CPacket) {
        if(packet.exists){
            Mc.screen = ConeLoginScreen(intValue,this)
        }else{
            Mc.screen = ConeRegisterScreen(this)
        }
    }

}