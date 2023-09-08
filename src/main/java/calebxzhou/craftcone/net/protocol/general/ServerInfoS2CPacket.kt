package calebxzhou.craftcone.net.protocol.general

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.utils.ByteBufUt.readUtf
import calebxzhou.craftcone.utils.ByteBufUt.readVarInt
import io.netty.buffer.ByteBuf

/**
 * Created  on 2023-08-29,20:17.
 */
data class ServerInfoS2CPacket(
    val version: Int,
    val onlinePlayers: Int,
    val maxPlayers: Int,
    val name: String,
    val descr: String,
    val icon: String,
) : Packet, RenderThreadProcessable {
    companion object : BufferReadable<ServerInfoS2CPacket> {
        override fun read(buf: ByteBuf) = ServerInfoS2CPacket(
            buf.readVarInt(),
            buf.readVarInt(),
            buf.readVarInt(),
            buf.readUtf(),
            buf.readUtf(),
            buf.readUtf()
        )

    }

    override fun process() {
        logger.info(this)
    }
}
