package me.bardy.komponent

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import me.bardy.komponent.colour.Color
import me.bardy.komponent.colour.ColourSerialiser
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.HoverEvent
import me.bardy.komponent.serialisers.componentElements
import me.bardy.komponent.serialisers.decodeComponent
import me.bardy.komponent.serialisers.encodeComponentElements

/**
 * The superinterface for all chat components
 *
 * @author Callum Seabrook
 */
abstract class Component protected constructor() {

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
            "text" in element.jsonObject -> TextComponent.Companion
            "translate" in element.jsonObject -> TranslationComponent.Companion
            "keybind" in element.jsonObject -> KeybindComponent.Companion
            "score" in element.jsonObject -> ScoreComponent.Companion
            else -> serializer() // we should never reach here
        }
    }
}

@Serializable(with = TextComponent.Companion::class)
data class TextComponent(
    @SerialName("text") val text: String,
    override val bold: Boolean?,
    override val italic: Boolean?,
    override val underlined: Boolean?,
    override val strikethrough: Boolean?,
    override val obfuscated: Boolean?,
    @Serializable(with = ColourSerialiser::class) override val color: Color,
    override val insertion: String?,
    override val clickEvent: ClickEvent?,
    override val hoverEvent: HoverEvent?,
    override val extra: List<Component>
) : Component() {

    companion object : KSerializer<TextComponent> {

        override val descriptor = buildClassSerialDescriptor("TextComponent") {
            element<String>("text")
            componentElements()
        }

        override fun serialize(encoder: Encoder, value: TextComponent) = encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.text)
            encodeComponentElements(descriptor, value)
        }

        override fun deserialize(decoder: Decoder): TextComponent = decoder.decodeStructure(descriptor) {
            val text = decodeStringElement(descriptor, 0)
            val (bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, extra) = decodeComponent(descriptor)
            TextComponent(text, bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, extra)
        }
    }
}

@Serializable(with = TranslationComponent.Companion::class)
data class TranslationComponent(
    @SerialName("translate") val translationKey: String,
    override val bold: Boolean?,
    override val italic: Boolean?,
    override val underlined: Boolean?,
    override val strikethrough: Boolean?,
    override val obfuscated: Boolean?,
    @Serializable(with = ColourSerialiser::class) override val color: Color,
    override val insertion: String?,
    override val clickEvent: ClickEvent?,
    override val hoverEvent: HoverEvent?,
    override val extra: List<Component>
) : Component() {

    companion object : KSerializer<TranslationComponent> {

        override val descriptor = buildClassSerialDescriptor("TranslationComponent") {
            element<String>("translate")
            componentElements("with")
        }

        override fun serialize(encoder: Encoder, value: TranslationComponent) = encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.translationKey)
            encodeComponentElements(descriptor, value)
        }

        override fun deserialize(decoder: Decoder): TranslationComponent = decoder.decodeStructure(descriptor) {
            val translationKey = decodeStringElement(descriptor, 0)
            val (bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, extra) = decodeComponent(descriptor)
            TranslationComponent(translationKey, bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, extra)
        }
    }
}

@Serializable(with = KeybindComponent.Companion::class)
data class KeybindComponent(
    @SerialName("keybind") val keybind: String,
    override val bold: Boolean?,
    override val italic: Boolean?,
    override val underlined: Boolean?,
    override val strikethrough: Boolean?,
    override val obfuscated: Boolean?,
    @Serializable(with = ColourSerialiser::class) override val color: Color,
    override val insertion: String?,
    override val clickEvent: ClickEvent?,
    override val hoverEvent: HoverEvent?,
    override val extra: List<Component>
) : Component() {

    companion object : KSerializer<KeybindComponent> {

        override val descriptor = buildClassSerialDescriptor("KeybindComponent") {
            element<String>("keybind")
            componentElements()
        }

        override fun serialize(encoder: Encoder, value: KeybindComponent) = encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.keybind)
            encodeComponentElements(descriptor, value)
        }

        override fun deserialize(decoder: Decoder): KeybindComponent = decoder.decodeStructure(descriptor) {
            val keybind = decodeStringElement(descriptor, 0)
            val (bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, extra) = decodeComponent(descriptor)
            KeybindComponent(keybind, bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, extra)
        }
    }
}

@Serializable(with = ScoreComponent.Companion::class)
data class ScoreComponent(
    @SerialName("score") val score: Score,
    override val bold: Boolean?,
    override val italic: Boolean?,
    override val underlined: Boolean?,
    override val strikethrough: Boolean?,
    override val obfuscated: Boolean?,
    @Serializable(with = ColourSerialiser::class) override val color: Color,
    override val insertion: String?,
    override val clickEvent: ClickEvent?,
    override val hoverEvent: HoverEvent?,
    override val extra: List<Component>
) : Component() {

    companion object : KSerializer<ScoreComponent> {

        override val descriptor = buildClassSerialDescriptor("ScoreComponent") {
            element<Score>("score")
            componentElements()
        }

        override fun serialize(encoder: Encoder, value: ScoreComponent) = encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, Score.serializer(), value.score)
            encodeComponentElements(descriptor, value)
        }

        override fun deserialize(decoder: Decoder): ScoreComponent = decoder.decodeStructure(descriptor) {
            val score = decodeSerializableElement(descriptor, 0, Score.serializer())
            val (bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, extra) = decodeComponent(descriptor)
            ScoreComponent(score, bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, extra)
        }
    }
}

@Serializable
data class Score(
    val name: String,
    val objective: String,
    val value: String
)