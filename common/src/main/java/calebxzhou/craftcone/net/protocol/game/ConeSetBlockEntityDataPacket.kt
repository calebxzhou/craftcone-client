package calebxzhou.craftcone.net.protocol.game

import calebxzhou.craftcone.net.protocol.ConeInGamePacket
import calebxzhou.libertorch.MC
import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.CommandBlockEntity

/**
 * Created  on 2023-07-01,18:06.
 */
data class ConeSetBlockEntityDataPacket(
    val level: Level,
    val pos: BlockPos,
    val type:BlockEntityType<*>?,
    val tag:CompoundTag?,
): ConeInGamePacket {

    companion object{
        fun read(buf: FriendlyByteBuf): ConeSetBlockEntityDataPacket {
            return ConeSetBlockEntityDataPacket(
                ConeInGamePacket.getLevelByDimId(buf.readByte().toInt()),
                buf.readBlockPos(),
                buf.readById(Registry.BLOCK_ENTITY_TYPE),
                buf.readNbt()
            )
        }
    }
    override fun write(buf: FriendlyByteBuf) {
        buf.writeByte(ConeInGamePacket.getDimIdByLevel(level))
        buf.writeBlockPos(pos)
        buf.writeId(Registry.BLOCK_ENTITY_TYPE,type)
        buf.writeNbt(tag)
    }


    override fun process() {
        level.getBlockEntity(pos,type).ifPresent { blockEntity ->
            if(tag != null){
                blockEntity.load(tag)
            }
            if (blockEntity is CommandBlockEntity && MC?.screen is CommandBlockEditScreen) {
                (MC?.screen as CommandBlockEditScreen).updateGui()
            }
        }
    }

}