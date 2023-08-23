package calebxzhou.craftcone.command

import calebxzhou.craftcone.entity.ConeChunkPos
import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mcl
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.game.SetSavedChunkC2SPacket
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

/**
 * Created  on 2023-08-18,21:08.
 */
object ConeSetSavedChunkCommand {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(Commands.literal("cone-set-saved-chunk").executes {
            val chunkPosNow = Mcl.player?.chunkPosition() ?: let {
                logger.error("玩家为空时就请求载入区块了")
                return@executes 1
            }
            val cpos = ConeChunkPos(chunkPosNow.x, chunkPosNow.z)
            ConeRoom.now?.let {
                val packet = SetSavedChunkC2SPacket(it.getDimIdByLevel(Mcl.level ?: let {
                    logger.error("当前游玩的存档为空")
                    return@executes 1
                }), cpos)
                ConeNetSender.sendPacket(packet)
            }
            return@executes 1
        })


    }
}
