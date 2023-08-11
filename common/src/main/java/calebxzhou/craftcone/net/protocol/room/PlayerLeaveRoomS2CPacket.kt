package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.misc.Room
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.ServerThreadProcessable
import calebxzhou.libertorch.MC
import net.minecraft.ChatFormatting
import net.minecraft.client.server.IntegratedServer
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import java.util.*

/**
 * Created  on 2023-08-11,11:50.
 */
data class PlayerLeaveRoomS2CPacket(
    val pid: UUID
): Packet,ServerThreadProcessable {
    companion object :BufferReadable<PlayerLeaveRoomS2CPacket>{
        override fun read(buf: FriendlyByteBuf): PlayerLeaveRoomS2CPacket {
            return PlayerLeaveRoomS2CPacket(buf.readUUID())
        }

    }
    override fun process(server: IntegratedServer) {
        val player = Room.players[pid]?:let {
            logger.error { "收到了玩家 $pid 的离开房间包 但是没找到此玩家" }
            return
        }
        MC.gui.chat.addMessage(Component.literal("${player.gameProfile.name} 离开了房间").withStyle(ChatFormatting.YELLOW))
        Room.players.remove(pid)
    }

}