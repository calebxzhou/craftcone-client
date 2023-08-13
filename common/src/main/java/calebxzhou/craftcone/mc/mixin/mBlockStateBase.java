package calebxzhou.craftcone.mc.mixin;

import calebxzhou.craftcone.net.ConeNetSender;
import calebxzhou.craftcone.net.protocol.game.SetBlockC2CPacket;
import calebxzhou.craftcone.utils.LevelUt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Created  on 2023-07-15,23:39.
 */
@Mixin(BlockBehaviour.BlockStateBase.class)
public class mBlockStateBase {
    //广播包：右键点击完方块，就更新它的状态
    @Inject(method = "use",at=@At(value = "RETURN"))
    private void onRightClickBlock(Level level, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir){
        var pos = blockHitResult.getBlockPos();
        var bState = level.getBlockState(pos);
        ConeNetSender.sendPacket(new SetBlockC2CPacket(LevelUt.getDimIdByLevel(level),pos.asLong(), Block.BLOCK_STATE_REGISTRY.getId(bState)));
    }
}
