package calebxzhou.craftcone.ui.screen

import net.minecraft.network.FriendlyByteBuf


/**
 * Created  on 2023-07-24,11:16.
 */
interface OkResponseScreen {
    fun onOk(data: FriendlyByteBuf)
}
