package calebxzhou.craftcone.net

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.account.LoginByNameC2SPacket
import calebxzhou.craftcone.net.protocol.account.LoginByUidC2SPacket
import calebxzhou.craftcone.net.protocol.account.RegisterC2SPacket
import calebxzhou.craftcone.net.protocol.game.*
import calebxzhou.craftcone.net.protocol.general.*
import calebxzhou.craftcone.net.protocol.room.*
import io.netty.buffer.ByteBuf

/**
 * Created  on 2023-07-14,8:55.
 */
object ConePacketSet {

    //包writer/reader种类 [id]
    private val packetTypes = arrayListOf<PacketType>()

    //s2c读
    private val packetIdReaders = linkedMapOf<Int, (ByteBuf) -> Packet>()

    //c2s写
    private val packetWriterClassIds = linkedMapOf<Class<out BufferWritable>, Int>()

    init {
        registerPacket(ConeRoom::read)

        registerPacket(CloseScreenS2CPacket::read)
        registerPacket(CopyToClipboardS2CPacket::read)
        registerPacket(DisconnectS2CPacket::read)
        registerPacket(GetServerInfoC2SPacket::class.java)
        registerPacket(OkDataS2CPacket::read)
        registerPacket(ServerInfoS2CPacket::read)
        registerPacket(SysMsgS2CPacket::read)

        registerPacket(LoginByUidC2SPacket::class.java)
        registerPacket(LoginByNameC2SPacket::class.java)
        registerPacket(RegisterC2SPacket::class.java)

        registerPacket(BlockDataS2CPacket::read)
        registerPacket(GetChunkC2SPacket::class.java)
        registerPacket(MovePlayerWpC2SPacket::class.java)
        registerPacket(MovePlayerXyzC2SPacket::class.java)
        registerPacket(PlayerJoinedRoomS2CPacket::read)
        registerPacket(PlayerLeftRoomS2CPacket::read)
        registerPacket(PlayerMoveWpS2CPacket::read)
        registerPacket(PlayerMoveXyzS2CPacket::read)
        registerPacket(SendChatMsgC2SPacket::class.java)
        registerPacket(SetBlockC2SPacket::class.java)

        registerPacket(CreateRoomC2SPacket::class.java)
        registerPacket(DelRoomC2SPacket::class.java)
        registerPacket(GetMyRoomC2SPacket::class.java)
        registerPacket(GetRoomC2SPacket::class.java)
        registerPacket(JoinRoomC2SPacket::class.java)
        registerPacket(LeaveRoomC2SPacket::class.java)


    }

    private fun registerPacket(reader: (ByteBuf) -> Packet) {
        packetIdReaders += packetTypes.size to reader
        packetTypes += PacketType.READ
    }

    private fun registerPacket(writerClass: Class<out BufferWritable>) {
        packetWriterClassIds += writerClass to packetTypes.size
        packetTypes += PacketType.WRITE
    }


    fun createPacket(packetId: Int, data: ByteBuf): Packet? = packetIdReaders[packetId]?.invoke(data) ?: let {
        logger.error("找不到ID$packetId 的包")
        null
    }


    fun getPacketId(packetClass: Class<out Packet>): Int? = packetWriterClassIds[packetClass]
    enum class PacketType {
        READ, WRITE
    }

}
