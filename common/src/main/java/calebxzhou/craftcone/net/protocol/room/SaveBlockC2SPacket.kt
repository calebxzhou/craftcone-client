package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.net.protocol.C2SPacket
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-17,17:16.
 */
//保存单个方块的包（setBlock）
data class SaveBlockC2SPacket(
    //维度ID
    val dimId: Int,
    //方块位置
    val bpos: Long,
    //状态ID
    val state: Int,
) : C2SPacket{
    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(dimId)
        buf.writeLong(bpos)
        buf.writeVarInt(state)
        /*
        buf.writeUtf(level.dimensionName())
        buf.writeLong(bpos.asLong())
        val bState = BlockState.CODEC.encodeStart(NbtOps.INSTANCE, state)
        val stateTag = bState.get().left().get()
        LOG.info(stateTag.asString)
         */
    }


}
