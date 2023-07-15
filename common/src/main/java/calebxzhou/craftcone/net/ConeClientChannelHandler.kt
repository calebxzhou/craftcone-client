package calebxzhou.craftcone.net

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.ConePacketSet
import calebxzhou.craftcone.net.protocol.ConeProcessablePacket
import io.netty.channel.*
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.socket.DatagramPacket
import net.minecraft.network.FriendlyByteBuf
import java.net.InetSocketAddress

/**
 * Created  on 2023-07-05,9:24.
 */
@Sharable
class ConeClientChannelHandler(val serverAddr: InetSocketAddress) : SimpleChannelInboundHandler<DatagramPacket>() {

    //接收包数
    var packetCountRx = 0
        private set
    //发包数
    var packetCountTx = 0

    override fun channelInactive(ctx: ChannelHandlerContext) {
        super.channelInactive(ctx)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        LOG.error("连接错误：",cause)
    }
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: DatagramPacket) {
        ++packetCountRx
        //第一个byte
        val byte1 = msg.content().readByte().toInt()
        //第1个bit（包类型，in game/out game）
        val bit1 = byte1 shr 7
        //第2~8个bit（包ID）
        val packetId = (byte1 shl 1).toByte().toInt()

        val data = FriendlyByteBuf(msg.content())
        val packet : ConeProcessablePacket = if(bit1 == 0){
            //0代表out game数据包
            ConePacketSet.OutGame.createPacket(packetId, data)

        }else{
            //1代表in game数据包
            ConePacketSet.InGame.createPacket(packetId,data)
        }
        //处理包
        packet.process()
    }
}