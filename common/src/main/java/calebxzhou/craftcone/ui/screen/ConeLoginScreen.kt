package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.net.ConeNetManager
import calebxzhou.craftcone.net.protocol.account.LoginC2SPacket
import calebxzhou.craftcone.net.protocol.account.LoginS2CPacket
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.libertorch.MC
import calebxzhou.libertorch.mc.gui.LtScreen
import com.mojang.blaze3d.platform.InputConstants
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-07-17,21:30.
 */
class ConeLoginScreen : LtScreen("登录"),S2CResponsibleScreen<LoginS2CPacket> {
    private lateinit var okBtn : Button
    private lateinit var pwdBox : EditBox
    override fun shouldCloseOnEsc(): Boolean {
        return true
    }

    override fun init() {
        okBtn = Button(150,150,100,20, Component.literal("确认"),::onSubmit)
        pwdBox  = EditBox(font,width/2,50,100,20, Component.literal("密码"))
        addRenderableWidget(okBtn)
        addRenderableWidget(pwdBox)
    }

    private fun onSubmit(button: Button?) {
        ConeNetManager.sendPacket(LoginC2SPacket(MC.user.profileId!!, pwdBox.value))
    }

    override fun tick() {
        okBtn.visible = pwdBox.value.isNotEmpty() &&  pwdBox.value.length<=16 && pwdBox.value.length>=6
        val handle = Minecraft.getInstance().window.window
        when {
            InputConstants.isKeyDown(handle, InputConstants.KEY_RETURN) || InputConstants.isKeyDown(handle, InputConstants.KEY_NUMPADENTER) -> {
                onSubmit(null)
            }
        }
        super.tick()
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBg()
        drawCenteredString(
            poseStack,
            font, "登录", width / 2, 17, fontColor
        )
        drawCenteredString(
            poseStack,
            font,"昵称：${MC.user.name}",width/2,height-32,fontColor
        )
        drawCenteredString(
            poseStack,
            font,"ID：${MC.user.profileId}",width/2,height-18,fontColor
        )
        drawString(poseStack,font,"密码",width/3,50,fontColor)
        super.render(poseStack, mouseX, mouseY, partialTick)
    }

    override fun onResponse(packet: LoginS2CPacket) {
        if(packet.isSuccess){
            MC.setScreen(ConeRoomJoinScreen())
        }else{
            ConeDialog.show(ConeDialogType.ERR,packet.msg)
        }

    }
}