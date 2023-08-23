package calebxzhou.craftcone.entity

import com.mojang.authlib.GameProfile
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import java.util.*

/**
 * Created  on 2023-08-11,12:09.
 */
data class ConePlayer(
    val id: Int, val name: String
) {
    companion object {
        //当前登录玩家
        var now: ConePlayer? = null
    }

    fun getServerPlayer(server: MinecraftServer): ServerPlayer {
        return ServerPlayer(server, server.overworld(), GameProfile(UUID.randomUUID(), name))
    }
}
