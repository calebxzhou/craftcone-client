package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.net.ConeNetManager;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created  on 2023-06-28,10:31.
 */
@Mixin(PauseScreen.class)
public class mPauseScreen extends Screen {
    private mPauseScreen(Component component) {
        super(component);
    }

    //点击暂停画面上的按钮，重连服务器
    /*@Inject(method = "createPauseMenu",at=@At("TAIL"))
    private void ad(CallbackInfo ci){
        addRenderableWidget(new Button(0,0,100,20,Component.literal("Reconn"),button -> {
            ConeNetManager.reconnect();
        }));
    }*/
}
