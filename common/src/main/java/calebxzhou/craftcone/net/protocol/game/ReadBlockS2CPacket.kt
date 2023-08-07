package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.ServerThreadProcessable
import calebxzhou.craftcone.utils.LevelUt
import calebxzhou.craftcone.utils.LevelUt.setBlockDefault
import calebxzhou.craftcone.utils.getBlockStateById
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-17,17:16.
 */
data class ReadBlockS2CPacket(
    //维度ID
    val dimId: Int,
    //方块位置
    val bpos: Long,
    //状态ID
    val stateId: Int,
) : Packet, ServerThreadProcessable{
    companion object : BufferReadable<ReadBlockS2CPacket> {
        override fun read(buf: FriendlyByteBuf): ReadBlockS2CPacket {
            return ReadBlockS2CPacket(buf.readVarInt(),buf.readLong(),buf.readVarInt())
        }

    }



    override fun process() {
        val state = getBlockStateById(stateId)?:let{
            logger.warn("找不到状态ID$stateId 对应的状态")
            return
        }
        LevelUt.getLevelByDimId(dimId).setBlockDefault(BlockPos.of(bpos), state)
    }


}