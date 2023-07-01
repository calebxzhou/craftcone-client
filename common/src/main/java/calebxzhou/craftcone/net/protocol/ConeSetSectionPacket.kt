package calebxzhou.craftcone.net.protocol

import calebxzhou.craftcone.utils.LevelUtils.setBlockDefault
import it.unimi.dsi.fastutil.shorts.ShortSet
import net.minecraft.core.BlockPos
import net.minecraft.core.SectionPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.chunk.LevelChunk
import net.minecraft.world.level.chunk.LevelChunkSection
import java.util.Arrays

/**
 * Created  on 2023-07-01,17:05.
 */
//批量方块更新
data class ConeSetSectionPacket(
    val level:Level,
    val chunkPos:ChunkPos,
    val sectionPos: SectionPos,
    val positions: ShortArray,
    val states: Array<BlockState?>,

) : ConePacket{

    companion object{
        @JvmStatic
        fun create(
            level: Level,
            chunkPos: ChunkPos,
            sectionPos: SectionPos,
                   changedBlocks: ShortSet,
                   levelChunkSection: LevelChunkSection) : ConeSetSectionPacket{
            val posLen = changedBlocks.size
            val positions = ShortArray(posLen)
            val states = arrayOfNulls<BlockState>(posLen)
            changedBlocks.forEachIndexed{ i, bpos ->
                positions[i] = bpos
                states[i] = levelChunkSection.getBlockState(
                    SectionPos.sectionRelativeX(bpos),
                    SectionPos.sectionRelativeY(bpos),
                    SectionPos.sectionRelativeZ(bpos)
                )
            }
            return ConeSetSectionPacket(level,chunkPos,sectionPos, positions, states)
        }

        fun read(buf: FriendlyByteBuf) : ConeSetSectionPacket{
            val level = ConePacket.getLevelByDimId(buf.readVarInt())
            val chunkPos = ChunkPos(buf.readLong())
            val sectionPos = SectionPos.of(buf.readLong())
            val posLen = buf.readVarInt()
            val positions = ShortArray(posLen)
            val states = arrayOfNulls<BlockState>(posLen)
            for(i in 0 until posLen){
                val l = buf.readLong()
                positions[i] = (l and 4095L).toShort()
                states[i] = Block.BLOCK_STATE_REGISTRY.byId((l ushr 12).toInt())
            }
            return ConeSetSectionPacket(level,chunkPos,sectionPos,positions,states)
        }
    }
    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(ConePacket.getDimIdByLevel(level))
        buf.writeLong(chunkPos.toLong())
        buf.writeLong(sectionPos.asLong())
        buf.writeVarInt(positions.size)
        positions.forEachIndexed { i, position ->
            buf.writeVarLong(((Block.getId(states[i]) shl 12) or position.toInt()).toLong())
        }
    }

    override fun process() {
        val mbpos = BlockPos.MutableBlockPos()
        positions.forEachIndexed{ i, position ->
            mbpos.set(
                sectionPos.relativeToBlockX(position),
                sectionPos.relativeToBlockY(position),
                sectionPos.relativeToBlockZ(position)
            )
            states[i]?.let { level.setBlockDefault(mbpos, it) }
        }
    }

    override fun checkSendCondition(): Boolean {
        return true
    }
}
