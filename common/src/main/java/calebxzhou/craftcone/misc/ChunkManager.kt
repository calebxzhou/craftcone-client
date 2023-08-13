package calebxzhou.craftcone.misc

import calebxzhou.craftcone.entity.Room
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.protocol.game.ReadBlockC2SPacket
import calebxzhou.craftcone.utils.LevelUt
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level

/**
 * Created  on 2023-08-05,15:02.
 */
object ChunkManager {
    @JvmStatic
    fun onRead(level: Level, chunkPos: ChunkPos) {
        if(Room.now == null)
            return
        ConeNetSender.sendPacket(ReadBlockC2SPacket(LevelUt.getDimIdByLevel(level),chunkPos.toLong()))
    }
}