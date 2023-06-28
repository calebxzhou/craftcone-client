package calebxzhou.craftcone.utils

/**
 * Created  on 2023-06-28,20:45.
 */
object NetworkUtils {
    fun ByteArray.toBigEndian(): ByteArray {
        val length = this.size
        val res = ByteArray(length)
        for (i in 0 until length) {
            res[length - i - 1] = this[i]
        }
        return res
    }
}