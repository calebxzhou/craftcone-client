package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.entity.ConePlayer
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.account.LoginC2SPacket
import calebxzhou.craftcone.ui.components.ConeButton
import calebxzhou.craftcone.ui.components.ConeEditBox
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-17,21:30.
 */
class ConeLoginScreen(prevSc: Screen) : ConeOkCancelIntInputScreen(prevSc, "输入UID和密码"), OkResponseScreen {
    private val pwdBox = ConeEditBox(w / 2 - 100, h / 3, 200).also { registerWidget(it) }
    private val regBtn = ConeButton(w / 2 - 50, h - 20, 100, "注册") { Mc.screen = ConeRegisterScreen(this) }.also { registerWidget(it) }

    override fun onSubmit() {
        ConeNetSender.sendPacket(LoginC2SPacket(intValue, pwdBox.value))
    }

    override fun onOk(data: FriendlyByteBuf) {
        val player = ConePlayer(intValue, Mc.playerName)
        ConePlayer.now = player
        logger.info { "$player 登录成功" }
        Mc.screen = ConeRoomSelectScreen(this)
    }
}