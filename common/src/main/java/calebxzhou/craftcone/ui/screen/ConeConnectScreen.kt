package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.net.ConeConnection
import calebxzhou.craftcone.entity.ConeUser
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.coneNetThread
import calebxzhou.craftcone.net.protocol.account.CheckPlayerExistC2SPacket
import calebxzhou.craftcone.net.protocol.account.CheckPlayerExistS2CPacket
import calebxzhou.libertorch.MC
import calebxzhou.libertorch.mc.gui.LtScreen
import calebxzhou.rdi.RdiConsts
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component
import java.net.InetSocketAddress

/**
 * Created  on 2023-06-19,21:47.
 */
class ConeConnectScreen : LtScreen("输入服务器IP地址"), S2CResponsibleScreen<CheckPlayerExistS2CPacket> {
    private lateinit var addrEbox: EditBox
    override fun init() {
        addrEbox = EditBox(font, width / 2 - 50, 50, 100, 20, Component.literal("服务器ip"))
        addRenderableWidget(addrEbox)
        if(ConeUser.now != null && ConeConnection.now !=null){
            MC.setScreen(ConeRoomJoinScreen())
        }
        super.init()
    }

    override fun onPressEnterKey() {
        var ip = addrEbox.value.replace("：", ":")
        //端口号
        var port = 19198
        if (ip.contains(":")) {
            port = ip.split(":")[1].toInt()
        }
        if (ip == "rdi")
            ip = RdiConsts.serverAddr

        val addr = InetSocketAddress(ip, port)
        comment="连接中 $addr"
        coneNetThread {
            ConeConnection.connect(addr)
            ConeNetSender.sendPacket(CheckPlayerExistC2SPacket(MC.user.profileId!!))
        }
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBg()
        super.render(poseStack, mouseX, mouseY, partialTick)
    }

    override fun onResponse(packet: CheckPlayerExistS2CPacket) {
        if (packet.exists) {
            MC.setScreen(ConeLoginScreen())
        } else {
            MC.setScreen(ConeRegisterScreen())
        }
    }
}