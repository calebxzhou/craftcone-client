package calebxzhou.craftcone.ui.screen

import io.netty.buffer.ByteBuf


/**
 * Created  on 2023-07-24,11:16.
 */
interface OkResponseScreen {
    fun onOk(data: ByteBuf)
}
