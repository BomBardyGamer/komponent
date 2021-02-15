package me.bardy.komponent

import kotlinx.serialization.Serializable
import me.bardy.komponent.colour.Color
import me.bardy.komponent.dsl.DSLBuilder
import me.bardy.komponent.dsl.FormatBuilder
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.HoverEvent

/**
 * The superinterface for all chat components
 *
 * @author Callum Seabrook
 */
interface Component {

    val value: String

    val decoration: ComponentDecoration

    val colour: Color?

    val insertion: String?

    val clickEvent: ClickEvent?

    val hoverEvent: HoverEvent<*>?

    val extra: List<Component>

    interface Builder {

        val color: Color?

        val insertion: String?

        val clickEvent: ClickEvent?

        val hoverEvent: HoverEvent<String>?

        fun formatting(builder: FormatBuilder.() -> Unit): FormatBuilder

        fun children(builder: DSLBuilder.() -> Unit): Component

        fun build(): Component
    }
}

// we use null here to distinguish it from explicit specification (saying you want or don't want it) and
// us inferring that we should just ignore it
@Serializable
data class ComponentDecoration(
    val bold: Boolean? = null,
    val italic: Boolean? = null,
    val underlined: Boolean? = null,
    val strikethrough: Boolean? = null,
    val obfuscated: Boolean? = null
)