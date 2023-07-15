package calebxzhou.craftcone

import calebxzhou.craftcone.net.ConeClientChannelHandler
import calebxzhou.craftcone.net.ConeNetManager
import calebxzhou.craftcone.net.protocol.ConePacketSet
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress


const val MOD_ID = "craftcone"
val LOG: Logger = LoggerFactory.getLogger("CraftCone")


object Cone {
    //维度编号与维度（eg 0=overworld 1=the_end 2=the_nether）
    val numDimKeyMap = hashMapOf<Int,ResourceKey<Level>>()
    var inGame = false
    @JvmStatic
    fun init() {
        ConePacketSet
        ConePacketSet.InGame
        ConePacketSet.OutGame
        Events.register()
        ConeNetManager
    }
}
/*


 */