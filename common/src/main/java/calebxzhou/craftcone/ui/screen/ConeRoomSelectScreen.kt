package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.Consts
import calebxzhou.libertorch.MC
import calebxzhou.libertorch.mc.gui.LtScreen
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-06-20,10:00.
 */
class ConeRoomSelectScreen() : LtScreen(("ConeServerSelectScreen")) {
    private lateinit var joinBtn: Button
    private lateinit var createBtn: Button
    private lateinit var roomIdBox : EditBox
    override fun init() {
        joinBtn = Button(100,height-30,80,20, Component.literal("加入"),::onJoinRoom)
        createBtn = Button(180,height-30,80,20,Component.literal("创建房间"),::onCreateRoom)
        //00000000-0000-0000-0000-000000000000
        roomIdBox  = EditBox(font,width/3,50,250,20, Component.literal("房间ID"))
        roomIdBox.setMaxLength(36)

        addRenderableWidget(joinBtn)
        addRenderableWidget(createBtn)
        addRenderableWidget(roomIdBox)
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBg()
        drawCenteredString(
            poseStack,
            font, "房间选择", width / 2, 17, fontColor
        )
        drawString(poseStack,font,"房间ID",width/4,50,fontColor)

        super.render(poseStack, mouseX, mouseY, partialTick)

    }

    override fun tick() {
        joinBtn.visible = roomIdBox.value.isNotEmpty() && roomIdBox.value.matches(Consts.regexUuid)

        super.tick()
    }

    private fun onCreateRoom(button: Button) {
        MC.setScreen(ConeRoomSelectScreen())
    }

    override fun shouldCloseOnEsc(): Boolean {
        return true
    }

    private fun onJoinRoom(button: Button) {

    }
}