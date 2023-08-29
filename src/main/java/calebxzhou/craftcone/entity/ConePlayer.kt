package calebxzhou.craftcone.entity

import com.mojang.authlib.GameProfile
import net.minecraft.client.multiplayer.PlayerInfo
import org.bson.types.ObjectId
import java.util.*

/**
 * Created  on 2023-08-11,12:09.
 */
data class ConePlayer(
    val id: ObjectId, val name: String
) {
    val mcUuid
        get() = UUID.nameUUIDFromBytes(id.toByteArray())
    val mcGameProfile
        get() = GameProfile(mcUuid, name)
    val mcPlayerInfo
        get() = PlayerInfo(mcGameProfile, false)

    companion object {
        //当前登录玩家
        var now: ConePlayer? = null
    }

}
