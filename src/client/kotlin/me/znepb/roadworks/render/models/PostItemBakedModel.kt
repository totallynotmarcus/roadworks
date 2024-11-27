package me.znepb.roadworks.render.models

import me.znepb.roadworks.RoadworksMain.ModId
import me.znepb.roadworks.render.PostContainerRenderer.Companion.POST_MEDIUM_FOOTER_MODEL
import me.znepb.roadworks.render.PostContainerRenderer.Companion.POST_MEDIUM_MID_MODEL
import me.znepb.roadworks.render.PostContainerRenderer.Companion.POST_THICK_FOOTER_MODEL
import me.znepb.roadworks.render.PostContainerRenderer.Companion.POST_THICK_MID_MODEL
import me.znepb.roadworks.render.PostContainerRenderer.Companion.POST_THIN_FOOTER_MODEL
import me.znepb.roadworks.render.PostContainerRenderer.Companion.POST_THIN_MID_MODEL
import me.znepb.roadworks.util.PostThickness
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext
import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.model.BakedModel
import net.minecraft.client.render.model.BakedQuad
import net.minecraft.client.render.model.json.ModelOverrideList
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.texture.Sprite
import net.minecraft.item.BlockItem.getBlockEntityNbt
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.BlockRenderView
import java.util.function.Supplier

class PostItemBakedModel(val sprite: Sprite) : BakedModel, FabricBakedModel {
    private val minecraft by lazy {MinecraftClient.getInstance()}

    companion object {
        val ITEM_THIN =ModId("item/thin_post")
        val ITEM_MEDIUM = ModId("item/medium_post")
        val ITEM_THICK = ModId("item/thick_post")
    }

    private fun modelFromThickness(thickness: PostThickness): Identifier? {
        return when(thickness) {
            PostThickness.THIN -> ITEM_THIN
            PostThickness.MEDIUM -> ITEM_MEDIUM
            PostThickness.THICK -> ITEM_THICK
            else -> null
        }
    }

    override fun isVanillaAdapter() = false

    override fun emitBlockQuads(
        blockView: BlockRenderView,
        state: BlockState,
        pos: BlockPos,
        randomSupplier: Supplier<Random>,
        context: RenderContext?
    ) {}

    override fun emitItemQuads(stack: ItemStack, randomSupplier: Supplier<Random>?, context: RenderContext) {
        val thicknessString = stack.orCreateNbt.getString("thickness")
        val thickness = thicknessString?.let { PostThickness.fromName(it) }

        val modelIdentifier = thickness?.let { modelFromThickness(it) }
        val model = minecraft.bakedModelManager.getModel(modelIdentifier)

        if(model != null) {
            model.emitItemQuads(stack, randomSupplier, context)
        } else {
            minecraft.bakedModelManager.missingModel.emitItemQuads(stack, randomSupplier, context)
        }
    }

    override fun getQuads(state: BlockState?, face: Direction?, random: Random?): MutableList<BakedQuad> = mutableListOf()

    override fun useAmbientOcclusion() = true
    override fun hasDepth() = false
    override fun isSideLit() = false
    override fun isBuiltin() = false
    override fun getParticleSprite() = sprite
    override fun getTransformation(): ModelTransformation = ModelHelper.MODEL_TRANSFORM_BLOCK
    override fun getOverrides(): ModelOverrideList = ModelOverrideList.EMPTY
}