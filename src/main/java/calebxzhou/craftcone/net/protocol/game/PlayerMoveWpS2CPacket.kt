package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.toMcPlayer
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.InRoomProcessable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.utils.ByteBufUt.readObjectId
import io.netty.buffer.ByteBuf
import net.minecraft.client.server.IntegratedServer
import org.bson.types.ObjectId

/**
 * Created  on 2023-07-13,10:21.
 */
data class PlayerMoveWpS2CPacket(
    val uid: ObjectId,
    val w: Float,
    val p: Float,
) : Packet, InRoomProcessable {
    companion object : BufferReadable<PlayerMoveWpS2CPacket> {
        override fun read(buf: ByteBuf): PlayerMoveWpS2CPacket {
            return PlayerMoveWpS2CPacket(buf.readObjectId(), buf.readFloat(), buf.readFloat())
        }
    }

    override fun process(server: IntegratedServer, room: ConeRoom) {
        room.getPlayer(uid)?.toMcPlayer(server)?.let {
            it.xRot = w
            it.yRot = p
        } ?: let {
            logger.warn("找不到玩家$uid")
            return
        }
    }


}
