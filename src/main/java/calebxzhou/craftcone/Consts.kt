package calebxzhou.craftcone

/**
 * Created  on 2023-07-06,7:32.
 */

private const val _regexBsonObjectIdStr = "^[0-9a-fA-F]{24}\$"

//private const val regexUuidStr = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$"
private const val _regexIntStr = "^[+-]?\\d+$"

// val regexUuid  = Regex(regexUuidStr)
val REGEX_OBJID = Regex(_regexBsonObjectIdStr)
val REGEX_INT = Regex(_regexIntStr)
val DEFAULT_PORT = 19198
