package calebxzhou.craftcone.model

import java.util.*

/**
 * Created  on 2023-07-13,20:29.
 */
data class ConeUser(
    val pid: UUID,
    val pName: String,
){
    companion object{
        var currentUser : ConeUser? = null
    }
}
