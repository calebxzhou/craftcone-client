package calebxzhou.craftcone.net.protocol

import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.Level
import kotlin.math.roundToInt

/**
 * Created  on 2023-07-01,10:20.
 */
data class ConeExplodePacket(
    val level: Level,
    val x : Float,
    val y : Float,
    val z : Float,
    val power : Float,
    val toBlow : List<BlockPos>,
): ConePacket {


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
            return ConeExplodePacket(ConePacket.getLevelByDimId(dimId),x,y,z,power,toBlow )
        }
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(ConePacket.getDimIdByLevel(level))
        buf.writeFloat(x)
        buf.writeFloat(y)
        buf.writeFloat(z)
        buf.writeFloat(power)
        buf.writeCollection(toBlow){ buf1 , pos ->
            buf1.writeByte(pos.x - x.roundToInt())
            buf1.writeByte(pos.y - y.roundToInt())
            buf1.writeByte(pos.z - z.roundToInt())
        }
    }

    override fun process() {
        val explosion =
            Explosion(level, null, x.toDouble(), y.toDouble(), z.toDouble(), power, toBlow)
        explosion.finalizeExplosion(true)
    }

    override fun checkSendCondition(): Boolean {
        return true
    }
}
