package calebxzhou.craftcone.utils

import java.io.File

/**
 * Created  on 2023-04-07,22:59.
 */
object FileUt {
    fun getFileInJarUrl(fileInJar: String): String {
        return FileUt::class.java
            .classLoader
            .getResource(fileInJar)!!.file.replace("%20", " ")
    }
    fun getFileInJar(fileInJar: String): File {
        return File(getFileInJarUrl(fileInJar))
    }
}
