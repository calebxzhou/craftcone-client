package calebxzhou.craftcone.net.protocol.account

import net.minecraft.network.FriendlyByteBuf
import calebxzhou.craftcone.net.protocol.*
import java.util.*
import calebxzhou.craftcone.net.protocol.*
/**
 * Created  on 2023-07-21,10:37.
 */
data class RegisterC2SPacket(
    val pid: UUID,
    val pName:String,
    val pwd : String,
): Packet, BufferWritable {

    override fun write(buf: FriendlyByteBuf) {
        buf.writeUUID(pid)
        buf.writeUtf(pName)
        buf.writeUtf(pwd)
    }

}
