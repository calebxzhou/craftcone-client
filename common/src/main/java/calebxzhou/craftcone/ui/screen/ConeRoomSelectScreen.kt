package calebxzhou.craftcone.ui.screen

import calebxzhou.libertorch.mc.gui.LtScreen
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component

/**
 * Created  on 2023-06-20,10:00.
 */
class ConeRoomSelectScreen() : LtScreen(("ConeServerSelectScreen")) {
    private lateinit var joinBtn: Button
    private lateinit var createBtn: Button
    override fun init() {
        joinBtn = Button(150,150,100,20, Component.literal("加入/创建房间"),::onJoinRoom)
        createBtn = Button(300,300,100,20,Component.literal("创建房间"),::onCreateRoom)
        addRenderableWidget(joinBtn)
        addRenderableWidget(createBtn)
    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(poseStack, mouseX, mouseY, partialTick)

    }


    private fun onCreateRoom(button: Button) {

    }

    override fun shouldCloseOnEsc(): Boolean {
        return true
    }

    private fun onJoinRoom(button: Button) {

    }
}