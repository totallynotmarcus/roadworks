package me.znepb.roadworks.item

import me.znepb.roadworks.RoadworksMain.NAMESPACE
import me.znepb.roadworks.container.PostContainerBlockEntity
import me.znepb.roadworks.sign.RoadSignAttachment
import me.znepb.roadworks.item.SignEditorScreenHandler.Companion.sendDataToClient
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.screen.SimpleNamedScreenHandlerFactory
import net.minecraft.text.Text
import net.minecraft.util.ActionResult

class SignEditor(settings: Settings) : Item(settings) {
    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        if(context.world.isClient) return ActionResult.CONSUME
        val be = context.world.getBlockEntity(context.blockPos)
        return if(be != null && be is PostContainerBlockEntity) {
            val attachment = context.player?.let { be.getPlayerAttachmentLookingAt(it) }
            return if(attachment != null && attachment is RoadSignAttachment) {
                context.player?.openHandledScreen(SimpleNamedScreenHandlerFactory({ syncId, inventory, _ ->
                    val screenHandler = SignEditorScreenHandler(syncId, inventory)
                    screenHandler
                }, Text.translatable("gui.${NAMESPACE}.sign_editor.name")))
                val player = context.player?.server?.playerManager?.getPlayer(context.player?.uuid)
                if (player != null) {
                    sendDataToClient(player, SignEditorScreenHandler.SyncData(context.blockPos, attachment.id))
                }
                ActionResult.SUCCESS
            } else { ActionResult.CONSUME }
        } else {
            ActionResult.FAIL
        }
    }
}