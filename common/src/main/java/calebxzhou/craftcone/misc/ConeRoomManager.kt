package calebxzhou.craftcone.misc

import calebxzhou.craftcone.net.ConeNetManager
import calebxzhou.craftcone.net.protocol.game.SetBlockStateC2SPacket
import calebxzhou.craftcone.net.protocol.room.RoomInfoS2CPacket
import calebxzhou.craftcone.ui.overlay.ConeDialog
import calebxzhou.craftcone.ui.overlay.ConeDialogType
import calebxzhou.craftcone.ui.screen.ConeRoomJoinScreen
import calebxzhou.craftcone.utils.blockStates
import calebxzhou.libertorch.MC
import net.minecraft.core.RegistryAccess
import net.minecraft.nbt.NbtOps
import net.minecraft.world.Difficulty
import net.minecraft.world.level.DataPackConfig
import net.minecraft.world.level.GameRules
import net.minecraft.world.level.GameType
import net.minecraft.world.level.LevelSettings
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.presets.WorldPresets

/**
 * Created  on 2023-08-02,10:01.
 */
object ConeRoomManager{
    var now : RoomInfoS2CPacket? = null
    fun loadRoom(rid: String,isCreative: Boolean,seed: Long){
        if(MC.levelSource.levelExists(rid)){
            MC.createWorldOpenFlows().loadLevel(ConeRoomJoinScreen(),rid)
        }else{
            val registry = RegistryAccess.builtinCopy().freeze()
            val levelSettings = LevelSettings (
                rid,
                if(isCreative) GameType.CREATIVE else GameType.SURVIVAL,
                false,
                Difficulty.HARD,
                isCreative,
                GameRules(), DataPackConfig.DEFAULT)
            val worldPresets = WorldPresets.createNormalWorldFromPreset(registry,seed)
            MC.createWorldOpenFlows().createFreshLevel(rid,
                levelSettings, registry,worldPresets
            )
        }
    }

    fun initialize(rid: String) {
        ConeDialog.show(ConeDialogType.WARN,"正在初始化房间，禁止退出游戏，禁止断开网络连接！")
        blockStates.forEachIndexed { i: Int, blockState: BlockState ->
            val bState = BlockState.CODEC.encodeStart(NbtOps.INSTANCE, blockState)
            val stateStr = bState.get().left().get().asString
            ConeNetManager.sendPacket(SetBlockStateC2SPacket(i,stateStr))
        }
        ConeDialog.show(ConeDialogType.INFO,"初始化完成")
    }
}