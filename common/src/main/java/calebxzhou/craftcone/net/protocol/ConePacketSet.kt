package calebxzhou.craftcone.net.protocol

import calebxzhou.craftcone.LOG
import calebxzhou.craftcone.net.protocol.account.*
import net.minecraft.network.FriendlyByteBuf

/**
 * Created  on 2023-07-14,8:55.
 */
object ConePacketSet {
    //包id和包类型对应map 全局通用
    private val idToPacketType = arrayListOf<PacketType>()

    //包class和包id对应map 全局通用
    private val classToPacketId = linkedMapOf<Class<out Packet>, Int>()

    /**S2C**/
    //包id和read方法对应map s2c读包用
    private val s2c_idToPacketReader = linkedMapOf<Int,(FriendlyByteBuf) -> S2CPacket>()
    private fun registerS2CPacket(reader: (FriendlyByteBuf) -> S2CPacket) {
        s2c_idToPacketReader += Pair(idToPacketType.size,reader)
        idToPacketType += PacketType.S2C
    }

    /**C2S**/
    private fun registerC2SPacket(packetClass: Class<out C2SPacket>) {
        classToPacketId += Pair(packetClass, idToPacketType.size)
        idToPacketType += PacketType.C2S
    }

    /**C2C**/
    private val c2c_idToPacketReader = linkedMapOf<Int,(FriendlyByteBuf) -> C2CPacket>()

    //idToPacketClassReader
    private fun registerC2CPacket(packetClass: Class<out C2CPacket>, reader: (FriendlyByteBuf) -> C2CPacket) {
        classToPacketId += Pair(packetClass, idToPacketType.size)
        c2c_idToPacketReader += Pair(idToPacketType.size,reader)
        idToPacketType += PacketType.C2C
    }
    fun createPacketAndProcess(packetId: Int, data: FriendlyByteBuf) {
        val packet = try {
            when (idToPacketType[packetId]) {
                PacketType.S2C -> {
                    s2c_idToPacketReader[packetId]?.invoke(data)?:let{
                        LOG.error("找不到C2S包ID$packetId ")
                        return
                    }
                }

                PacketType.C2C -> {
                    c2c_idToPacketReader[packetId]?.invoke(data)?:let {
                        LOG.error("找不到C2C包ID$packetId ")
                        return
                    }
                }

                else -> {
                    LOG.error("客户端不应该收到C2S数据包（ID=$packetId）")
                    return
                }
            }
        } catch (e: IndexOutOfBoundsException) {
            LOG.error("读包ID$packetId 错误 ${e.localizedMessage}")
            return
        }

        try {
            packet.process()
        } catch (e: Exception) {
            LOG.error("处理包ID$packetId 错误",e)
        }
    }

    fun getPacketId(packetClass: Class<out Packet>): Int? {
        return classToPacketId[packetClass]
    }


    init {
        registerC2SPacket(CheckPlayerExistC2SPacket::class.java)
        registerS2CPacket(CheckPlayerExistS2CPacket::read)

        registerC2SPacket(LoginC2SPacket::class.java)
        registerS2CPacket(LoginS2CPacket::read)

        registerC2SPacket(RegisterC2SPacket::class.java)
        registerS2CPacket(RegisterS2CPacket::read)


        /*registerS2CPacket(PlayerJoinRoomC2SPacket::read)

        registerS2CPacket(PlayerLeaveRoomC2SPacket::read)

        registerC2CPacket(ChatC2CPacket::class.java, ChatC2CPacket::read)
        registerC2CPacket(PlayerMoveC2CPacket::class.java, PlayerMoveC2CPacket::read)
        registerC2CPacket(SetBlockC2CPacket::class.java, SetBlockC2CPacket::read)*/

    }
    

}