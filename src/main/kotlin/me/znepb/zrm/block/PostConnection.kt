package me.znepb.zrm.block

import net.minecraft.util.StringIdentifiable

enum class PostConnection(s: String) : StringIdentifiable {
    THICK("thick"),
    MEDIUM("medium"),
    THIN("thin"),
    NONE("none");

    private var s = s;

    override fun asString(): String {
        return s
    }

    open fun isConnected(): Boolean {
        return this != NONE
    }
}