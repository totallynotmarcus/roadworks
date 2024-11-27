package me.znepb.roadworks.container

import me.znepb.roadworks.RoadworksMain
import me.znepb.roadworks.RoadworksRegistry
import me.znepb.roadworks.util.PostThickness
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class PostContainerItem(var settings: Settings) : BlockItem(RoadworksRegistry.ModBlocks.POST_CONTAINER, settings) {
    override fun getName(stack: ItemStack) = Text.translatable("block.roadworks.post_${getThickness(stack).name.lowercase()}")

    companion object {
        fun getThickness(stack: ItemStack) =
            PostThickness.fromName(stack.orCreateNbt.getString("thickness").lowercase())
    }
}