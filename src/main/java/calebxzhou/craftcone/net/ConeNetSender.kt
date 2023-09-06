package calebxzhou.craftcone.net

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.MsgLevel
import calebxzhou.craftcone.net.protocol.MsgType
import calebxzhou.craftcone.ui.coneMsg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Created  on 2023-06-22,22:37.
 */
//网络管理器
object ConeNetSender {

    //TODO 做容器同步


    //发送线程
    private val senderScope = CoroutineScope(Dispatchers.IO)

    @JvmStatic
    fun sendPacket(packet: BufferWritable) {
        senderScope.launch {
            ConeConnection.now?.channelFuture?.channel()?.writeAndFlush(packet) ?: let {
                coneMsg(MsgType.Dialog, MsgLevel.Err, "连接服务器失败")
                ConeConnection.disconnect()
            }
        }
        //发走
    }

}

