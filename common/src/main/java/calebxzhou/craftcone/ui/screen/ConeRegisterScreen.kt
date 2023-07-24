package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.net.ConeNetManager
import calebxzhou.craftcone.net.protocol.account.RegisterC2SPacket
import calebxzhou.craftcone.net.protocol.account.RegisterS2CPacket
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.libertorch.MC
import calebxzhou.libertorch.mc.gui.LtScreen
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-07-17,21:30.
 */
class ConeRegisterScreen : LtScreen("注册") {
    private lateinit var okBtn : Button
    private lateinit var pwdBox : EditBox
    override fun shouldCloseOnEsc(): Boolean {
        return true
    }
    override fun init() {
        okBtn = Button(150,150,100,20, Component.literal("提交"),::onSubmit)
        pwdBox  = EditBox(font,width/2,50,100,20, Component.literal("密码"))
        okBtn.visible =false
        addRenderableWidget(okBtn)
        addRenderableWidget(pwdBox)
    }

    private fun onSubmit(button: Button) {
        ConeNetManager.sendPacket(RegisterC2SPacket(MC.user.profileId!!,pwdBox.value))
    }
    fun onResponse(packet: RegisterS2CPacket){
        if(packet.isSuccess){
            MC.setScreen(ConeRoomJoinScreen())
        }else{
            ConeDialog.show(ConeDialogType.ERR,packet.msg)
        }
    }
    override fun tick() {
        okBtn.visible = pwdBox.value.isNotEmpty() &&  pwdBox.value.length<=16 && pwdBox.value.length>=6
        super.tick()
    }
    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBg()

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
}