package me.znepb.roadworks.sign

import me.znepb.roadworks.RoadworksMain
import me.znepb.roadworks.RoadworksMain.ModId
import me.znepb.roadworks.RoadworksMain.logger
import me.znepb.roadworks.RoadworksRegistry.ModAttachments.SIGN_ATTACHMENT
import me.znepb.roadworks.container.PostContainerBlockEntity
import me.znepb.roadworks.attachment.Attachment
import me.znepb.roadworks.util.RotateVoxelShape.Companion.offsetFromDirectionXZ
import me.znepb.roadworks.util.RotateVoxelShape.Companion.rotateVoxelShape
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import org.joml.Vector3d

class SignAttachment(
    container: PostContainerBlockEntity,
) : Attachment(SIGN_ATTACHMENT, container) {
    var signType = ModId("unknown")

    companion object {
        val SIGN_SHAPE_WALL = BlockWithEntity.createCuboidShape(0.0, 0.0, 7.75, 16.0, 16.0, 8.25)
    }

    fun getSignData() = RoadworksMain.signageManager.getSign(signType)

    override fun readNBT(nbt: NbtCompound) {
        val newSignType = Identifier.tryParse(nbt.getString("sign_type"))
        if(newSignType == null) {
            logger.warn("Invalid sign identifier: {}", nbt.getString(("sign_type")))
        } else {
            signType = newSignType
        }

        super.readNBT(nbt)
    }

    override fun writeNBT(nbt: NbtCompound) {
        nbt.putString("sign_type", signType.toString())
        super.writeNBT(nbt)
    }

    override fun getShape(context: ShapeContext): VoxelShape {
        val thickness = this.container.thickness.thickness * 0.5

        return offsetFromDirectionXZ(
            rotateVoxelShape(SIGN_SHAPE_WALL, Direction.NORTH, this.facing),
            facing,
            Vector3d(0.0, 0.0, -thickness),
            Vector3d(thickness, 0.0, 0.0,),
            Vector3d(0.0, 0.0, thickness),
            Vector3d(-thickness, 0.0, 0.0),
        )
    }
}