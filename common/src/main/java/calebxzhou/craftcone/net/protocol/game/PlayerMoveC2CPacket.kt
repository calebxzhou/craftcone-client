package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.ServerThreadProcessable
import net.minecraft.client.server.IntegratedServer
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.phys.Vec3

/**
 * Created  on 2023-07-13,10:21.
 */
data class PlayerMoveC2CPacket(
    val pid:Int,
    val x:Float,
    val y:Float,
    val z:Float,
    val w:Float,
    val p:Float,
) : Packet,ServerThreadProcessable,BufferWritable {
    companion object : BufferReadable<PlayerMoveC2CPacket>{
        override fun read(buf: FriendlyByteBuf): PlayerMoveC2CPacket {
            return PlayerMoveC2CPacket(buf.readVarInt(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat(),buf.readFloat())
        }
    }

    override fun process(server: IntegratedServer) {
        val player = ConeRoom.now?.players?.get(pid)?.getServerPlayer(server) ?:let {
            logger.warn { "当前没有游玩房间 或者找不到玩家$pid 就收到了玩家移动包" }
            return
        }

        player.setPos(Vec3(x.toDouble(),y.toDouble(),z.toDouble()))
        player.xRot = w
        player.yRot = p
    }

    override fun write(buf: FriendlyByteBuf) {
        buf.writeVarInt(pid)
        buf.writeFloat(x)
        buf.writeFloat(y)
        buf.writeFloat(z)
        buf.writeFloat(w)
        buf.writeFloat(p)
    }



}