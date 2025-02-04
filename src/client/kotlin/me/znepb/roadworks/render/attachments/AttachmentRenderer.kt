package me.znepb.roadworks.render.attachments

import me.znepb.roadworks.attachment.Attachment
import me.znepb.roadworks.container.PostContainerBlockEntity
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Direction

interface AttachmentRenderer<T : Attachment> {
    fun render(
        attachment: T,
        blockEntity: PostContainerBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    )

    companion object {
        fun translateForCenter(matrices: MatrixStack, facing: Direction, faceRotation: Int) {
            matrices.translate(0.5F, 0.5F, 0.5F)
            matrices.multiply(facing.rotationQuaternion.rotateXYZ((Math.PI / 2).toFloat(), Math.PI.toFloat(), Math.PI.toFloat() + Math.toRadians(faceRotation.toDouble()).toFloat()))
            matrices.translate(-0.5F, -0.5F, -0.5F)
        }
    }
}