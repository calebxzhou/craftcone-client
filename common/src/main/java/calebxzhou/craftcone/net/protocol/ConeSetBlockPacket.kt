package calebxzhou.craftcone.net.protocol

import calebxzhou.craftcone.Cone
import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.MC
import calebxzhou.craftcone.net.ConePacketSender
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-06-29,20:46.
 */
//设置单个方块的包
data class ConeSetBlockPacket(
    val dimId: Int, //varint
    val bpos:BlockPos,
    val state:BlockState?,
   // val flags:Int, 默认是3
   // val recursionLeft:Int,  默认是512

    ) : ConePacket{

    constructor(level: Level, pos: BlockPos, state: BlockState,
    ) : this(
        Cone.numDimKeyMap.filterValues { it == level.dimension() }.keys.first(),
        pos, state,
    )
    companion object{
        var prevSetBlockPos: BlockPos?=null
        var prevSetBlockState: BlockState?=null
        const val DEFAULT_FLAG = 3
        const val DEFAULT_RECURSION_LEFT = 512

        fun read(buf: FriendlyByteBuf) : ConeSetBlockPacket {
            return ConeSetBlockPacket(buf.readVarInt(),
            buf.readBlockPos(),
            buf.readById(Block.BLOCK_STATE_REGISTRY))
        }
    }
    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(dimId)
        buf.writeBlockPos(bpos)
        buf.writeId(Block.BLOCK_STATE_REGISTRY,state?:run{
            LOG.warn("无效的方块状态，将使用空气代替")
            Blocks.AIR.defaultBlockState()
        })
    }


    override fun process() {
        ConePacket.getLevelByDimId(dimId).setBlock(bpos,state?:run{
            LOG.warn("无效的方块状态，将使用空气代替")
            Blocks.AIR.defaultBlockState()
        },
            DEFAULT_FLAG,
            DEFAULT_RECURSION_LEFT)
        prevSetBlockPos = bpos
        prevSetBlockState = state

    }

    override fun checkSendCondition(): Boolean {
        var send = true

        //检查一下别跟上回发送的一样，否则死循环了
        if(prevSetBlockPos == bpos && prevSetBlockState == state){
            send = false
        }

        return send
    }

}
