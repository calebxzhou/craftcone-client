package calebxzhou.craftcone.test

/**
 * Created  on 2023-07-06,7:20.
 */
fun main() {
    val byte1 = 0b10000010
    val bit1 = byte1 shr 7
    val packetId = (byte1 shl 1).toByte().toInt()
    println(bit1)
    println(packetId)
}