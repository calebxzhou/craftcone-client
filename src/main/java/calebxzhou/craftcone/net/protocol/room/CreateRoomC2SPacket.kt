package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.network.FriendlyByteBuf

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
    //mod加载器？Fabric：Forge
    val isFabric: Boolean,
    //方块状态数量
    val blockStateAmount: Int,
) : Packet, BufferWritable {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeUtf(rName)
        buf.writeUtf(mcVersion)
        buf.writeBoolean(isCreative)
        buf.writeBoolean(isFabric)
        buf.writeVarInt(blockStateAmount)
    }


}
