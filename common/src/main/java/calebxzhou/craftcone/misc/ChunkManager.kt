package calebxzhou.craftcone.misc

import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.coneNetThread
import calebxzhou.craftcone.net.protocol.game.ReadBlockC2SPacket
import calebxzhou.craftcone.utils.LevelUt
import calebxzhou.libertorch.MC
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level

/**
 * Created  on 2023-08-05,15:02.
 */
object ChunkManager {
    @JvmStatic
    fun onRead(level: Level, chunkPos: ChunkPos) {
        if(!MC.isLocalServer)
            return
        coneNetThread {
            ConeNetSender.sendPacket(ReadBlockC2SPacket(LevelUt.getDimIdByLevel(level),chunkPos.toLong()))
        }
    }
}