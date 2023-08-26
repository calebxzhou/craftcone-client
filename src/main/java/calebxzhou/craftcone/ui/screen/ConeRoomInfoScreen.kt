package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.entity.ConeRoom
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-08-13,15:21.
 */
class ConeRoomInfoScreen(prevScreen: Screen, private val room: ConeRoom) :
    ConeOkCancelScreen(prevScreen, "房间 $room 的信息") {

    override fun doRender(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        guiGraphics.drawCenteredString(font, "mc版本：${room.mcVersion}", width / 2, 20 * 2, textColor)
        guiGraphics.drawCenteredString(

            font,
            "模式：${if (room.isCreative) "创造" else "生存"}",
            width / 2,
            20 * 3,
            textColor
        )
        guiGraphics.drawCenteredString(font, "创建时间：${room.createTimeStr}", width / 2, 20 * 4, textColor)
        guiGraphics.drawCenteredString(

            font,
            "mod加载器：${if (room.isFabric) "Fabric" else "Forge "}",
            width / 2,
            20 * 5,
            textColor
        )
    }

    override fun onSubmit() {
        ConeRoom.loadRoomLevel(room)
    }

    override fun onPressEnterKey() {
        //必须按确定键加入房间，不允许回车键防止误触
    }
}
