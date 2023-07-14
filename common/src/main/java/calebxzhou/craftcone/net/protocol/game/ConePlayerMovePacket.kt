package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.net.protocol.ConeInGamePacket
import calebxzhou.libertorch.MC
import calebxzhou.libertorch.MCS
import net.minecraft.network.FriendlyByteBuf
import java.util.UUID

/**
 * Created  on 2023-07-13,10:21.
 */
data class ConePlayerMovePacket(
    val pid: UUID,
    val x:Float,
    val y:Float,
    val z:Float,
) : ConeInGamePacket {
    override fun write(buf: FriendlyByteBuf) {
        buf.writeUUID(pid)
        buf.writeFloat(x)
        buf.writeFloat(y)
        buf.writeFloat(z)
    }

    override fun process() {
        val player = MCS.playerList.getPlayer(MC.user.profileId)

    }


}