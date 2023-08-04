package calebxzhou.craftcone

import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.ConePacketSet
import mu.KotlinLogging


const val MOD_ID = "craftcone"
val logger = KotlinLogging.logger("CraftCone")


object Cone {

    @JvmStatic
    fun init() {
        ConePacketSet
        Events.register()
        ConeNetSender
    }
}
/*


 */