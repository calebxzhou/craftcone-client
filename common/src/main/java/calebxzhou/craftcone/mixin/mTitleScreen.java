package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.ui.screen.ConeRoomJoinScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TitleScreen.class)
public class mTitleScreen extends Screen {


    //点标题画面的多人游戏，迁移到cone的服务器选择画面
    @Redirect(method = "method_19860",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"))
    private void goConeLogin(Minecraft mc, Screen screen){
        mc.setScreen(new ConeRoomJoinScreen());
    }

    private mTitleScreen(Component component) {
        super(component);
    }
}