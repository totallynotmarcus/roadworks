package me.znepb.roadworks.render.attachments

import me.znepb.roadworks.RoadworksMain
import me.znepb.roadworks.container.PostContainerBlockEntity
import me.znepb.roadworks.signal.FiveHeadSignalAttachment
import me.znepb.roadworks.signal.SignalLight
import me.znepb.roadworks.util.RenderUtils
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack

class FiveHeadSignalAttachmentRenderer : AttachmentRenderer<FiveHeadSignalAttachment> {
    companion object {
        val SIGNAL_FRAME_5 = RoadworksMain.ModId("block/signal_frame_5")
    }

    override fun render(
        attachment: FiveHeadSignalAttachment,
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
        AttachmentRenderer.translateForCenter(matrices, attachment.facing.opposite, 0)
        matrices.translate(0.0, 0.0, -thickness / 2)
        RenderUtils.renderModel(matrices, renderer.buffer, light, overlay, SIGNAL_FRAME_5, null)
        matrices.pop()

        var leftYellow: SignalLight? = null
        var leftGreen: SignalLight? = null
        var rightYellow: SignalLight? = null
        var rightGreen: SignalLight? = null
        val lights = attachment.signalType.lights

        if(lights.contains(SignalLight.GREEN_LEFT) && lights.contains(SignalLight.GREEN_RIGHT)) {
            leftYellow = SignalLight.YELLOW_LEFT
            leftGreen = SignalLight.GREEN_LEFT
            rightYellow = SignalLight.YELLOW_RIGHT
            rightGreen = SignalLight.GREEN_RIGHT
        } else if(lights.contains(SignalLight.GREEN_LEFT)) {
            leftYellow = SignalLight.YELLOW_LEFT
            leftGreen = SignalLight.GREEN_LEFT
            rightYellow = SignalLight.YELLOW
            rightGreen = SignalLight.GREEN
        } else if(lights.contains(SignalLight.GREEN_RIGHT)) {
            leftYellow = SignalLight.YELLOW
            leftGreen = SignalLight.GREEN
            rightYellow = SignalLight.YELLOW_RIGHT
            rightGreen = SignalLight.GREEN_RIGHT
        }

        if(leftYellow == null || leftGreen == null || rightYellow == null || rightGreen == null) return;

        renderer.renderSignal(attachment.signalType.getReds()[0], 0.0, 0.25)
        renderer.renderSignal(leftYellow, 0.125, 0.0)
        renderer.renderSignal(leftGreen, 0.125, -0.25)
        renderer.renderSignal(rightYellow, -0.125, 0.0)
        renderer.renderSignal(rightGreen, -0.125, -0.25)
    }
}