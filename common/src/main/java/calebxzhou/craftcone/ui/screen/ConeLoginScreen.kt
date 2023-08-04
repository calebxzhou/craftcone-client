package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.entity.ConeUser
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.account.LoginC2SPacket
import calebxzhou.craftcone.net.protocol.account.LoginS2CPacket
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.libertorch.MC
import calebxzhou.libertorch.mc.gui.LtScreen
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-07-17,21:30.
 */
class ConeLoginScreen : LtScreen("输入密码"),S2CResponsibleScreen<LoginS2CPacket> {
    private lateinit var pwdBox : EditBox
    override fun shouldCloseOnEsc(): Boolean {
        return true
    }

    override fun init() {
        pwdBox  = EditBox(font,width/2 - 50,50,100,20, Component.literal("密码"))
        addRenderableWidget(pwdBox)
    }

    override fun onPressEnterKey() {
        ConeNetSender.sendPacket(LoginC2SPacket(MC.user.profileId!!, pwdBox.value))
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBg()
        renderUidNameAtBottom(poseStack)
        super.render(poseStack, mouseX, mouseY, partialTick)
    }

    override fun onResponse(packet: LoginS2CPacket) {
        if(packet.ok){
            ConeUser.now = ConeUser(MC.user.profileId!!,pwdBox.value, MC.user.name)
            MC.setScreen(ConeRoomJoinScreen())
        }else{
            ConeDialog.show(ConeDialogType.ERR,packet.data)
        }

    }
}