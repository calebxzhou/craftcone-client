package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.toMcPlayer
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.InRoomProcessable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.client.server.IntegratedServer
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-13,10:21.
 */
data class PlayerMoveWpS2CPacket(
    val pid: Int,
    val w: Float,
    val p: Float,
) : Packet, InRoomProcessable {
    companion object : BufferReadable<PlayerMoveWpS2CPacket> {
        override fun read(buf: FriendlyByteBuf): PlayerMoveWpS2CPacket {
            return PlayerMoveWpS2CPacket(buf.readVarInt(), buf.readFloat(), buf.readFloat())
        }
    }

    override fun process(server: IntegratedServer, room: ConeRoom) {
        room.getPlayer(pid)?.toMcPlayer(server)?.let {
            it.xRot = w
            it.yRot = p
        } ?: let {
            logger.warn("找不到玩家$pid")
            return
        }
    }


}
