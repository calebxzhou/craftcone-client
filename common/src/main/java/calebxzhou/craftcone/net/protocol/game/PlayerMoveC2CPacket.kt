package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.net.protocol.C2CPacket
import calebxzhou.craftcone.net.protocol.ReadablePacket
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-13,10:21.
 */
data class PlayerMoveC2CPacket(
    val tpid:Int,
    val x:Float,
    val y:Float,
    val z:Float,
    val w:Float,
    val p:Float,
) : C2CPacket {
    companion object : ReadablePacket<PlayerMoveC2CPacket>{
        override fun read(buf: FriendlyByteBuf): PlayerMoveC2CPacket {
            return PlayerMoveC2CPacket(buf.readVarInt(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat())
        }
    }

    override fun process() {
        TODO("Not yet implemented")
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(tpid)
        buf.writeFloat(x)
        buf.writeFloat(y)
        buf.writeFloat(z)
        buf.writeFloat(w)
        buf.writeFloat(p)
    }



}