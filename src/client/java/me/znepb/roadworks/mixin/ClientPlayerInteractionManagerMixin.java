package me.znepb.roadworks.mixin;

import me.znepb.roadworks.container.PostContainer;
import me.znepb.roadworks.container.PostContainerBlockEntity;
import me.znepb.roadworks.network.DestroyAttachmentPacket;
import me.znepb.roadworks.network.DestroyAttachmentPacketClient;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Shadow @Final private MinecraftClient client;

    @Shadow private float currentBreakingProgress;

    @Shadow private int blockBreakingCooldown;

    @Unique
    private void check(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        assert this.client.world != null;
        var be = this.client.world.getBlockEntity(pos);
        var blockState = this.client.world.getBlockState(pos);
        if(be instanceof PostContainerBlockEntity && blockState.getBlock() instanceof PostContainer block) {
            var hit = this.client.crosshairTarget;

            double x = pos.getX();
            double y = pos.getY();
            double z = pos.getZ();

            assert hit != null;
            if(hit.getType() == HitResult.Type.BLOCK) {
                var relativeHitPos = hit.getPos().subtract(x, y, z);
                var hitAttachment = ((PostContainerBlockEntity) be).getAttachmentHit(relativeHitPos, ShapeContext.absent());

                if(hitAttachment != null) {
                    this.currentBreakingProgress = 0;
                    this.blockBreakingCooldown = 5;
                    DestroyAttachmentPacketClient.Companion.sendDestroyAttachmentPacket(
                            new DestroyAttachmentPacket(pos, this.client.world.getRegistryKey(), hitAttachment.getId())
                    );
                    this.client.world.playSoundAtBlockCenter(pos, SoundEvent.of(new Identifier("block.stone.break")), SoundCategory.BLOCKS, 1.0F, 0.75F, true);
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            }
        }
    }

    @Inject(cancellable = true, method="Lnet/minecraft/client/network/ClientPlayerInteractionManager;attackBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z", at=@At("HEAD"))
    public void attackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        check(pos, direction, cir);
    }

    @Inject(cancellable = true, method="Lnet/minecraft/client/network/ClientPlayerInteractionManager;updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z", at=@At("HEAD"))
    public void updateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (this.blockBreakingCooldown > 0) {
            --this.blockBreakingCooldown;
            cir.setReturnValue(true);
            cir.cancel();
            return;
        }
        check(pos, direction, cir);
    }
}
