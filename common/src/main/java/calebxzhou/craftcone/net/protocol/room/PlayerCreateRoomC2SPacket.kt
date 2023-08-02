package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.C2SPacket
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-06,8:48.
 */
//玩家请求创建房间
data class PlayerCreateRoomC2SPacket(
    //是否创造模式
    val isCreative: Boolean,
    //mod加载器？Fabric：Forge
    val isFabric: Boolean,
    //方块状态数量
    val blockStateAmount: Int,
): C2SPacket {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeBoolean(isCreative)
        buf.writeBoolean(isFabric)
        buf.writeInt(blockStateAmount)
    }


}