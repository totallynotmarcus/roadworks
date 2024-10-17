package me.znepb.roadworks.block.post

import me.znepb.roadworks.Registry
import me.znepb.roadworks.RoadworksMain.logger
import me.znepb.roadworks.util.PostThickness
import me.znepb.roadworks.util.RotateVoxelShape.Companion.rotateVoxelShape
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.ItemPlacementContext
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

open class PostBlock(
    settings: Settings,
    private val size: PostThickness) : BlockWithEntity(settings), BlockEntityProvider
{
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

        fun getShapeFromDirectionAndSize(direction: Direction, otherSize: PostThickness, thisSize: PostThickness): VoxelShape {
            val shapeIndex = (otherSize.id.coerceAtMost(thisSize.id) - 1).coerceAtLeast(0)

            return when(direction) {
                Direction.DOWN -> listOf(BOTTOM_SHAPE_THIN, BOTTOM_SHAPE_MEDIUM, BOTTOM_SHAPE_THICK)[shapeIndex]
                Direction.UP -> listOf(
                    rotateVoxelShape(BOTTOM_SHAPE_THIN, Direction.DOWN, Direction.UP),
                    rotateVoxelShape(BOTTOM_SHAPE_MEDIUM, Direction.DOWN, Direction.UP),
                    rotateVoxelShape(BOTTOM_SHAPE_THICK, Direction.DOWN, Direction.UP)
                )[shapeIndex]
                else -> {
                    listOf(
                        rotateVoxelShape(BOTTOM_SHAPE_THIN, Direction.DOWN, direction),
                        rotateVoxelShape(BOTTOM_SHAPE_MEDIUM, Direction.DOWN, direction),
                        rotateVoxelShape(BOTTOM_SHAPE_THICK, Direction.DOWN, direction)
                    )[shapeIndex]
                }
            }
        }
    }

    override fun <T : BlockEntity?> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        if (world.isClient) return null
        return checkType(type, Registry.ModBlockEntities.POST_BLOCK_ENTITY, PostBlockEntity.Companion::onTick)
    }

    override fun isTransparent(state: BlockState?, world: BlockView?, pos: BlockPos?): Boolean {
        return true
    }

    override fun getCollisionShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        return this.getShape(world, pos)
    }

    override fun getOutlineShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        return this.getShape(world, pos)
    }

    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        world: WorldAccess,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState? {
        (world.getBlockEntity(pos) as PostBlockEntity?)?.getPlacementState(pos)
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        (ctx.world.getBlockEntity(ctx.blockPos) as PostBlockEntity?)?.getPlacementState(ctx.blockPos)
        return super.getPlacementState(ctx)
    }

    private fun pickSideShape(connectingSize: PostThickness, direction: Direction): VoxelShape {
        return when(connectingSize) {
            PostThickness.THICK -> {
                when(size) {
                    PostThickness.THICK -> getShapeFromDirectionAndSize(direction, connectingSize, size)
                    PostThickness.MEDIUM -> getShapeFromDirectionAndSize(direction, PostThickness.MEDIUM, size)
                    PostThickness.THIN -> getShapeFromDirectionAndSize(direction, PostThickness.THIN, size)
                    else -> VoxelShapes.empty()
                }
            }
            PostThickness.MEDIUM ->
                getShapeFromDirectionAndSize(
                    direction,
                    if(size == PostThickness.THICK) PostThickness.MEDIUM else connectingSize,
                    size
                )
            PostThickness.THIN -> getShapeFromDirectionAndSize(direction, PostThickness.THIN, size)
            else -> VoxelShapes.empty()
        }
    }

    private fun getMidsectionShape(): VoxelShape {
        return when(size) {
            PostThickness.THICK -> MIDSECTION_SHAPE_THICK
            PostThickness.MEDIUM -> MIDSECTION_SHAPE_MEDIUM
            PostThickness.THIN -> MIDSECTION_SHAPE_THIN
            else -> VoxelShapes.empty()
        }
    }

    private fun getFooterShape(): VoxelShape {
        return when(size) {
            PostThickness.THICK -> FOOTER_SHAPE_THICK
            PostThickness.MEDIUM -> FOOTER_SHAPE_MEDIUM
            PostThickness.THIN -> FOOTER_SHAPE_THIN
            else -> VoxelShapes.empty()
        }
    }

    private fun getShape(world: BlockView, pos: BlockPos): VoxelShape {
        if(world.getBlockEntity(pos) !is PostBlockEntity)
            return VoxelShapes.empty()

        val blockEntity = world.getBlockEntity(pos) as PostBlockEntity

        var shape = this.getMidsectionShape()
        if (blockEntity.footer) shape = VoxelShapes.union(shape, this.getFooterShape())

        Direction.entries.forEach {
            if (it != Direction.DOWN || !blockEntity.footer) {
                shape = VoxelShapes.union(shape, pickSideShape(blockEntity.getDirectionThickness(it), it))
            }
        }

        return shape
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return PostBlockEntity(pos, state)
    }
}
