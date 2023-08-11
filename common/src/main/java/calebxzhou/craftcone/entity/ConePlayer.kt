package calebxzhou.craftcone.entity

import com.mojang.authlib.GameProfile
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import java.util.*

/**
 * Created  on 2023-08-11,12:09.
 */
data class ConePlayer(
    val id:UUID,val name:String
){
    fun getServerPlayer (server:MinecraftServer): ServerPlayer {
        return ServerPlayer(server,server.overworld(), GameProfile(id,name),null)
    }
}
