package calebxzhou.craftcone.command

import calebxzhou.craftcone.logger
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.game.ReadBlockC2SPacket
import calebxzhou.craftcone.utils.LevelUt
import calebxzhou.libertorch.MC
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.world.level.ChunkPos

/**
 * Created  on 2023-08-10,21:46.
 */
object ConeRefreshChunkCommand {
    fun register(dispatcher:CommandDispatcher<CommandSourceStack> ){
        dispatcher.register(Commands.literal("cone-refresh-chunk").executes {
            val chunkPosNow = MC.player?.chunkPosition()?:let {
                logger.error { "玩家为空时就请求载入区块了" }
                return@executes 1
            }
            for (x in chunkPosNow.x-16 .. chunkPosNow.x + 16){
               for(z in chunkPosNow.z - 16 .. chunkPosNow.z + 16){
                   val cpos = ChunkPos(x, z)
                   val info = "刷新区块中 $x,$z"
                   MC.gui.setOverlayMessage(Component.literal(info),false)
                   logger.info { info }
                   ConeNetSender.sendPacket(ReadBlockC2SPacket(LevelUt.getDimIdByLevel(MC.level?:let {
                       logger.error { "当前游玩的存档为空" }
                       return@executes 1
                   }), cpos.toLong()))
               }
            }
            MC.gui.setOverlayMessage(Component.literal("刷新区块完成"),false)
            return@executes 1
        })
    }
}