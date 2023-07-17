package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.net.ConeNetManager
import calebxzhou.libertorch.MC
import calebxzhou.libertorch.ui.DefaultColors
import calebxzhou.rdi.RdiConsts
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen
import net.minecraft.network.chat.Component
import java.net.InetSocketAddress

/**
 * Created  on 2023-06-19,21:47.
 */
class ConeConnectScreen : JoinMultiplayerScreen(TitleScreen()) {
    private lateinit var addrEbox: EditBox
    private lateinit var pwdEbox: EditBox
    private lateinit var connectBtn : Button
    override fun init() {
        addrEbox = EditBox(MC.font,300,300,100,20, Component.literal("服务器ip"))
        pwdEbox = EditBox(MC.font,350,300,100,20, Component.literal("用户密码"))
        connectBtn = Button(400,400,100,20,Component.literal("连接"),::onConnect)
    }

    override fun render(poseStack: PoseStack, i: Int, j: Int, f: Float) {
        super.render(poseStack, i, j, f)
        font.draw(poseStack,"连接CraftCone服务器",50f,50f,DefaultColors.White.color.opaque)
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