package calebxzhou.craftcone

import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.ConePacketSet
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer

/**
 * Created  on 2023-08-23,21:22.
 */
public object QuiltEntry : ModInitializer {
    override fun onInitialize(mod: ModContainer?) {
        ConePacketSet
        Events.register()
        ConeNetSender
        System.setProperty("java.net.preferIPv4Stack", "false")
        System.setProperty("java.net.preferIPv6Addresses", "true")
    }
}
