package me.bardy.komponent.event

import kotlinx.serialization.Serializable

// W.I.P
@Serializable
class HoverEvent<T> private constructor(
    val action: HoverAction,
    val content: T
) {

    companion object {

        fun showText(text: String) = HoverEvent(HoverAction.SHOW_TEXT, text)
    }
}

enum class HoverAction {

    SHOW_TEXT,
    SHOW_ITEM,
    SHOW_ENTITY;

    override fun toString() = name.toLowerCase()
}