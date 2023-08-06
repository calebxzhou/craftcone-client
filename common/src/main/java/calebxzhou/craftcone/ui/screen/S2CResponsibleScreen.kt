package calebxzhou.craftcone.ui.screen

import calebxzhou.craftcone.net.protocol.Packet


/**
 * Created  on 2023-07-24,11:16.
 */
interface S2CResponsibleScreen<T: Packet> {

    fun onResponse(packet: T)
}