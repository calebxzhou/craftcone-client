package calebxzhou.libertorch.util

import calebxzhou.libertorch.ui.DefaultColors
import calebxzhou.libertorch.ui.LtColor
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-04-27,21:33.
 */

//消息提示类型
enum class RdiInfoType(val id:Byte, val title:String,val toastColor: LtColor,val chatPrefixColor: ChatFormatting) {
    No(
        -1,
        "",
        DefaultColors.White.color,
        ChatFormatting.WHITE
    ),
    Ok(
        0,
        "成功",
        DefaultColors.LightGreen.color,
        ChatFormatting.GREEN,
    ),
    Info(
        1,
        "提示",
        DefaultColors.LightBlue.color,
        ChatFormatting.AQUA,
    ),
    Warn(
        2,
        "警告",
        DefaultColors.LightYellow.color,
        ChatFormatting.GOLD,
    ),
    Err(
        3,
        "错误",
        DefaultColors.LightRed.color,
        ChatFormatting.RED,
    ),
    ;
    //聊天提示前缀样式
    val chatPrefixStyle
        get() = Component.literal(title).withStyle(chatPrefixColor).withStyle(
            ChatFormatting.BOLD).append(Component.literal(">"))
    companion object {
        //id取类型
        private val map = RdiInfoType.values().associateBy { it.id }
        operator fun get(value: Byte) = map[value]?:Ok
    }
}
