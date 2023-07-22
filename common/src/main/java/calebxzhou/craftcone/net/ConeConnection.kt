package calebxzhou.craftcone.net

import io.netty.channel.ChannelFuture

/**
 * Created  on 2023-07-22,14:48.
 */
data class ConeConnection(val channelFuture: ChannelFuture, val channelHandler: ConeClientChannelHandler)
