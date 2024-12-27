package me.znepb.roadworks.render.attachments

import me.znepb.roadworks.RoadworksMain
import me.znepb.roadworks.attachment.AttachmentPosition
import me.znepb.roadworks.container.PostContainerBlockEntity
import me.znepb.roadworks.signal.BeaconAttachment
import me.znepb.roadworks.train.TrainBellAttachment
import me.znepb.roadworks.util.RenderUtils
import net.minecraft.client.render.TexturedRenderLayers
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack

class TrainBellAttachmentRenderer : AttachmentRenderer<TrainBellAttachment> {
    companion object {
        val TRAIN_BELL = RoadworksMain.ModId("block/train_bell")
    }

    override fun render(
        attachment: TrainBellAttachment,
        blockEntity: PostContainerBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        val thickness = blockEntity.thickness.thickness
        val buffer: VertexConsumer = vertexConsumers.getBuffer(TexturedRenderLayers.getEntityTranslucentCull())

        matrices.push()
        AttachmentRenderer.translateForCenter(matrices, attachment.facing.opposite, 0)
        matrices.translate(0.0, 0.0, -thickness / 2)
        RenderUtils.renderModel(matrices, buffer, light, overlay, TRAIN_BELL, null)
        matrices.pop()
    }
}