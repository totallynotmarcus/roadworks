package me.znepb.roadworks.util

enum class MarkingSide {
    RIGHT, LEFT;

    fun opposite(): MarkingSide {
        return if(this == RIGHT) LEFT else RIGHT
    }
}