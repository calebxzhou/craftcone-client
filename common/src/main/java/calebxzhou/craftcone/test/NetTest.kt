package calebxzhou.craftcone.test

import java.util.*

/**
 * Created  on 2023-07-06,7:20.
 */
fun main() {
   // ConeNetManager.reconnect()
    val scanner = Scanner(System.`in`)
    var inputString: String?
    do {
        inputString = scanner.nextLine()
        //ConeNetManager.checkAndSendPacket(ConeChatPacket(UUID(0L,0L),inputString))
    } while (inputString != "stop")
}