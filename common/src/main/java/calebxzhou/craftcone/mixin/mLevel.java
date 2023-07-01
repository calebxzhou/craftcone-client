package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.net.ConePacketSender;
import calebxzhou.craftcone.net.protocol.ConeSetBlockPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
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
    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
            at=@At(value = "RETURN",ordinal = 3))
    private void onSetBlock(BlockPos pos, BlockState state, int flags, int recursionLeft, CallbackInfoReturnable<Boolean> cir){
        Level level = (Level)(Object)this;
        ConePacketSender.checkAndSendPacket(new ConeSetBlockPacket(level,pos,state));
    }

}
