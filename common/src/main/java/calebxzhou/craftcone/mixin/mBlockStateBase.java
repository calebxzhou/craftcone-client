package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.net.ConeNetManager;
import calebxzhou.craftcone.net.protocol.game.ConeSetBlockPacket;
import dev.architectury.event.EventResult;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
    //广播包：右键点击完方块
    @Inject(method = "use",at=@At(value = "RETURN"))
    private void onRightClickBlock(Level level, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir){
        var pos = blockHitResult.getBlockPos();
        var bState = level.getBlockState(pos);
        ConeNetManager.sendPacket(new ConeSetBlockPacket(level,pos,bState));
    }
}
