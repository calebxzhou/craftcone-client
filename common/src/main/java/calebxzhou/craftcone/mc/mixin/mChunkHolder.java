package calebxzhou.craftcone.mc.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Created  on 2023-07-01,9:43.
 */
@Mixin(ChunkHolder.class)
public class mChunkHolder {

    //广播包：block state有更新时
    @Inject(method = "broadcastBlockEntity",locals = LocalCapture.CAPTURE_FAILEXCEPTION,at=@At(value = "INVOKE",target = "Lnet/minecraft/server/level/ChunkHolder;broadcast(Lnet/minecraft/network/protocol/Packet;Z)V"))
    private void onBroadcastBlockEntity(Level level, BlockPos blockPos, CallbackInfo ci, BlockEntity entity, Packet<?> packet){
       /* if(packet instanceof ClientboundBlockEntityDataPacket pack){
            ConeNetManager.checkAndSendPacket(new ConeSetBlockEntityDataPacket(level,blockPos,pack.getType(),pack.getTag()));
        }*/
    }
}
