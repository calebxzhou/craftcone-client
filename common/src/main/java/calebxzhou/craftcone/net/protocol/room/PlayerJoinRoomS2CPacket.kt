package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.entity.ConePlayer
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
 * Created  on 2023-07-06,8:48.
 */
data class PlayerJoinRoomS2CPacket(
    val pid: UUID,
    val pName: String
) : Packet, ServerThreadProcessable{
    companion object : BufferReadable<PlayerJoinRoomS2CPacket>{
        override fun read(buf: FriendlyByteBuf): PlayerJoinRoomS2CPacket {
            return PlayerJoinRoomS2CPacket(buf.readUUID(),buf.readUtf())
        }

    }

    override fun process(server: IntegratedServer) {
        MC.gui.chat.addMessage(Component.literal("$pName 加入了房间").withStyle(ChatFormatting.YELLOW))
        Room.players += Pair(pid,ConePlayer(pid,pName).getServerPlayer(server))
    }
}