package calebxzhou.craftcone.mc

import calebxzhou.libertorch.MC

/**
 * Created  on 2023-08-12,16:21.
 */
object Mc {
    var screen
        get() = MC.screen
        set(value) = MC.setScreen(value)
    val playerName
        get() = MC.user.name
}