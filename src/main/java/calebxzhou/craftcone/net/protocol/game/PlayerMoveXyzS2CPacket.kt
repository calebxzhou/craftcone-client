package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.toMcPlayer
import calebxzhou.craftcone.net.ConeByteBuf.Companion.readObjectId
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.InRoomProcessable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.client.server.IntegratedServer
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.phys.Vec3
import org.bson.types.ObjectId

/**
 * Created  on 2023-07-13,10:21.
 */
data class PlayerMoveXyzS2CPacket(
    val uid: ObjectId,
    val x: Float,
    val y: Float,
    val z: Float,
) : Packet, InRoomProcessable {
    companion object : BufferReadable<PlayerMoveXyzS2CPacket> {
        override fun read(buf: FriendlyByteBuf): PlayerMoveXyzS2CPacket {
            return PlayerMoveXyzS2CPacket(buf.readObjectId(), buf.readFloat(), buf.readFloat(), buf.readFloat())
        }
    }

    override fun process(server: IntegratedServer, room: ConeRoom) = room.getPlayer(uid)
        ?.toMcPlayer(server)
        ?.setPos(Vec3(x.toDouble(), y.toDouble(), z.toDouble()))
        ?: let {
        logger.warn("找不到玩家$uid")
    }


}
