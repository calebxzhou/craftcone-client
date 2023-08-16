package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.InRoomProcessable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.utils.LevelUt.setBlockDefault
import net.minecraft.client.server.IntegratedServer
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks

/**
 * Created  on 2023-06-29,20:46.
 */
//c2c 设置单个方块的包（玩家破坏+放置）
data class SetBlockPacket(
    //维度ID
    val levelId: Int,
    //方块位置
    val bpos: Long,
    //状态
    val stateId: Int,
) : Packet, InRoomProcessable,BufferWritable {


    companion object :BufferReadable<SetBlockPacket> {

        //从buf读
        override fun read(buf: FriendlyByteBuf): SetBlockPacket {
            return SetBlockPacket(
                buf.readByte().toInt(),
                buf.readLong(),
                buf.readVarInt(),
            )
        }
    }

    override fun process(server: IntegratedServer, room: ConeRoom) {
        val level = room.getLevelByDimId(this.levelId)
        val bpos = BlockPos.of(this.bpos)
        val state = Block.BLOCK_STATE_REGISTRY.byId(this.stateId)
        server.getLevel(level.dimension())?.setBlockDefault(
            bpos, state ?: run {
                logger.warn("无效的方块状态，将使用空气代替")
                Blocks.AIR.defaultBlockState()
            }
        )?:run {
            logger.warn("不存在维度${level.dimension()}")
            return
        }
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeByte(levelId)
        buf.writeLong(bpos)
        buf.writeVarInt(stateId)
    }

}