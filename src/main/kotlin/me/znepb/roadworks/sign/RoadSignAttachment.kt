package me.znepb.roadworks.sign

import me.znepb.roadworks.RoadworksRegistry
import me.znepb.roadworks.container.PostContainerBlockEntity
import me.znepb.roadworks.attachment.Attachment
import me.znepb.roadworks.attachment.AttachmentPosition
import me.znepb.roadworks.attachment.PositionableAttachment
import me.znepb.roadworks.util.Charset
import me.znepb.roadworks.util.RotateVoxelShape
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import org.joml.Vector3d

class RoadSignAttachment(
    container: PostContainerBlockEntity,
) : PositionableAttachment(RoadworksRegistry.ModAttachments.ROAD_SIGN_ATTACHMENT, container) {
    var color = "green"
    var contents = listOf<Charset>()

    private fun getContentsPixelWidth(): Float {
        var size = -1
        contents.forEach {
            size += (it.w + 1)
        }

        val pixelCount = size.toFloat()

        return (pixelCount + 8) / 4
    }

    override fun getShape(context: ShapeContext): VoxelShape {
        val width = this.getContentsPixelWidth()
        val thickness = this.container.thickness.thickness * 0.5
        val offsetHeight = when(this.position) {
            AttachmentPosition.TOP -> 0.75
            AttachmentPosition.MIDDLE -> 0.375
            AttachmentPosition.BOTTOM -> 0.0
        }

        val shape = BlockWithEntity.createCuboidShape(
            (8 - width / 2).toDouble(),
            0.0,
            7.5,
            (8 + width / 2).toDouble(),
            4.0,
            8.5
        ).offset(0.0, offsetHeight, 0.0)

        return RotateVoxelShape.offsetFromDirectionXZ(
            RotateVoxelShape.rotateVoxelShape(shape, Direction.NORTH, this.facing),
            facing,
            Vector3d(0.0, 0.0, -thickness),
            Vector3d(thickness, 0.0, 0.0,),
            Vector3d(0.0, 0.0, thickness),
            Vector3d(-thickness, 0.0, 0.0),
        )
    }

    override fun writeNBT(nbt: NbtCompound) {
        val contents = mutableListOf<Int>()
        this.contents.forEach {
            contents.add(it.ordinal)
        }

        nbt.putString("color", color)
        nbt.putIntArray("contents", contents)
        super.writeNBT(nbt)
    }

    override fun readNBT(nbt: NbtCompound) {
        super.readNBT(nbt)

        this.color = nbt.getString("color")
        val contents = nbt.getIntArray("contents")

        val newList = mutableListOf<Charset>()
        contents.forEach {
            newList.add(Charset.entries[it])
        }
        this.contents = newList
    }

    override fun isPositionable() = true
}