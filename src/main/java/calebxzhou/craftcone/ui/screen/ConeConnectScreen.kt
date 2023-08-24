package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.Consts
import calebxzhou.craftcone.entity.ConePlayer
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.ConeConnection
import net.minecraft.client.gui.screens.Screen
import java.net.InetSocketAddress
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

/**
 * Created  on 2023-06-19,21:47.
 */
class ConeConnectScreen(val titleScreen: Screen) : ConeOkCancelInputScreen(titleScreen, "输入服务器IP地址") {
    init {
        if (ConePlayer.now != null && ConeConnection.now != null) {
            Mc.screen = ConeRoomSelectScreen(titleScreen)
        }
    }

    override fun init() {
        super.init()
        inputValue = Path("coneServerAddr.txt").let {
            if (it.exists()) {
                Files.readString(it)
            } else {
                ""
            }
        }
    }

    override fun onSubmit() {
        val ip = inputValue.replace("：", ":")
        //端口号
        var port = Consts.DefaultPort
        if (ip.contains(":")) {
            port = ip.split(":")[1].toInt()
        }
        val addr = InetSocketAddress(ip, port)
        screenTitle = "连接中 $addr"
        ConeConnection.connect(addr)
        Files.writeString(Path("coneServerAddr.txt"), inputValue)
        Mc.screen = ConeLoginScreen(titleScreen)

    }


}
