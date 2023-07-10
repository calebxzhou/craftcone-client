package calebxzhou.craftcone.net.protocol

import calebxzhou.craftcone.LOG
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Block
import java.util.UUID

/**
 * Created  on 2023-07-06,8:48.
 */
data class ConeLoginPacket (
    val pid: UUID,
    val pwd: String,
): ConePacket {

    companion object{
        fun read(buf: FriendlyByteBuf): ConeLoginPacket {
            return ConeLoginPacket(buf.readUUID(),buf.readUtf())
        }
    }
    override fun write(buf: FriendlyByteBuf) {
        buf.writeUUID(pid)
        buf.writeUtf(pwd)
    }

    override fun process() {

    }

    override fun checkSendCondition(): Boolean {
        return true
    }
}