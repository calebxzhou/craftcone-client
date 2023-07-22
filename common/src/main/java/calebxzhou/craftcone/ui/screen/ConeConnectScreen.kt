package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.net.ConeNetManager
import calebxzhou.libertorch.mc.gui.LtScreen
import calebxzhou.libertorch.mc.gui.LtTheme
import calebxzhou.libertorch.ui.DefaultColors
import calebxzhou.rdi.RdiConsts
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiComponent
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component
import java.net.InetSocketAddress

/**
 * Created  on 2023-06-19,21:47.
 */
class ConeConnectScreen : LtScreen("连接服务器") {
    private lateinit var addrEbox: EditBox
    //private lateinit var pwdEbox: EditBox
    private lateinit var connectBtn : Button
    override fun init() {
        super.init()
        addrEbox = EditBox(font,width/2,50,100,20, Component.literal("服务器ip"))
        //pwdEbox = EditBox(font,150,150,100,20, Component.literal("用户密码"))
        connectBtn = Button(150,150,100,20,Component.literal("连接"),::onConnect)
        addRenderableWidget(addrEbox)
        //addWidget(pwdEbox)
        addRenderableWidget(connectBtn)
    }

    override fun tick() {
        super.tick()
    }
    override fun render(poseStack: PoseStack, i: Int, j: Int, f: Float) {
        renderBg()
        addrEbox.x = width / 2
        addrEbox.y = 50
        drawString(poseStack,font,"IP地址",width/3,addrEbox.y,fontColor)
        drawCenteredString(
            poseStack,
            font, "连接CraftCone服务器", width / 2, 17, fontColor
        )
        addrEbox.render(poseStack, i, j, f)
        connectBtn.render(poseStack,i, j, f)
        super.render(poseStack, i, j, f)
    }
    private fun onConnect(button: Button) {
        var ip = addrEbox.value
        ip = ip.replace("：",":")

        //端口号
        var port = 19198
        if(ip.contains(":")){
            port = ip.split(":")[1].toInt()
        }
        if(ip == "rdi4")
            ip = RdiConsts.serverAddr

        val addr = InetSocketAddress(ip,port)
        ConeNetManager.connect(addr)
        //TODO 发登录用户名密码验证包
    }
}