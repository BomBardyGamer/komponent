package me.bardy.komponent

import kotlinx.serialization.Serializable
import me.bardy.komponent.colour.Colour
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.HoverEvent
import me.bardy.komponent.serialisers.KeybindComponentSerialiser

/**
 * Represents a component that's value is a keybind that the client replaces with the key mapped
 * to that keybind
 *
 * For example, by default, the keybind "key.forward" maps to the key "W"
 *
 * @author Callum Seabrook
 *
 * More info can be found [here](https://wiki.vg/Chat#Keybind_component)
 */
@Serializable(with = KeybindComponentSerialiser::class)
class KeybindComponent(
    override val value: String,
    override val decoration: ComponentDecoration,
    override val colour: Colour? = null,
    override val insertion: String? = null,
    override val clickEvent: ClickEvent? = null,
    override val hoverEvent: HoverEvent<*>? = null,
    override val extra: List<Component>
) : Component