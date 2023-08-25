package calebxzhou.craftcone.mc.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Created  on 2023-08-10,22:18.
 */

@Mixin(Gui.class)
public class mAlwaysEnableTabOverlay {
	//按TAB永远可以看到玩家列表
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isLocalServer()Z"))
	private boolean isLocalServer(Minecraft instance) {
		return false;
	}
}
