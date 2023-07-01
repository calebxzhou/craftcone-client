package calebxzhou.craftcone.net.protocol

import calebxzhou.craftcone.Cone
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import kotlin.math.roundToInt

/**
 * Created  on 2023-07-01,10:20.
 */
data class ConeExplodePacket(
    val dimId: Int, //varint
    val x : Float,
    val y : Float,
    val z : Float,
    val power : Float,
    val toBlow : List<BlockPos>,
    val knockbackX : Float,
    val knockbackY : Float,
    val knockbackZ : Float,
): ConePacket {
    constructor(level: Level,   x : Float,
                 y : Float,
                 z : Float,
                 power : Float,
                 toBlow : List<BlockPos>,
                 knockbackX : Float,
                 knockbackY : Float,
                 knockbackZ : Float,
    ) : this(Cone.numDimKeyMap.filterValues { it == level.dimension() }.keys.first(), x, y, z, power, toBlow, knockbackX, knockbackY, knockbackZ)
    companion object{
        fun read(buf: FriendlyByteBuf): ConeExplodePacket {
            val dimId = buf.readVarInt()
            val x = buf.readFloat()
            val y = buf.readFloat()
            val z = buf.readFloat()
            val power = buf.readFloat()
            val toBlow = buf.readList {
                BlockPos(
                    it.readByte() + x.roundToInt(),
                    it.readByte() + y.roundToInt(),
                    it.readByte() + z.roundToInt(),
                ) }
            return ConeExplodePacket(dimId,x,y,z,power,toBlow,
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat(),
                )
        }
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(dimId)
        buf.writeFloat(x)
        buf.writeFloat(y)
        buf.writeFloat(z)
        buf.writeFloat(power)
        buf.writeCollection(toBlow){ buf1 , pos ->
            buf1.writeByte(pos.x - x.roundToInt())
            buf1.writeByte(pos.y - y.roundToInt())
            buf1.writeByte(pos.z - z.roundToInt())
        }
        buf.writeFloat(knockbackX)
        buf.writeFloat(knockbackY)
        buf.writeFloat(knockbackZ)
    }

    override fun process() {
        Explosion()

    }

    override fun checkSendCondition(): Boolean {
        return true
    }
}
