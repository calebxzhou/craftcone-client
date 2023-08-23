package calebxzhou.craftcone.net.protocol

import calebxzhou.craftcone.ui.ConeColor
import calebxzhou.craftcone.ui.DefaultColors
import net.minecraft.ChatFormatting

/**
 * Created  on 2023-08-13,20:57.
 */
enum class MsgLevel(val id: Int, val color: ConeColor, val chatFormat: ChatFormatting) {
    Normal(0, DefaultColors.White.color, ChatFormatting.WHITE),
    Info(1, DefaultColors.KleinBlue.color, ChatFormatting.AQUA),
    Ok(2, DefaultColors.OliveGreen.color, ChatFormatting.GREEN),
    Warn(3, DefaultColors.LightYellow.color, ChatFormatting.GOLD),
    Err(4, DefaultColors.LightRed.color, ChatFormatting.RED)
    ;

    companion object {
        //id取类型
        private val map = MsgLevel.values().associateBy { it.id }
        operator fun get(value: Int) = map[value] ?: Info
    }

}
