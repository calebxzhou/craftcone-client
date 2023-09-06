package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.entity.ConePlayer
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.account.LoginByNameC2SPacket
import calebxzhou.craftcone.ui.components.ConeButton
import calebxzhou.craftcone.utils.ByteBufUt.readObjectId
import io.netty.buffer.ByteBuf
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-07-17,21:30.
 */
class ConeLoginScreen(prevSc: Screen) : ConeOkCancelInputScreen(prevSc, "登录${Mc.playerName}·输入密码"), OkResponseScreen {
    private val regBtn =
        ConeButton(w / 2 - 50, h - 20, 100, "新建账户") { Mc.screen = ConeRegisterScreen(this) }.also { registerWidget(it) }

    override fun onSubmit() {
        ConeNetSender.sendPacket(LoginByNameC2SPacket(Mc.playerName, inputValue))
    }

    override fun onOk(data: ByteBuf) {
        val player = ConePlayer(data.readObjectId(), Mc.playerName)
        ConePlayer.now = player
        logger.info("$player 登录成功")
        Mc.screen = ConeRoomSelectScreen(this)
    }
}
