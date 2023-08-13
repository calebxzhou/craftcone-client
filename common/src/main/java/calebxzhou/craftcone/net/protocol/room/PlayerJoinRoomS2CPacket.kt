package calebxzhou.craftcone.net.protocol.room

import calebxzhou.craftcone.entity.ConePlayer
import calebxzhou.craftcone.entity.Room
import calebxzhou.craftcone.mc.Chat
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.ServerThreadProcessable
import net.minecraft.ChatFormatting
import net.minecraft.client.server.IntegratedServer
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-07-06,8:48.
 */
data class PlayerJoinRoomS2CPacket(
    val pid: Int,
    val pName: String
) : Packet, ServerThreadProcessable{
    companion object : BufferReadable<PlayerJoinRoomS2CPacket>{
        override fun read(buf: FriendlyByteBuf): PlayerJoinRoomS2CPacket {
            return PlayerJoinRoomS2CPacket(buf.readVarInt(),buf.readUtf())
        }

    }

    override fun process(server: IntegratedServer) {
        Chat.addMsg(Component.literal("$pName 加入了房间").withStyle(ChatFormatting.YELLOW))
        Room.now?.addPlayer(ConePlayer(pid,pName))
    }
}