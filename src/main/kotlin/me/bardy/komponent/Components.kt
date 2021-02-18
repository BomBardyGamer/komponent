package me.bardy.komponent

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import me.bardy.komponent.colour.Color
import me.bardy.komponent.colour.ColourSerialiser
import me.bardy.komponent.colour.EmptyColor
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.HoverEvent

/**
 * The superinterface for all chat components
 *
 * @author Callum Seabrook
 */
@Serializable(with = Component.Companion::class)
sealed class Component {

    abstract val bold: Boolean?
    abstract val italic: Boolean?
    abstract val underlined: Boolean?
    abstract val strikethrough: Boolean?
    abstract val obfuscated: Boolean?

    abstract val color: Color

    abstract val insertion: String?

    abstract val clickEvent: ClickEvent?

    abstract val hoverEvent: HoverEvent?

    abstract val extra: List<Component>

    companion object : JsonContentPolymorphicSerializer<Component>(Component::class) {

        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out Component> = when {
            "text" in element.jsonObject -> TextComponent.serializer()
            "translate" in element.jsonObject -> TranslationComponent.serializer()
            "keybind" in element.jsonObject -> KeybindComponent.serializer()
            "score" in element.jsonObject -> ScoreComponent.serializer()
            else -> throw IllegalArgumentException("Cannot deserialise type $element! (How did we get here?)")
        }
    }
}

@Serializable
data class TextComponent internal constructor(
    val text: String,
    override val bold: Boolean? = null,
    override val italic: Boolean? = null,
    override val underlined: Boolean? = null,
    override val strikethrough: Boolean? = null,
    override val obfuscated: Boolean? = null,
    @Serializable(with = ColourSerialiser::class) override val color: Color = EmptyColor,
    override val insertion: String? = null,
    override val clickEvent: ClickEvent? = null,
    override val hoverEvent: HoverEvent? = null,
    override val extra: List<Component> = emptyList()
) : Component()

@Serializable
data class TranslationComponent internal constructor(
    @SerialName("translate") val translationKey: String,
    override val bold: Boolean? = null,
    override val italic: Boolean? = null,
    override val underlined: Boolean? = null,
    override val strikethrough: Boolean? = null,
    override val obfuscated: Boolean? = null,
    @Serializable(with = ColourSerialiser::class) override val color: Color = EmptyColor,
    override val insertion: String? = null,
    override val clickEvent: ClickEvent? = null,
    override val hoverEvent: HoverEvent? = null,
    @SerialName("with") override val extra: List<Component> = emptyList()
) : Component()

@Serializable
data class KeybindComponent internal constructor(
    val keybind: String,
    override val bold: Boolean? = null,
    override val italic: Boolean? = null,
    override val underlined: Boolean? = null,
    override val strikethrough: Boolean? = null,
    override val obfuscated: Boolean? = null,
    @Serializable(with = ColourSerialiser::class) override val color: Color = EmptyColor,
    override val insertion: String? = null,
    override val clickEvent: ClickEvent? = null,
    override val hoverEvent: HoverEvent? = null,
    override val extra: List<Component> = emptyList()
) : Component()

@Serializable
data class ScoreComponent internal constructor(
    val score: Score,
    override val bold: Boolean? = null,
    override val italic: Boolean? = null,
    override val underlined: Boolean? = null,
    override val strikethrough: Boolean? = null,
    override val obfuscated: Boolean? = null,
    @Serializable(with = ColourSerialiser::class) override val color: Color = EmptyColor,
    override val insertion: String? = null,
    override val clickEvent: ClickEvent? = null,
    override val hoverEvent: HoverEvent? = null,
    override val extra: List<Component> = emptyList()
) : Component()

@Serializable
data class Score(
    val name: String,
    val objective: String,
    val value: String
)