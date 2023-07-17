package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.misc.NeighborUpdateManager;
import calebxzhou.craftcone.net.ConeNetManager;
import calebxzhou.craftcone.net.protocol.game.ConeSaveBlockPacket;
import calebxzhou.craftcone.net.protocol.game.ConeSetBlockPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.redstone.NeighborUpdater;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created  on 2023-06-29,20:31.
 */
@Mixin(Level.class)
abstract class mLevel {


    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
            at=@At(value = "RETURN",ordinal = 3))
    private void onSetBlockAir(BlockPos blockPos, BlockState blockState, int i, int j, CallbackInfoReturnable<Boolean> cir){
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        new ConeSaveBlockPacket((Level)(Object)this, blockPos, blockState).write(buf);
        buf.clear();
    }

}
