package me.znepb.roadworks.mixin;

import me.znepb.roadworks.container.PostContainerBlockEntity;
import me.znepb.roadworks.render.PostContainerRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow
    @Nullable
    private ClientWorld world;

    @Inject(method= "drawBlockOutline(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/entity/Entity;DDDLnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at=@At("HEAD"), cancellable = true)
    private void drawBlockOutline(MatrixStack matrices, VertexConsumer vertexConsumer, Entity entity, double cameraX, double cameraY, double cameraZ, BlockPos pos, BlockState state, CallbackInfo ci) {
        var world = entity.getWorld();
        var be = world.getBlockEntity(pos);

        if(be instanceof PostContainerBlockEntity) {
            BlockEntityRenderer<BlockEntity> renderer = MinecraftClient.getInstance().getBlockEntityRenderDispatcher().get(be);

            assert renderer != null;

            if((Object)renderer instanceof PostContainerRenderer containerRenderer) {
                assert this.world != null;
                containerRenderer.renderOutline((PostContainerBlockEntity) be, matrices, vertexConsumer, entity,  this.world, cameraX, cameraY, cameraZ, pos, state);
                ci.cancel();
            }
        }
    }
}
