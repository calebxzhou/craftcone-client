package calebxzhou.craftcone.mc.mixin;

import calebxzhou.craftcone.entity.ConeChunkPos;
import calebxzhou.craftcone.entity.ConeRoom;
import calebxzhou.craftcone.misc.ChunkManager;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created  on 2023-08-05,15:00.
 */

@Mixin(ClientChunkCache.class)
public class mChunkSerializer {
    @Shadow @Final
    ClientLevel level;

    // on read
    @Inject(method = "replaceWithPacketData" , at = @At(value = "RETURN",ordinal = 1))
    private void onRead(int cx, int cz, FriendlyByteBuf friendlyByteBuf, CompoundTag compoundTag, Consumer<ClientboundLevelChunkPacketData.BlockEntityTagOutput> consumer, CallbackInfoReturnable<@Nullable LevelChunk> cir){
        Objects.requireNonNull(ConeRoom.getNow()).onReadChunkData(level,new ConeChunkPos(cx,cz));
    }
}
