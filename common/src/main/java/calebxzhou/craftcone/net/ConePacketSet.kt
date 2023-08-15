package calebxzhou.craftcone.net

import calebxzhou.craftcone.entity.Room
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.mc.Mcl
import calebxzhou.craftcone.net.protocol.*
import calebxzhou.craftcone.net.protocol.account.LoginC2SPacket
import calebxzhou.craftcone.net.protocol.account.RegisterC2SPacket
import calebxzhou.craftcone.net.protocol.game.*
import calebxzhou.craftcone.net.protocol.general.SysMsgS2CPacket
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
        registerPacket(SysMsgS2CPacket::read)

        registerPacket(LoginC2SPacket::class.java)
        registerPacket(RegisterC2SPacket::class.java)
        registerPacket(PlayerChatC2SPacket::class.java)
        registerPacket(PlayerMoveC2CPacket::class.java)
        registerPacket(PlayerMoveC2CPacket::read)
        registerPacket(ReadBlockC2SPacket::class.java)
        registerPacket(ReadBlockS2CPacket::read)
        registerPacket(WriteBlockC2SPacket::class.java)
        registerPacket(SetBlockC2CPacket::class.java)
        registerPacket(SetBlockC2CPacket::read)
        // registerPacket(SetBlockStateC2SPacket::class.java)

        registerPacket(RoomInfoC2SPacket::class.java)
        registerPacket(CreateRoomC2SPacket::class.java)
        registerPacket(DeleteRoomC2SPacket::class.java)
        registerPacket(JoinRoomC2SPacket::class.java)
        registerPacket(PlayerJoinedRoomS2CPacket::read)
        registerPacket(LeaveRoomC2SPacket::class.java)
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
                Mcl.logicThread {
                    packet.process(it)
                }
            }
            is InRoomProcessable ->{
                Mcl.logicThread {
                    packet.process(it,Room.now?:let {
                        logger.warn { "收到了房间内数据包，但是没有在房间内！" }
                        return@logicThread
                    })
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