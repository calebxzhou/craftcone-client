package calebxzhou.craftcone.misc

import calebxzhou.craftcone.entity.ConeRoom
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.game.GetChunkPacket
import calebxzhou.craftcone.utils.LevelUt
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level

/**
 * Created  on 2023-08-05,15:02.
 */
object ChunkManager {
    @JvmStatic
    fun onRead(level: Level, chunkPos: ChunkPos) {
        if(ConeRoom.now == null)
            return
        ConeNetSender.sendPacket(GetChunkPacket(LevelUt.getDimIdByLevel(level),chunkPos.toLong()))
    }
}