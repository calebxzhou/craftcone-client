package calebxzhou.craftcone.entity

import com.mojang.authlib.GameProfile
import net.minecraft.client.multiplayer.PlayerInfo
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import java.util.*

/**
 * Created  on 2023-08-11,12:09.
 */
data class ConePlayer(
    val id: Int, val name: String
) {
    val mcUuid
        get() = UUID(0, id.toLong())
    val mcGameProfile
        get() = GameProfile(mcUuid, name)
    val mcPlayerInfo
        get() = PlayerInfo(mcGameProfile, false)

    companion object {
        //当前登录玩家
        var now: ConePlayer? = null
    }

}
