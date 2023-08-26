package calebxzhou.craftcone.mc.mixin;

import calebxzhou.craftcone.misc.NeighborUpdateManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.redstone.CollectingNeighborUpdater;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created  on 2023-07-15,22:50.
 */
@Mixin(CollectingNeighborUpdater.class)
public class mCollectingNeighborUpdater {
	@Inject(method = "addAndRun", at = @At(value = "HEAD"/*,target = "Ljava/util/ArrayDeque;push(Ljava/lang/Object;)V"*/))
	private void onAdd(BlockPos blockPos, CollectingNeighborUpdater.NeighborUpdates neighborUpdates, CallbackInfo ci) {
		BlockPos bpos = null;
		if (neighborUpdates instanceof CollectingNeighborUpdater.ShapeUpdate update) {
			bpos = update.pos();
		}
		if (neighborUpdates instanceof CollectingNeighborUpdater.SimpleNeighborUpdate update) {
			bpos = update.pos();
		}
		if (neighborUpdates instanceof CollectingNeighborUpdater.MultiNeighborUpdate) {
			bpos = ((aMultiNeighborUpdate) neighborUpdates).getSourcePos();
		}
		if (neighborUpdates instanceof CollectingNeighborUpdater.FullNeighborUpdate update) {
			bpos = update.pos();
		}
		if (bpos != null) {
			NeighborUpdateManager.onAdd(bpos);
		}

	}

	@Inject(method = "runUpdates", at = @At(value = "INVOKE", target = "Ljava/util/ArrayDeque;clear()V"))
	private void onClear(CallbackInfo ci) {
		NeighborUpdateManager.onClear();
	}
}
