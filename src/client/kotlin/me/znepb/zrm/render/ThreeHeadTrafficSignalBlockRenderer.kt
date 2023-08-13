package me.znepb.zrm.render

import me.znepb.zrm.block.signals.SignalLight
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory

class ThreeHeadTrafficSignalBlockRenderer(private val ctx: BlockEntityRendererFactory.Context) :
    AbstractThreeHeadSignalBlockRenderer(
        ctx, SignalLight.RED, SignalLight.YELLOW, SignalLight.GREEN
    )