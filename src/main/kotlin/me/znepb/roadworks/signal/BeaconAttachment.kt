package me.znepb.roadworks.signal

import me.znepb.roadworks.RoadworksRegistry
import me.znepb.roadworks.container.PostContainerBlockEntity
import me.znepb.roadworks.attachment.AttachmentType
import me.znepb.roadworks.util.RotateVoxelShape
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import org.joml.Vector3d

open class BeaconAttachment(signalType: SignalType, attachmentType: AttachmentType<*>, container: PostContainerBlockEntity, ) : AbstractSignalAttachment(signalType, attachmentType, container) {
    companion object {
        val SIGNAL_SHAPE = BlockWithEntity.createCuboidShape(5.0, 5.0, 7.5, 11.0, 11.0, 8.5)
        const val HALF = 0.03125
    }

    override fun getShape(context: ShapeContext): VoxelShape {
        val thickness = this.container.thickness.thickness * 0.5

        return RotateVoxelShape.offsetFromDirectionXZ(
            RotateVoxelShape.rotateVoxelShape(SIGNAL_SHAPE, Direction.NORTH, this.facing),
            facing,
            Vector3d(0.0, 0.0, -thickness - HALF),
            Vector3d(thickness + HALF, 0.0, 0.0),
            Vector3d(0.0, 0.0, thickness + HALF),
            Vector3d(-thickness - HALF, 0.0, 0.0),
        )
    }

    class Red(container: PostContainerBlockEntity) : BeaconAttachment(SignalType.BEACON_RED, RoadworksRegistry.ModAttachments.BEACON_RED, container)
    class Yellow(container: PostContainerBlockEntity) : BeaconAttachment(SignalType.BEACON_YELLOW, RoadworksRegistry.ModAttachments.BEACON_YELLOW, container)
    class Green(container: PostContainerBlockEntity) : BeaconAttachment(SignalType.BEACON_GREEN, RoadworksRegistry.ModAttachments.BEACON_GREEN, container)
}