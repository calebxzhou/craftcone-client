package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.utils.ByteBufUt.writeUtf
import calebxzhou.craftcone.utils.ByteBufUt.writeVarInt
import io.netty.buffer.ByteBuf

/**
 * Created  on 2023-07-06,8:48.
 */
//玩家请求创建房间
data class CreateRoomC2SPacket(
    //房间名称
    val rName: String,
    //mc版本
    val mcVersion: String,
    //是否创造模式
    val isCreative: Boolean,
    //方块状态数量
    val blockStateAmount: Int,
) : Packet, BufferWritable {
    override fun write(buf: ByteBuf) {
        buf.writeUtf(rName)
        buf.writeUtf(mcVersion)
        buf.writeBoolean(isCreative)
        buf.writeVarInt(blockStateAmount)
    }


}
