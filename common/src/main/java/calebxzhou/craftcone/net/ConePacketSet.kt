package calebxzhou.craftcone.net

import calebxzhou.craftcone.entity.Room
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.net.protocol.BufferWritable
import calebxzhou.craftcone.net.protocol.Packet
import calebxzhou.craftcone.net.protocol.RenderThreadProcessable
import calebxzhou.craftcone.net.protocol.ServerThreadProcessable
import calebxzhou.craftcone.net.protocol.account.*
import calebxzhou.craftcone.net.protocol.game.*
import calebxzhou.craftcone.net.protocol.room.*
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-14,8:55.
 */
object ConePacketSet {

    //包writer/reader种类 [id]
    private val packetTypes = arrayListOf<PacketType>()
    //s2c读
    private val packetIdReaders = linkedMapOf<Int,(FriendlyByteBuf) -> Packet>()
    //c2s写
    private val packetWriterClassIds = linkedMapOf<Class<out BufferWritable>,Int>()
    init {
        registerPacket(Room::read)

        registerPacket(CheckPlayerExistC2SPacket::class.java)
        registerPacket(CheckPlayerExistS2CPacket::read)
        registerPacket(LoginC2SPacket::class.java)
        registerPacket(LoginS2CPacket::read)
        registerPacket(RegisterC2SPacket::class.java)
        registerPacket(RegisterS2CPacket::read)
        registerPacket(ChatC2CPacket::class.java)
        registerPacket(ChatC2CPacket::read)
        registerPacket(PlayerMoveC2CPacket::class.java)
        registerPacket(PlayerMoveC2CPacket::read)
        registerPacket(ReadBlockC2SPacket::class.java)
        registerPacket(ReadBlockS2CPacket::read)
        registerPacket(WriteBlockC2SPacket::class.java)
        registerPacket(SetBlockC2CPacket::class.java)
        registerPacket(SetBlockC2CPacket::read)
        // registerPacket(SetBlockStateC2SPacket::class.java)
        registerPacket(SysChatMsgS2CPacket::read)

        registerPacket(GetRoomInfoC2SPacket::class.java)
        registerPacket(PlayerCreateRoomC2SPacket::class.java)
        registerPacket(PlayerCreateRoomS2CPacket::read)
        registerPacket(PlayerDeleteRoomC2SPacket::class.java)
        registerPacket(PlayerDeleteRoomS2CPacket::read)
        registerPacket(PlayerJoinRoomC2SPacket::class.java)
        registerPacket(PlayerJoinedRoomS2CPacket::read)
        registerPacket(PlayerLeaveRoomC2SPacket::class.java)
        registerPacket(PlayerLeftRoomS2CPacket::read)



    }

    private fun registerPacket(reader: (FriendlyByteBuf) -> Packet){
        packetIdReaders += Pair (packetTypes.size,reader)
        packetTypes += PacketType.READ
    }
    private fun registerPacket(writerClass: Class<out BufferWritable>) {
        packetWriterClassIds += Pair (writerClass, packetTypes.size)
        packetTypes += PacketType.WRITE
    }


    fun createPacket(packetId: Int,data: FriendlyByteBuf): Packet? {
        return packetIdReaders[packetId] ?.invoke(data)?:let{
            logger.error { "找不到ID$packetId 的包" }
            null
        }

    }
    fun processPacket(packet: Packet) {
        when(packet){
            is RenderThreadProcessable ->{
                Mc.renderThread {
                    packet.process()
                }
            }
            is ServerThreadProcessable ->{
                Mc.InGame.logicThread {
                    packet.process(it)
                }
            }
        }
    }


    fun getPacketId(packetClass: Class<out Packet>): Int? {
        return packetWriterClassIds[packetClass]
    }
    enum class PacketType {
        READ,WRITE
    }

}