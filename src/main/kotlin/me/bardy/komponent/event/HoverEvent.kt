@file:Suppress("unused") // API functions
package me.bardy.komponent.event

import kotlinx.serialization.Serializable
import me.bardy.komponent.Component

/**
 * Represents a hover event for a [Component]
 *
 * @author Callum Seabrook
 */
@Serializable
data class HoverEvent internal constructor(
    val action: HoverAction,
    val contents: String
)

fun showText(text: String) = HoverEvent(HoverAction.SHOW_TEXT, text)

enum class HoverAction {

    SHOW_TEXT,
    SHOW_ITEM,
    SHOW_ENTITY;

    override fun toString() = name.toLowerCase()
}