package me.bardy.komponent

import kotlinx.serialization.Serializable
import me.bardy.komponent.colour.Colour
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.HoverEvent
import me.bardy.komponent.serialisers.TextComponentSerialiser

/**
 * Represents a component that's value is sent to the client as plain text
 *
 * @author Callum Seabrook
 *
 * More info can be found [here](https://wiki.vg/Chat#String_component)
 */
@Serializable(with = TextComponentSerialiser::class)
data class TextComponent internal constructor(
    override val value: String,
    override val decoration: ComponentDecoration,
    override val colour: Colour? = null,
    override val insertion: String? = null,
    override val clickEvent: ClickEvent? = null,
    override val hoverEvent: HoverEvent<String>? = null,
    override val extra: List<Component>
) : Component