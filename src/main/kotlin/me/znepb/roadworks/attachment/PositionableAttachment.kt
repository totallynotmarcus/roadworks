package me.znepb.roadworks.attachment

import me.znepb.roadworks.RoadworksRegistry
import me.znepb.roadworks.container.PostContainerBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult

abstract class PositionableAttachment(
    type: AttachmentType<*>,
    container: PostContainerBlockEntity,
) : Attachment(type, container) {
    var position = AttachmentPosition.MIDDLE

    override fun writeNBT(nbt: NbtCompound) {
        nbt.putString("position", position.getName())
        super.writeNBT(nbt)
    }

    override fun readNBT(nbt: NbtCompound) {
        super.readNBT(nbt)

        this.position = if(!this.container.isVertical())
            AttachmentPosition.MIDDLE
        else
            AttachmentPosition.fromName(nbt.getString("position") ?: "middle") ?: AttachmentPosition.MIDDLE
    }

    abstract fun isPositionable(): Boolean

    override fun onUse(player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        if(!isPositionable()) return ActionResult.PASS
        if(!this.container.isVertical()) return ActionResult.PASS

        if(player.isHolding(RoadworksRegistry.ModItems.WRENCH)) {
            position = if(player.isSneaking) position.previous() else position.next()
            return ActionResult.SUCCESS
        }

        return super.onUse(player, hand, hit)
    }
}