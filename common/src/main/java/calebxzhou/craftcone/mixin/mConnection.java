package calebxzhou.craftcone.mixin;

import calebxzhou.craftcone.NetworkManager;
import calebxzhou.craftcone.PacketsToBroadcast;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.*;
import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.ByteBuffer;

/**
 * Created  on 2023-06-20,22:21.
 */
@Mixin(Connection.class)
public class mConnection {

    /*
    S2C广播：
    添加实体
    添加经验球
    添加玩家
    动画

     */
    //转发数据包，本地C2S包 广播给同一房间所有人
    /*@Inject(method = "doSendPacket",at=@At(value = "INVOKE",target = "Lio/netty/channel/Channel;writeAndFlush(Ljava/lang/Object;)Lio/netty/channel/ChannelFuture;"))
    private void broadcastPacket(Packet<?> packet, @Nullable PacketSendListener packetSendListener, ConnectionProtocol connectionProtocol, ConnectionProtocol connectionProtocol2, CallbackInfo ci){
        //不发的情况
        if(PacketsToBroadcast.c2sNotSend.contains(packet)){
            return;
        }
        NetworkManager.sendPacket(packet);


    }*/

    //本地S2C包
    @Inject(method = "genericsFtw",
            at=@At(value = "HEAD" ))
    private static void onRecv(Packet<? extends PacketListener> packet, PacketListener packetListener, CallbackInfo ci){
        NetworkManager.sendPacket(packet);
    }

}