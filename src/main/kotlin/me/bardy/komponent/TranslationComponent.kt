package me.bardy.komponent

import kotlinx.serialization.Serializable
import me.bardy.komponent.colour.Color
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.HoverEvent
import me.bardy.komponent.serialisers.TranslationComponentSerialiser

/**
 * Represents a component that's value is a translation key that the client replaces with their
 * corresponding locale message
 *
 * @author Callum Seabrook
 *
 * More info can be found [here](https://wiki.vg/Chat#Translation_component)
 */
@Serializable(with = TranslationComponentSerialiser::class)
data class TranslationComponent(
    override val value: String,
    override val decoration: ComponentDecoration,
    override val colour: Color? = null,
    override val insertion: String? = null,
    override val clickEvent: ClickEvent? = null,
    override val hoverEvent: HoverEvent<*>? = null,
    override val extra: List<Component>
) : Component