package calebxzhou.rdi

import calebxzhou.libertorch.MC
import calebxzhou.rdi.ui.RdiTitleScreen

/**
 * Created  on 2023-08-07,21:54.
 */
fun goRdiTitleScreen(){
    MC.execute {
        MC.setScreen(RdiTitleScreen())
    }
}