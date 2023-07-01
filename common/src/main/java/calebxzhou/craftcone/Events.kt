package calebxzhou.craftcone

import calebxzhou.craftcone.net.ConePacketListener
import dev.architectury.event.EventResult
import dev.architectury.event.events.common.BlockEvent
import dev.architectury.event.events.common.LifecycleEvent
import dev.architectury.utils.value.IntValue
import net.minecraft.core.BlockPos
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

/**
 * Created  on 2023-06-29,18:12.
 */
object Events{
    fun register(){
        BlockEvent.BREAK.register(::onBlockBreak)
        LifecycleEvent.SERVER_STARTED.register(::onLocalServerStarted)
        LifecycleEvent.SERVER_STOPPING.register(::onLocalServerStopping)
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
        ConePacketListener.start()
    }

    //本地服务器关闭时
    private fun onLocalServerStopping(server: MinecraftServer?) {
        //关闭listener
        ConePacketListener.interrupt()
    }


    private fun onBlockBreak(
        level: Level?,
        blockPos: BlockPos?,
        blockState: BlockState?,
        serverPlayer: ServerPlayer?,
        intValue: IntValue?
    ): EventResult? {
        return EventResult.pass()
    }
}