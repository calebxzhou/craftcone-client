package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.entity.Room
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.Screen

/**
 * Created  on 2023-08-13,15:21.
 */
class ConeRoomInfoScreen(prevScreen: Screen, private val room: Room) :
    ConeOkCancelScreen(prevScreen, "房间 $room 的信息") {

    override fun doRender(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        drawCenteredString(poseStack, font, "名称：${room.name}", width / 2, 20, textColor)
        drawCenteredString(poseStack, font, "mc版本：${room.mcVersion}", width / 2, 20 * 2, textColor)
        drawCenteredString(
            poseStack,
            font,
            "模式：${if (room.isCreative) "创造" else "生存"}",
            width / 2,
            20 * 3,
            textColor
        )
        drawCenteredString(poseStack, font, "创建时间：${room.createTimeStr}", width / 2, 20 * 4, textColor)
        drawCenteredString(
            poseStack,
            font,
            "mod加载器：${if (room.isFabric) "Fabric" else "Forge "}",
            width / 2,
            20 * 5,
            textColor
        )
    }

    override fun onSubmit() {
        Room.loadAndJoin(room)
    }

    override fun onPressEnterKey() {
        //必须按确定键加入房间，不允许回车键防止误触
    }
}