package calebxzhou.libertorch

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created  on 2023-04-07,22:53.
 */
object SerDesJson {
    @JvmField
    val gson: Gson = GsonBuilder()
        .enableComplexMapKeySerialization()
        .serializeSpecialFloatingPointValues()
        .serializeNulls().create()
}
