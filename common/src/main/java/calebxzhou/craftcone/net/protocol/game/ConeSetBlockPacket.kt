package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.ConeInGamePacket
import calebxzhou.craftcone.utils.LevelUtils.setBlockDefault
import calebxzhou.libertorch.MCS
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.piston.PistonHeadBlock
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-06-29,20:46.
 */
//设置单个方块的包
data class ConeSetBlockPacket(
    //维度
    val level: Level,
    //方块位置
    val bpos: BlockPos,
    //状态
    val state: BlockState?,
) : ConeInGamePacket {


    companion object {

        //从buf读
        fun read(buf: FriendlyByteBuf): ConeSetBlockPacket {
            buf.retain()
            return ConeSetBlockPacket(
                ConeInGamePacket.getLevelByDimId(buf.readByte().toInt()),
                buf.readBlockPos(),
                buf.readById(Block.BLOCK_STATE_REGISTRY),
            )
        }
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeByte(ConeInGamePacket.getDimIdByLevel(level))
        buf.writeBlockPos(bpos)
        buf.writeId(Block.BLOCK_STATE_REGISTRY, state ?: run {
            LOG.warn("无效的方块状态，将使用空气代替")
            Blocks.AIR.defaultBlockState()
        })
    }


    override fun process() {
        MCS.execute{
            MCS.getLevel(level.dimension())?.setBlockDefault(
                bpos, state ?: run {
                    LOG.warn("无效的方块状态，将使用空气代替")
                    Blocks.AIR.defaultBlockState()
                }
            )?:run {
                LOG.warn("不存在维度${level.dimension()}")
                return@execute
            }
        }

    }


}
