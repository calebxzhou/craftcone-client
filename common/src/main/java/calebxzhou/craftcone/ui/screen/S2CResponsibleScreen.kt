package calebxzhou.craftcone.ui.screen

/**
 * Created  on 2023-07-24,11:16.
 */
interface S2CResponsibleScreen<T: S2CPacket> {

    fun onResponse(packet: T)
}