package me.znepb.roadworks.container

import me.znepb.roadworks.RoadworksMain
import me.znepb.roadworks.RoadworksRegistry
import me.znepb.roadworks.util.PostThickness
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class PostContainerItem(var settings: Settings) : BlockItem(RoadworksRegistry.ModBlocks.POST_CONTAINER, settings) {
    override fun getName(stack: ItemStack): Text {
        val nbt = getBlockEntityNbt(stack)
        val thickness = if(nbt?.contains("thickness") == true) PostThickness.fromName(nbt.getString("thickness")) else null
        return Text.translatable(if(thickness != null) "block.roadworks.post_${thickness.name.lowercase()}" else "block.roadworks.post")
    }
}