package me.znepb.roadworks.util

import me.znepb.roadworks.RoadworksRegistry
import me.znepb.roadworks.datagen.TagProvider
import me.znepb.roadworks.marking.OneSideFilledMarking
import me.znepb.roadworks.marking.OneSideFilledMarking.Companion.SHOULD_FILL
import net.minecraft.block.BlockState
import net.minecraft.state.property.Properties
import net.minecraft.util.math.Direction

class MarkingUtil {
    companion object {
        fun doesBorder(from: BlockState, other: BlockState): Boolean {
            if(other.isOf(RoadworksRegistry.ModBlocks.WHITE_INFILL_MARKING)) return true
            if(!from.contains(Properties.HORIZONTAL_FACING)
                || !other.contains(Properties.HORIZONTAL_FACING)) return false
            if(!from.isIn(TagProvider.MARKINGS) || !other.isIn(TagProvider.MARKINGS)) return false
            if(from.isIn(TagProvider.STANDALONE_MARKINGS) || other.isIn(TagProvider.STANDALONE_MARKINGS)) return false
            if(other.contains(SHOULD_FILL) && other.get(SHOULD_FILL) == false) return false

            val thisState = from.get(Properties.HORIZONTAL_FACING)
            val otherState = other.get(Properties.HORIZONTAL_FACING)

            return thisState == otherState || thisState == otherState.opposite
        }

        fun getAbsoluteFromRelative(state: BlockState, relative: MarkingSide): Direction {
            return when(state.get(Properties.HORIZONTAL_FACING)) {
                Direction.NORTH -> when(relative) {
                    MarkingSide.RIGHT -> Direction.EAST
                    MarkingSide.LEFT -> Direction.WEST
                }
                Direction.EAST -> when(relative) {
                    MarkingSide.RIGHT -> Direction.SOUTH
                    MarkingSide.LEFT -> Direction.NORTH
                }
                Direction.SOUTH -> when(relative) {
                    MarkingSide.RIGHT -> Direction.WEST
                    MarkingSide.LEFT -> Direction.EAST
                }
                Direction.WEST -> when(relative) {
                    MarkingSide.RIGHT -> Direction.NORTH
                    MarkingSide.LEFT -> Direction.SOUTH
                }
                else -> Direction.NORTH
            }
        }

        fun getCardinalDirectionFilled(state: BlockState, direction: Direction): Boolean {
            if(!state.contains(Properties.HORIZONTAL_FACING)) return false
            if(state.get(Properties.HORIZONTAL_FACING) == direction ||
                state.get(Properties.HORIZONTAL_FACING) == direction.opposite) return false

            return if(direction == state.get(Properties.HORIZONTAL_FACING).rotateYClockwise())
                state.get(OneSideFilledMarking.RIGHT_FILL)
            else state.get(OneSideFilledMarking.LEFT_FILL)
        }
    }
}