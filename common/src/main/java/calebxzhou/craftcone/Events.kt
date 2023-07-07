package calebxzhou.craftcone

import calebxzhou.craftcone.net.ConeNetManager
import calebxzhou.craftcone.net.ConeNetManager.checkAndSendPacket
import calebxzhou.craftcone.net.protocol.ConeChatPacket
import calebxzhou.craftcone.net.protocol.ConeSetBlockPacket
import dev.architectury.event.EventResult
import dev.architectury.event.events.common.BlockEvent
import dev.architectury.event.events.common.ChatEvent
import dev.architectury.event.events.common.LifecycleEvent
import dev.architectury.utils.value.IntValue
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-06-29,18:12.
 */
object Events{
    fun register(){
        BlockEvent.PLACE.register(::onPlaceBlock)
        BlockEvent.BREAK.register(::onBreakBlock)
        ChatEvent.RECEIVED.register(::onChat )
        LifecycleEvent.SERVER_STARTED.register(::onLocalServerStarted)
        LifecycleEvent.SERVER_STOPPING.register(::onLocalServerStopping)
    }

    //放置方块
    private fun onPlaceBlock(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        entity: Entity?
    ): EventResult {
        var realState: BlockState = blockState
        //实体放置方块
        if(entity is Entity){
            val handItem = entity.handSlots.first()?.item
            if(handItem is BlockItem){
                val handBlock = handItem.block
                if(handBlock is DirectionalBlock){
                    //TODO 解决方向同步问题
                }
                realState = handBlock.defaultBlockState()
            }
        }else{
            //发射器放置方块
            realState = blockState
        }
        checkAndSendPacket(
            ConeSetBlockPacket(
                level,
                blockPos,
                realState
            )
        )
        return EventResult.pass()
    }


    private fun onBreakBlock(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        serverPlayer: ServerPlayer?,
        intValue: IntValue?
    ): EventResult? {
        checkAndSendPacket(
            ConeSetBlockPacket(
                level,
                blockPos,
                Blocks.AIR.defaultBlockState()
            )
        )
        return EventResult.pass()
    }


    //广播包：本地服务器接收到聊天信息时
    private fun onChat(player: ServerPlayer?, component: Component?): EventResult? {
        if(player==null || component==null)
            return EventResult.pass()
        ConeNetManager.checkAndSendPacket(ConeChatPacket(player.uuid,component.string))
        return EventResult.pass()
    }

    //本地服务器启动时
    private fun onLocalServerStarted(server: MinecraftServer) {
        //打开地图时，每个维度编成数字
        server.levelKeys().forEach {
            val number = Cone.numDimKeyMap.size
            LOG.info("Dimension Number: $number $it")
            Cone.numDimKeyMap += Pair(number,it)
        }
        //启动Listener
        //ConePacketListener.start()
    }


    //本地服务器关闭时
    private fun onLocalServerStopping(server: MinecraftServer?) {
        //关闭listener
        //ConePacketListener.interrupt()
    }
}
