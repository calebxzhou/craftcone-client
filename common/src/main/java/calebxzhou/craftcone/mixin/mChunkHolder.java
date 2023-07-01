package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.net.ConePacketSender;
import calebxzhou.craftcone.net.protocol.ConeSetBlockPacket;
import calebxzhou.craftcone.net.protocol.ConeSetSectionPacket;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
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

    //广播包：成功设置方块时（section单个方块更新）
    /*
    intellij的mc dev插件会报错Method parameters do not match expected parameters for Inject ，是bug不用管，他多加了一个int就mixin失败了  */
    @Inject(method = "broadcastChanges",at=@At(value = "INVOKE",target = "Lnet/minecraft/server/level/ChunkHolder;broadcast(Lnet/minecraft/network/protocol/Packet;Z)V",ordinal = 1),locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void onBroadcastSectionBlockSingleChange(LevelChunk levelChunk, CallbackInfo ci, Level level, int i,  ShortSet shortSet, int k, SectionPos sectionPos, BlockPos blockPos, BlockState blockState){
        ConePacketSender.checkAndSendPacket(new ConeSetBlockPacket(level,blockPos,blockState));
    }

    //广播包：成功设置方块时（section多个方块更新）
    @Inject(method = "broadcastChanges",at=@At(value = "INVOKE",target = "Lnet/minecraft/server/level/ChunkHolder;broadcast(Lnet/minecraft/network/protocol/Packet;Z)V",ordinal = 2),locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void onBroadcastSectionBlockMultiChanges(LevelChunk chunk, CallbackInfo ci, Level level, int i,  ShortSet shortSet, int k, SectionPos sectionPos, LevelChunkSection section, ClientboundSectionBlocksUpdatePacket clientboundSectionBlocksUpdatePacket){
        ConePacketSender.checkAndSendPacket(ConeSetSectionPacket.create(level, chunk.getPos(), sectionPos, shortSet, section));
    }
    //广播包：block state有更新时
    @Inject(method = "broadcastBlockEntity",at=@At(value = "INVOKE",target = "Lnet/minecraft/server/level/ChunkHolder;broadcast(Lnet/minecraft/network/protocol/Packet;Z)V"))
    private void onBroadcastBlockEntity(Level level, BlockPos blockPos, CallbackInfo ci){
        //TODO ConePacketSender.checkAndSendPacket
    }
}
