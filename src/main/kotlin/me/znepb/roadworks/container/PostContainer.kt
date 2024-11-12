package me.znepb.roadworks.container

import me.znepb.roadworks.RoadworksRegistry
import me.znepb.roadworks.util.PostThickness
import me.znepb.roadworks.util.RotateVoxelShape
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.enums.Thickness
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class PostContainer(settings: Settings) : BlockWithEntity(settings), BlockEntityProvider {
    companion object {
        val BOTTOM_SHAPE_THICK = createCuboidShape(5.0, 0.0, 5.0, 11.0, 5.0, 11.0)
        val MIDSECTION_SHAPE_THICK = createCuboidShape(5.0, 5.0, 5.0, 11.0, 11.0, 11.0)
        val FOOTER_SHAPE_THICK = VoxelShapes.union(
            BOTTOM_SHAPE_THICK, createCuboidShape(3.0, 0.0, 3.0, 13.0, 3.0, 13.0)
        )

        val BOTTOM_SHAPE_MEDIUM = createCuboidShape(6.0, 0.0, 6.0, 10.0, 6.0, 10.0)
        val MIDSECTION_SHAPE_MEDIUM = createCuboidShape(6.0, 6.0, 6.0, 10.0, 10.0, 10.0)
        val FOOTER_SHAPE_MEDIUM = VoxelShapes.union(
            BOTTOM_SHAPE_MEDIUM, createCuboidShape(5.0, 0.0, 5.0, 11.0, 2.0, 11.0)
        )

        val BOTTOM_SHAPE_THIN = createCuboidShape(7.0, 0.0, 7.0, 9.0, 7.0, 9.0)
        val MIDSECTION_SHAPE_THIN = createCuboidShape(7.0, 7.0, 7.0, 9.0, 9.0, 9.0)
        val FOOTER_SHAPE_THIN = VoxelShapes.union(
            BOTTOM_SHAPE_THIN, createCuboidShape(6.0, 0.0, 6.0, 10.0, 1.0, 10.0)
        )

        fun getShapeFromDirectionAndSize(direction: Direction, otherSize: PostThickness, thisSize: PostThickness?): VoxelShape {
            if(thisSize == null) return VoxelShapes.empty()
            val shapeIndex = (otherSize.id.coerceAtMost(thisSize.id) - 1).coerceAtLeast(0)

            return when(direction) {
                Direction.DOWN -> listOf(BOTTOM_SHAPE_THIN, BOTTOM_SHAPE_MEDIUM, BOTTOM_SHAPE_THICK)[shapeIndex]
                Direction.UP -> listOf(
                    RotateVoxelShape.rotateVoxelShape(BOTTOM_SHAPE_THIN, Direction.DOWN, Direction.UP),
                    RotateVoxelShape.rotateVoxelShape(BOTTOM_SHAPE_MEDIUM, Direction.DOWN, Direction.UP),
                    RotateVoxelShape.rotateVoxelShape(BOTTOM_SHAPE_THICK, Direction.DOWN, Direction.UP)
                )[shapeIndex]
                else -> {
                    listOf(
                        RotateVoxelShape.rotateVoxelShape(BOTTOM_SHAPE_THIN, Direction.DOWN, direction),
                        RotateVoxelShape.rotateVoxelShape(BOTTOM_SHAPE_MEDIUM, Direction.DOWN, direction),
                        RotateVoxelShape.rotateVoxelShape(BOTTOM_SHAPE_THICK, Direction.DOWN, direction)
                    )[shapeIndex]
                }
            }
        }

        fun createItemStackForThickness(thickness: PostThickness): ItemStack {
            val item = ItemStack(RoadworksRegistry.ModItems.POST_CONTAINER)
            val nbt = NbtCompound()
            nbt.putString("thickness", thickness.name)
            BlockItem.setBlockEntityNbt(item, RoadworksRegistry.ModBlockEntities.CONTAINER_BLOCK_ENTITY, nbt)
            return item
        }
    }

    override fun <T : BlockEntity?> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        if (world.isClient) return null
        return checkType(type, RoadworksRegistry.ModBlockEntities.CONTAINER_BLOCK_ENTITY, PostContainerBlockEntity.Companion::onTick)
    }

    override fun isTransparent(state: BlockState, world: BlockView, pos: BlockPos): Boolean {
        return true
    }

    override fun getCollisionShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        return this.getShape(world, pos, context)
    }

    override fun getOutlineShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        return this.getShape(world, pos, context)
    }

    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        world: WorldAccess,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState? {
        (world.getBlockEntity(pos) as PostContainerBlockEntity?)?.getConnections()
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        (ctx.world.getBlockEntity(ctx.blockPos) as PostContainerBlockEntity?)?.getPlacementState(ctx)
        return super.getPlacementState(ctx)
    }

    override fun getPickStack(world: BlockView, pos: BlockPos, state: BlockState): ItemStack {
        val be = world.getBlockEntity(pos)
        if(be == null || be !is PostContainerBlockEntity) return ItemStack.EMPTY

        return createItemStackForThickness(be.thickness)
    }

    private fun pickSideShape(blockEntity: PostContainerBlockEntity, connectingSize: PostThickness, direction: Direction): VoxelShape {
        return when(connectingSize) {
            PostThickness.THICK -> {
                when(blockEntity.thickness) {
                    PostThickness.THICK -> getShapeFromDirectionAndSize(direction, connectingSize, blockEntity.thickness)
                    PostThickness.MEDIUM -> getShapeFromDirectionAndSize(direction, PostThickness.MEDIUM, blockEntity.thickness)
                    PostThickness.THIN -> getShapeFromDirectionAndSize(direction, PostThickness.THIN, blockEntity.thickness)
                    else -> VoxelShapes.empty()
                }
            }
            PostThickness.MEDIUM ->
                getShapeFromDirectionAndSize(
                    direction,
                    if (blockEntity.thickness == PostThickness.THICK) PostThickness.MEDIUM else connectingSize,
                    blockEntity.thickness
                )
            PostThickness.THIN -> getShapeFromDirectionAndSize(direction, PostThickness.THIN, blockEntity.thickness)
            else -> VoxelShapes.empty()
        }
    }

    private fun getMidsectionShape(blockEntity: PostContainerBlockEntity): VoxelShape {
        return when(blockEntity.thickness) {
            PostThickness.THICK -> MIDSECTION_SHAPE_THICK
            PostThickness.MEDIUM -> MIDSECTION_SHAPE_MEDIUM
            PostThickness.THIN -> MIDSECTION_SHAPE_THIN
            else -> VoxelShapes.empty()
        }
    }

    private fun getFooterShape(blockEntity: PostContainerBlockEntity): VoxelShape {
        return when(blockEntity.thickness) {
            PostThickness.THICK -> FOOTER_SHAPE_THICK
            PostThickness.MEDIUM -> FOOTER_SHAPE_MEDIUM
            PostThickness.THIN -> FOOTER_SHAPE_THIN
            else -> VoxelShapes.empty()
        }
    }

    fun getPostShape(world: BlockView, pos: BlockPos, shapeContext: ShapeContext): VoxelShape {
        if (world.getBlockEntity(pos) !is PostContainerBlockEntity)
            return VoxelShapes.empty()

        val blockEntity = world.getBlockEntity(pos) as PostContainerBlockEntity

        var shape = this.getMidsectionShape(blockEntity)
        if (blockEntity.footer) shape = VoxelShapes.union(shape, this.getFooterShape(blockEntity))

        Direction.entries.forEach {
            if (it != Direction.DOWN || !blockEntity.footer) {
                shape = VoxelShapes.union(shape, pickSideShape(blockEntity, blockEntity.getDirectionThickness(it), it))
            }
        }

        return shape
    }

    fun getShape(world: BlockView, pos: BlockPos, shapeContext: ShapeContext): VoxelShape {
        if (world.getBlockEntity(pos) !is PostContainerBlockEntity)
            return MIDSECTION_SHAPE_MEDIUM

        val blockEntity = world.getBlockEntity(pos) as PostContainerBlockEntity

        var shape = getPostShape(world, pos, shapeContext)

        blockEntity.attachments.forEach {
            val attachmentShape = it.getShape(shapeContext)
            shape = VoxelShapes.union(shape, attachmentShape)
        }

        return shape
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return PostContainerBlockEntity(pos, state)
    }

    override fun spawnBreakParticles(world: World?, player: PlayerEntity?, pos: BlockPos?, state: BlockState?) {
        super.spawnBreakParticles(world, player, pos, state)
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        val be = world.getBlockEntity(pos)
        if(be is PostContainerBlockEntity) be.remove()
        super.onBreak(world, pos, state, player)
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        val be = world.getBlockEntity(pos)
        if(be is PostContainerBlockEntity) {
            val attachment = be.getAttachmentHit(hit)
            return attachment?.onUse(player, hand, hit) ?: ActionResult.PASS
        }
        return ActionResult.PASS
    }
}