package me.znepb.roadworks.render.attachments

import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.RenderPhase
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.util.Identifier

object RenderLayers {
    fun getRenderLayer(texture: Identifier): RenderLayer {
        return RenderLayer.of(
            "custom_sign_layer",
            VertexFormats.POSITION_COLOR_TEXTURE_LIGHT,
            VertexFormat.DrawMode.QUADS,
            2097152,
            true,
            false,
            RenderLayer.MultiPhaseParameters.builder()
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .program(RenderPhase.CUTOUT_MIPPED_PROGRAM)
                .texture(RenderPhase.Texture(texture, false, true))
                .transparency(RenderPhase.NO_TRANSPARENCY)
                .overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
                .build(true)
        )
    }
}