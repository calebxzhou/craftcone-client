package calebxzhou.craftcone.net.protocol

import calebxzhou.craftcone.ui.ConeColor
import calebxzhou.craftcone.ui.DefaultColors
import net.minecraft.ChatFormatting

/**
 * Created  on 2023-08-13,20:57.
 */
enum class MsgLevel(val id:Int, val color:ConeColor, val chatFormat: ChatFormatting) {
    Info(0,DefaultColors.KleinBlue.color,ChatFormatting.AQUA),
    Ok(1,DefaultColors.OliveGreen.color,ChatFormatting.GREEN),
    Warn(2,DefaultColors.LightYellow.color,ChatFormatting.GOLD),
    Err(3,DefaultColors.LightRed.color,ChatFormatting.RED)
    ;

    companion object {
        //id取类型
        private val map = MsgLevel.values().associateBy { it.id }
        operator fun get(value: Int) = map[value]?: Info
    }

}