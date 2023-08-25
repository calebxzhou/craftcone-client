package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.mc.Mcl
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.InRoomProcessable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.utils.LevelUt.setBlockDefault
import net.minecraft.client.server.IntegratedServer
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.TagParser
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Level

/**
 * Created  on 2023-07-17,17:16.
 */
data class BlockDataC2CPacket(
    //维度ID
    val dimId: Int,
    //方块位置
    val bpos: Long,
    //状态ID
    val stateId: Int,
    //NBT额外数据(没有就null)
    val tag: CompoundTag? = null
) : Packet, InRoomProcessable, BufferWritable {
    companion object : BufferReadable<BlockDataC2CPacket> {
        override fun read(buf: FriendlyByteBuf) = BlockDataC2CPacket(
            buf.readVarInt(),
            buf.readLong(),
            buf.readVarInt(),
            buf.readUtf().let {
                if (it.isNotEmpty())
                    TagParser.parseTag(it)
                else
                    null
            }
        )

    }


    override fun process(server: IntegratedServer, room: ConeRoom) {
        val state = Mc.getBlockStateById(stateId) ?: let {
            logger.warn("找不到状态ID$stateId 对应的状态")
            return
        }
        val lvl = room.getLevelByDimId(dimId) ?: let {
            logger.warn("找不到编号为${dimId}的维度。默认为主世界！")
            Mcl.getOverworld(server)
        }
        val bpos = BlockPos.of(this.bpos)
        lvl.setBlockDefault(bpos, state)
        tag?.let {
            logger.info("载入NBT $it")
            lvl.getBlockEntity(bpos)?.load(it)
        }
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(dimId)
        buf.writeLong(bpos)
        buf.writeVarInt(stateId)
        buf.writeUtf(tag?.asString ?: "")
    }


}
