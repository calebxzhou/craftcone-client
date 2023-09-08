package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.InRoomProcessable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.utils.ByteBufUt.readUtf
import calebxzhou.craftcone.utils.ByteBufUt.readVarInt
import calebxzhou.craftcone.utils.ByteBufUt.writeUtf
import calebxzhou.craftcone.utils.ByteBufUt.writeVarInt
import calebxzhou.craftcone.utils.LevelUt.setBlockDefault
import io.netty.buffer.ByteBuf
import net.minecraft.client.server.IntegratedServer
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.TagParser

/**
 * Created  on 2023-07-17,17:16.
 */
data class SetBlockC2SPacket(
    //维度ID
    val dimId: Int,
    //方块位置
    val bpos: BlockPos,
    //状态ID
    val stateId: Int,
    //NBT额外数据(没有就null)
    val tag: String? = null
) : Packet,  BufferWritable {


    override fun write(buf: ByteBuf) {
        buf.writeVarInt(dimId)
        buf.writeLong(bpos.asLong())
        buf.writeVarInt(stateId)
        buf.writeUtf(tag ?: "")
    }


}
