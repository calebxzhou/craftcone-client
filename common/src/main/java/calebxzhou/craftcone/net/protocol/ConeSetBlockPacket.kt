package calebxzhou.craftcone.net.protocol

import calebxzhou.craftcone.Cone
import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.MC
import calebxzhou.craftcone.net.ConePacketSender
import calebxzhou.craftcone.utils.LevelUtils.setBlockDefault
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.FluidState

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
) : ConePacket {


    companion object {
        //上次设定的方块位置与状态
        var prevSetBlockPos: BlockPos? = null
        var prevSetBlockState: BlockState? = null

        //从buf读
        fun read(buf: FriendlyByteBuf): ConeSetBlockPacket {
            return ConeSetBlockPacket(
                ConePacket.getLevelByDimId(buf.readVarInt()),
                buf.readBlockPos(),
                buf.readById(Block.BLOCK_STATE_REGISTRY),
            )
        }
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(ConePacket.getDimIdByLevel(level))
        buf.writeBlockPos(bpos)
        buf.writeId(Block.BLOCK_STATE_REGISTRY, state ?: run {
            LOG.warn("无效的方块状态，将使用空气代替")
            Blocks.AIR.defaultBlockState()
        })
    }


    override fun process() {
        level.setBlockDefault(
            bpos, state ?: run {
                LOG.warn("无效的方块状态，将使用空气代替")
                Blocks.AIR.defaultBlockState()
            }
        )
        prevSetBlockPos = bpos
        prevSetBlockState = state

    }

    override fun checkSendCondition(): Boolean {
        var send = true

        //不发送非满格液体
        val block = state?.block
        if(block is LiquidBlock){
            if (state?.fluidState?.amount != FluidState.AMOUNT_FULL) {

                send = false
            }
        }
        //检查一下别跟上回发送的一样，否则死循环了
        if (prevSetBlockPos == bpos && prevSetBlockState == state) {
            send = false
        }

        return send
    }

}
