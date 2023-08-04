package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.account.RegisterC2SPacket
import calebxzhou.craftcone.net.protocol.account.RegisterS2CPacket
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.libertorch.MC
import calebxzhou.libertorch.mc.gui.LtScreen
import calebxzhou.rdi.ui.RdiTitleScreen
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-07-17,21:30.
 */
class ConeRegisterScreen : LtScreen("注册·请设置密码"), S2CResponsibleScreen<RegisterS2CPacket> {
    private lateinit var pwdBox : EditBox
    override fun shouldCloseOnEsc(): Boolean {
        return true
    }
    override fun init() {
        pwdBox  = EditBox(font,width/2-100,50,100,20, Component.literal("密码"))
        addRenderableWidget(pwdBox)
    }

    override fun onPressEnterKey() {
        if(pwdBox.value.isNotEmpty() &&  pwdBox.value.length<=16 && pwdBox.value.length>=6)
            ConeNetSender.sendPacket(RegisterC2SPacket(MC.user.profileId!!, MC.user.name,pwdBox.value))
        else
            ConeDialog.show(ConeDialogType.ERR,"密码长度必须6到16位，现在只有${pwdBox.value.length}位")
    }

    override fun onResponse(packet: RegisterS2CPacket){
        if(packet.ok){
            MC.setScreen(RdiTitleScreen())
        }else{
            ConeDialog.show(ConeDialogType.ERR,packet.data)
        }
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBg()
        renderUidNameAtBottom(poseStack)
        super.render(poseStack, mouseX, mouseY, partialTick)
    }
}