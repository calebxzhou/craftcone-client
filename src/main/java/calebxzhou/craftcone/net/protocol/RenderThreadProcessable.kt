package calebxzhou.craftcone.net.protocol


/**
 * Created  on 2023-07-21,22:21.
 */
interface RenderThreadProcessable : Packet {
    //处理数据
    fun process()
}
