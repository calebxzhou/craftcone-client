package calebxzhou.craftcone.net

import io.netty.util.concurrent.DefaultThreadFactory
import java.util.concurrent.Executors

/**
 * Created  on 2023-08-01,11:31.
 */
public fun coneNetThread(todo: ()->Unit){
    ConeNetThread.thPool.submit(todo)
}
object ConeNetThread {
    val thPool = Executors.newFixedThreadPool(4,DefaultThreadFactory("ConeNetThreadPool"))
}