package calebxzhou.rdi.mixin;

import calebxzhou.rdi.ui.RdiTitleScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created  on 2023-07-10,13:11.
 */
@Mixin(TitleScreen.class)
public class mTitleScreen extends Screen {
    protected mTitleScreen(Component component) {
        super(component);
    }

    //默认进入rdi4的载入界面

    @Inject(method = "init",at=@At(value = "HEAD"  ), cancellable = true)
    private void init2(CallbackInfo ci){
        Minecraft.getInstance().setScreen(new RdiTitleScreen());
        ci.cancel();
    }
}
