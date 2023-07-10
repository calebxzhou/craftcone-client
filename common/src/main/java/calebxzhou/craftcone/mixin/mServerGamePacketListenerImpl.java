package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.net.ConeNetManager;
import calebxzhou.craftcone.net.protocol.ConeSetBlockPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Created  on 2023-07-01,9:34.
 */
@Mixin(ServerGamePacketListenerImpl.class)
abstract class mServerGamePacketListenerImpl {
    @Shadow public ServerPlayer player;

    //广播包：成功设置方块时（玩家使用物品）
   /* @Inject(method = "handleUseItemOn",at=@At(value = "INVOKE",target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V",ordinal = 0))
    private void onPlayerUseItemOn(ServerboundUseItemOnPacket packet, CallbackInfo ci){
        ServerLevel level = player.getLevel();
        BlockPos blockPos = packet.getHitResult().getBlockPos();
        ConeNetManager.checkAndSendPacket(new ConeSetBlockPacket(level,blockPos,level.getBlockState(blockPos)));
    }*/
}
