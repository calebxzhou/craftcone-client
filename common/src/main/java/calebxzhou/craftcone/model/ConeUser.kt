package calebxzhou.craftcone.model

import java.util.*

/**
 * Created  on 2023-07-13,20:29.
 */
data class ConeUser(
    val pid: UUID,
    val pwd:String,

    val pName: String,
){
    companion object{
        var now : ConeUser? = null
    }
}
