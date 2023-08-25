package calebxzhou.craftcone.mc.mixin;

import calebxzhou.craftcone.entity.ConeRoom;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created  on 2023-08-25,9:01.
 */
@Mixin(PlayerTabOverlay.class)
public class mTabOverlayDisplayConeRoomPlayerList {
	@Redirect(method = "getPlayerInfos", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;toList()Ljava/util/List;"))
	private List<PlayerInfo> tabOverlayDisplayConeRoomPlayerList(Stream instance) {
		return Objects.requireNonNull(ConeRoom.getNow()).getPlayerInfos();
	}
}
