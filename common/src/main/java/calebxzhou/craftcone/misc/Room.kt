package calebxzhou.craftcone.misc

import calebxzhou.craftcone.net.protocol.room.RoomInfoS2CPacket
import calebxzhou.craftcone.ui.screen.ConeRoomJoinScreen
import calebxzhou.libertorch.MC
import net.minecraft.core.RegistryAccess
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Difficulty
import net.minecraft.world.level.DataPackConfig
import net.minecraft.world.level.GameRules
import net.minecraft.world.level.GameType
import net.minecraft.world.level.LevelSettings
import net.minecraft.world.level.levelgen.presets.WorldPresets
import java.util.*

/**
 * Created  on 2023-08-02,10:01.
 */
object Room{
    var now : RoomInfoS2CPacket? = null
    val players = hashMapOf<UUID,ServerPlayer>()
    fun loadRoom(rid: String,isCreative: Boolean,seed: Long){
        val levelName = "${MC.user.name}-$rid"
        if(MC.levelSource.levelExists(levelName)){
            MC.createWorldOpenFlows().loadLevel(ConeRoomJoinScreen(),levelName)
        }else{
            val registry = RegistryAccess.builtinCopy().freeze()
            val levelSettings = LevelSettings (
                levelName,
                if(isCreative) GameType.CREATIVE else GameType.SURVIVAL,
                false,
                Difficulty.HARD,
                isCreative,
                GameRules(), DataPackConfig.DEFAULT)
            val worldPresets = WorldPresets.createNormalWorldFromPreset(registry,seed)
            MC.createWorldOpenFlows().createFreshLevel(levelName,
                levelSettings, registry,worldPresets
            )
        }
    }
//TODO 用局域网模式启动
    /*fun initialize(rid: String) {
        ConeDialog.show(ConeDialogType.WARN,"正在初始化房间，禁止退出游戏，禁止断开网络连接！")
        blockStates.forEachIndexed { i: Int, blockState: BlockState ->
            val bState = BlockState.CODEC.encodeStart(NbtOps.INSTANCE, blockState)
            val stateStr = bState.get().left().get().asString
            ConeNetManager.sendPacket(SetBlockStateC2SPacket(i,stateStr))
        }
        ConeDialog.show(ConeDialogType.INFO,"初始化完成")
    }*/
}