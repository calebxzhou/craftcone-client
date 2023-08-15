package calebxzhou.craftcone.ui

import calebxzhou.craftcone.mc.Mc
import calebxzhou.craftcone.mc.Mcl
import calebxzhou.craftcone.net.protocol.MsgLevel
import calebxzhou.craftcone.net.protocol.MsgType
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeToast
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-08-15,9:01.
 */
fun coneMsg(type: MsgType,level: MsgLevel,msg:String){
    when(type){
        MsgType.Dialog -> ConeDialog.show(level, msg )
        MsgType.Chat -> Mcl.addChatMsg(Component.literal(msg).withStyle(level.chatFormat))
        MsgType.Toast -> Mc.addToast(ConeToast(level,msg))
    }
}
fun coneErr(msg: String){
    coneMsg(MsgType.Dialog,MsgLevel.Err,msg)
}