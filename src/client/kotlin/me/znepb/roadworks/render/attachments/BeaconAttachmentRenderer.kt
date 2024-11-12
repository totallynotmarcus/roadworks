package me.znepb.roadworks.render.attachments

import me.znepb.roadworks.RoadworksMain
import me.znepb.roadworks.container.PostContainerBlockEntity
import me.znepb.roadworks.signal.BeaconAttachment
import me.znepb.roadworks.util.RenderUtils
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack

class BeaconAttachmentRenderer : AttachmentRenderer<BeaconAttachment> {
    companion object {
        val SIGNAL_FRAME_1 = RoadworksMain.ModId("block/signal_frame_1")
    }

    override fun render(
        attachment: BeaconAttachment,
        blockEntity: PostContainerBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        val thickness = blockEntity.thickness.thickness
        val renderer = SignalRenderer(attachment, matrices, vertexConsumers, light, overlay)

        matrices.push()
        AttachmentRenderer.translateForCenter(matrices, attachment.facing.opposite)
        matrices.translate(0.0, 0.0, -thickness / 2)
        RenderUtils.renderModel(matrices, renderer.buffer, light, overlay, SIGNAL_FRAME_1, null)
        matrices.pop()

        renderer.renderSignal(attachment.signalType.lights[0], 0.0, 0.0)
    }
}