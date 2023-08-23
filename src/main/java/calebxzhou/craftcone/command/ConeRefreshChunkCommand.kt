package calebxzhou.craftcone.command

import calebxzhou.craftcone.entity.ConeChunkPos
import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.logger
import calebxzhou.craftcone.mc.Mcl
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.game.GetChunkC2SPacket
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-08-10,21:46.
 */
object ConeRefreshChunkCommand {
    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(Commands.literal("cone-refresh-chunk").executes {
            val chunkPosNow = Mcl.player?.chunkPosition() ?: let {
                logger.error("玩家为空时就请求载入区块了")
                return@executes 1
            }
            for (x in chunkPosNow.x - 16..chunkPosNow.x + 16) {
                for (z in chunkPosNow.z - 16..chunkPosNow.z + 16) {
                    val cpos = ConeChunkPos(x, z)
                    val info = "刷新区块中 $x,$z"
                    Mcl.actionBarMsg = Component.literal(info)
                    ConeRoom.now?.let {
                        val packet = GetChunkC2SPacket(it.getDimIdByLevel(Mcl.level ?: let {
                            logger.error("当前游玩的存档为空")
                            return@executes 1
                        }), cpos)
                        ConeNetSender.sendPacket(packet)
                    }

                }
            }
            Mcl.actionBarMsg = Component.literal("刷新区块完成")
            return@executes 1
        })


    }
}
