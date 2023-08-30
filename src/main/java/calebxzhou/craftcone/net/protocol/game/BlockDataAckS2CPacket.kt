package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.net.ConeByteBuf
import calebxzhou.craftcone.net.protocol.BufferReadable
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.InRoomProcessable
import calebxzhou.craftcone.net.protocol.Packet
import net.minecraft.client.server.IntegratedServer
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf

data class BlockDataAckS2CPacket(
    val dimId:Int,val blockPos: BlockPos
): Packet,InRoomProcessable{
    companion object : BufferReadable<BlockDataAckS2CPacket>{
        override fun read(buf: FriendlyByteBuf)= BlockDataAckS2CPacket(buf.readVarInt(),BlockPos.of(buf.readLong()))
    }

    override fun process(server: IntegratedServer, room: ConeRoom) {

    }

}
