package calebxzhou.craftcone

import calebxzhou.craftcone.mc.Events
import calebxzhou.craftcone.net.ConeNetSender
import calebxzhou.craftcone.net.ConePacketSet
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LoggerContext
import org.apache.logging.log4j.core.config.Configurator
import org.apache.logging.log4j.core.config.LoggerConfig
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

        val ctx = LogManager.getContext(false) as LoggerContext
        val config = ctx.configuration
        val loggerConfig: LoggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME)
        loggerConfig.level = Level.DEBUG
        ctx.updateLoggers()
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.DEBUG);
    }
}
