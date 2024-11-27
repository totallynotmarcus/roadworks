package me.znepb.roadworks.attachment

import me.znepb.roadworks.container.PostContainerBlockEntity
import me.znepb.roadworks.util.PostThickness
import net.minecraft.item.BlockItem.getBlockEntityNbt
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

open class AttachmentItem(var settings: Settings, val attachment: AttachmentType<out Attachment>) : Item(settings) {
    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        context.world.playSoundAtBlockCenter(context.blockPos, SoundEvent.of(Identifier("block.stone.place")), SoundCategory.BLOCKS, 1.0F, 0.75F, true)
        if(context.world.isClient) return ActionResult.PASS
        val be = context.world.getBlockEntity(context.blockPos)
        if(be !is PostContainerBlockEntity) return ActionResult.FAIL

        if(context.side == Direction.UP || context.side == Direction.DOWN) return ActionResult.FAIL
        if(be.getAttachmentsOnFace(context.side).isNotEmpty()) return ActionResult.FAIL
        if(be.getAttachmentsOnFace(context.side.rotateYClockwise()).isNotEmpty()) return ActionResult.FAIL
        if(be.getAttachmentsOnFace(context.side.rotateYCounterclockwise()).isNotEmpty()) return ActionResult.FAIL
        if(be.getDirectionThickness(context.side) != PostThickness.NONE) return ActionResult.FAIL

        val attachment = attachment.factory.create(be)
        val nbt = getBlockEntityNbt(context.stack)
        nbt?.let { attachment.readNBT(it) }
        be.addAttachment(attachment, context.side)
        return ActionResult.SUCCESS
    }
}