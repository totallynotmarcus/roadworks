package me.znepb.roadworks.attachment

enum class AttachmentPosition(val position: String) {
    TOP("top"), MIDDLE("middle"), BOTTOM("bottom");

    fun getName() = position

    fun next(): AttachmentPosition {
        return when(this) {
            TOP -> MIDDLE
            MIDDLE -> BOTTOM
            BOTTOM -> TOP
        }
    }

    fun previous(): AttachmentPosition {
        return when(this) {
            TOP -> BOTTOM
            MIDDLE -> TOP
            BOTTOM -> MIDDLE
        }
    }

    companion object {
        fun fromName(str: String): AttachmentPosition? {
            return when(str) {
                "top" -> TOP
                "middle" -> MIDDLE
                "bottom" -> BOTTOM
                else -> null
            }
        }
    }
}