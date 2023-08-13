package calebxzhou.craftcone.mc.mixin;

import calebxzhou.craftcone.ui.components.ConeButton;
import calebxzhou.craftcone.ui.screen.ConeConnectScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TitleScreen.class)
public class mTitleScreen extends Screen {

    //禁用单人游戏 realms
    @Redirect(method = "createNormalMenuOptions",at=@At(value = "INVOKE",target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;",
    ordinal = 0))
    private GuiEventListener noSinglePlayer(TitleScreen instance, GuiEventListener guiEventListener){

        return null;
    }
    @Redirect(method = "createNormalMenuOptions",at=@At(value = "INVOKE",target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;",
    ordinal = 2))
    private GuiEventListener noRealms(TitleScreen instance, GuiEventListener guiEventListener){

        return new ConeButton(0,0,0,"",(b)-> null);
    }

    //点标题画面的多人游戏，迁移到cone的服务器选择画面
    @Redirect(method = "method_19860",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"))
    private void goConeLogin(Minecraft mc, Screen screen){
        mc.setScreen(new ConeConnectScreen(this));
    }

    private mTitleScreen(Component component) {
        super(component);
    }
}