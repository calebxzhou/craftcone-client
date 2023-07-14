package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.net.ConeNetManager;
import calebxzhou.craftcone.net.protocol.game.ConeSetBlockPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created  on 2023-06-29,20:31.
 */
@Mixin(Level.class)
abstract class mLevel {

    //广播包：成功设置方块时（通常为放置）
    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z",
            at=@At(value = "HEAD"))
    private void onSetBlock(BlockPos blockPos, BlockState blockState, int i, CallbackInfoReturnable<Boolean> cir){
        if(blockState.getBlock() instanceof LiquidBlock lb){
            if (!blockState.getFluidState().isSource()) {
                //不发送非源头液体
                return;
            }
        }
        ConeNetManager.sendPacket(new ConeSetBlockPacket((Level)(Object)this,blockPos,blockState));
    }

}
