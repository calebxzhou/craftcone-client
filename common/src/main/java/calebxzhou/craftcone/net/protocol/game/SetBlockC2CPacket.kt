package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.C2CPacket
import calebxzhou.craftcone.utils.LevelUt
import calebxzhou.craftcone.utils.LevelUt.setBlockDefault
import calebxzhou.libertorch.MCS
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks

/**
 * Created  on 2023-06-29,20:46.
 */
//c2c 设置单个方块的包（玩家破坏+放置）
data class SetBlockC2CPacket(
    //维度ID
    val levelId: Int,
    //方块位置
    val bpos: Long,
    //状态
    val state: Int,
) : C2CPacket {


    companion object {

        //从buf读
        fun read(buf: FriendlyByteBuf): SetBlockC2CPacket {
            return SetBlockC2CPacket(
                buf.readByte().toInt(),
                buf.readLong(),
                buf.readVarInt(),
            )
        }
    }

    override fun process() {
        val level = LevelUt.getLevelByDimId(this.levelId)
        val bpos = BlockPos.of(this.bpos)
        val state = Block.BLOCK_STATE_REGISTRY.byId(this.state)
        MCS.getLevel(level.dimension())?.setBlockDefault(
            bpos, state ?: run {
                LOG.warn("无效的方块状态，将使用空气代替")
                Blocks.AIR.defaultBlockState()
            }
        )?:run {
            LOG.warn("不存在维度${level.dimension()}")
            return
        }
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeByte(levelId)
        buf.writeLong(bpos)
        buf.writeVarInt(state)
    }

}
