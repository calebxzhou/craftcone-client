package calebxzhou.craftcone

/**
 * Created  on 2023-07-06,7:32.
 */

object Consts {
    //private const val regexUuidStr = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$"
    private const val regexIntStr = "^[+-]?\\d+$"

    // val regexUuid  = Regex(regexUuidStr)
    val regexInt = Regex(regexIntStr)
    val DefaultPort = 19198
}
