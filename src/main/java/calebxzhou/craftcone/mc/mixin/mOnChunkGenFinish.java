package calebxzhou.craftcone.mc.mixin;

import calebxzhou.craftcone.misc.ChunkGenManager;
import net.minecraft.core.Registry;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created  on 2023-08-26,17:56.
 */

@Mixin(ProtoChunk.class)
abstract class mOnChunkGenFinish extends ChunkAccess {
	@Shadow
	private volatile ChunkStatus status;

	private mOnChunkGenFinish(ChunkPos chunkPos, UpgradeData upgradeData, LevelHeightAccessor levelHeightAccessor, Registry<Biome> biomeRegistry, long inhabitedTime, @Nullable LevelChunkSection[] sections, @Nullable BlendingData blendingData) {
		super(chunkPos, upgradeData, levelHeightAccessor, biomeRegistry, inhabitedTime, sections, blendingData);
	}

	@Inject(method = "setStatus", at = @At("HEAD"))
	private void onChunkGenFinish(ChunkStatus status, CallbackInfo ci) {
		if (status == ChunkStatus.SPAWN) {
			ChunkGenManager.removeGeneratingChunk(this);
		}
	}
}
