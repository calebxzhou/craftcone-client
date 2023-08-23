package calebxzhou.craftcone.entity

/**
 * Created  on 2023-08-17,11:12.
 */
data class ConeChunkPos(val x: Int, val z: Int) {
    val asLong
        get() = x.toLong() shl 32 or z.toLong()
    val asInt: Int
        get() = x.toShort().toInt() shl 16 or z.toShort().toInt()

    override fun toString(): String {
        return "$x,$z"
    }
}
