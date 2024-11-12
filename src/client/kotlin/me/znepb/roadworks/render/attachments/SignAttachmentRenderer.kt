package me.znepb.roadworks.render.attachments

import me.znepb.roadworks.container.PostContainerBlockEntity
import me.znepb.roadworks.sign.SignAttachment
import me.znepb.roadworks.util.RenderUtils
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier

class SignAttachmentRenderer : AttachmentRenderer<SignAttachment> {
    private fun getSignFrontTexture(attachment: SignAttachment): Identifier {
        val tex = attachment.getSignData()?.frontTexture ?: return Identifier("missing")
        return Identifier(tex.namespace, "textures/" + tex.path + ".png")
    }

    private fun getSignBackTexture(attachment: SignAttachment): Identifier {
        val tex = attachment.getSignData()?.backTexture ?: return Identifier("missing")
        return Identifier(tex.namespace, "textures/" + tex.path + ".png")
    }

    override fun render(
        attachment: SignAttachment,
        blockEntity: PostContainerBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        val frontTexture = getSignFrontTexture(attachment)
        val backTexture = getSignBackTexture(attachment)

        matrices.push()
        AttachmentRenderer.translateForCenter(matrices, attachment.facing)
        matrices.translate(0.0, 0.0, attachment.container.thickness.thickness / 2)

        // Render sign front
        val frontBuffer: VertexConsumer = vertexConsumers.getBuffer(RenderLayers.getRenderLayer(frontTexture))
        val frontMatrix = matrices.peek().positionMatrix
        RenderUtils.drawSquare(
            0F, 0F, 0.501F, 0F, 0F, 64F, 64F,
            64, 64, 64F, 64F,
            64, 64, frontBuffer, frontMatrix, light, overlay
        )
        matrices.pop()

        matrices.push()
        AttachmentRenderer.translateForCenter(matrices, attachment.facing.opposite)
        matrices.translate(0.0, 0.0, -attachment.container.thickness.thickness / 2)

        // Render sign back
        val backBuffer: VertexConsumer = vertexConsumers.getBuffer(RenderLayers.getRenderLayer(backTexture))
        val backMatrix = matrices.peek().positionMatrix
        RenderUtils.drawSquare(
            0F, 0F, 0.501F, 0F, 0F, 64F, 64F,
            64, 64, 64F, 64F,
            64, 64, backBuffer, backMatrix, light, overlay
        )
        matrices.pop()
    }
}