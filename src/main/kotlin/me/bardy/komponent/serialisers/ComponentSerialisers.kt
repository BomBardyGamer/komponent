package me.bardy.komponent.serialisers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import kotlinx.serialization.serializer
import me.bardy.komponent.*
import me.bardy.komponent.colour.Colour
import me.bardy.komponent.colour.ColourSerialiser
import me.bardy.komponent.colour.NullableColourSerialiser
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.HoverEvent

/**
 * Serialisers for [Component]s
 *
 * @author Callum Seabrook
 */
sealed class ComponentSerialiser<T : Component>(componentName: String, private val keyName: String, private val extraName: String = "extra") : SerializationStrategy<T> {

    override val descriptor = buildClassSerialDescriptor(componentName) {
        element<String>(keyName)
        element<Boolean?>("bold", isOptional = true)
        element<Boolean?>("italic", isOptional = true)
        element<Boolean?>("underlined", isOptional = true)
        element<Boolean?>("strikethrough", isOptional = true)
        element<Boolean?>("obfuscated", isOptional = true)
        element<Colour?>("color", isOptional = true)
        element<String?>("insertion", isOptional = true)

        element<ClickEvent?>("clickEvent", isOptional = true)
        element<HoverEvent<String>?>("hoverEvent", isOptional = true)
        element<List<Component>>(extraName, isOptional = true)
    }

    override fun serialize(encoder: Encoder, value: T) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.value)

            val decoration = value.decoration
            if (decoration.bold != null) encodeBooleanElement(descriptor, 1, decoration.bold)
            if (decoration.italic != null) encodeBooleanElement(descriptor, 2, decoration.italic)
            if (decoration.underlined != null) encodeBooleanElement(descriptor, 3, decoration.underlined)
            if (decoration.strikethrough != null) encodeBooleanElement(descriptor, 4, decoration.strikethrough)
            if (decoration.obfuscated != null) encodeBooleanElement(descriptor, 5, decoration.obfuscated)

            val colour = value.colour
            if (colour != null) encodeSerializableElement(descriptor, 6, ColourSerialiser, colour)

            val insertion = value.insertion
            if (insertion != null) encodeStringElement(descriptor, 7, insertion)

            val clickEvent = value.clickEvent
            if (clickEvent != null) encodeSerializableElement(descriptor, 8, serializer(), clickEvent)

            val hoverEvent = value.hoverEvent
            if (hoverEvent != null) encodeSerializableElement(descriptor, 9, serializer(), hoverEvent)

            val extra = value.extra
            if (extra.isNotEmpty()) encodeSerializableElement(descriptor, 10, serializer(), extra)
        }
    }
}

object TextComponentSerialiser : ComponentSerialiser<TextComponent>("TextComponent", "text"), KSerializer<TextComponent> {

    override fun deserialize(decoder: Decoder): TextComponent = decoder.decodeStructure(descriptor) {
        val (text, decoration, colour, insertion, clickEvent, hoverEvent, extra) = decodeComponentElement(descriptor)
        TextComponent(text, decoration, colour, insertion, clickEvent, hoverEvent, extra)
    }
}

object TranslationComponentSerialiser : ComponentSerialiser<TranslationComponent>("TranslationComponent", "translation", "with"), KSerializer<TranslationComponent> {

    override fun deserialize(decoder: Decoder): TranslationComponent = decoder.decodeStructure(descriptor) {
        val (translationKey, decoration, colour, insertion, clickEvent, hoverEvent, with) = decodeComponentElement(descriptor)
        TranslationComponent(translationKey, decoration, colour, insertion, clickEvent, hoverEvent, with)
    }
}

object KeybindComponentSerialiser : ComponentSerialiser<KeybindComponent>("KeybindComponent", "keybind"), KSerializer<KeybindComponent> {

    override fun deserialize(decoder: Decoder): KeybindComponent = decoder.decodeStructure(descriptor) {
        val (keybind, decoration, colour, insertion, clickEvent, hoverEvent, extra) = decodeComponentElement(descriptor)
        KeybindComponent(keybind, decoration, colour, insertion, clickEvent, hoverEvent, extra)
    }
}

private fun CompositeDecoder.decodeComponentElement(descriptor: SerialDescriptor) = ComponentDataWrapper(
    decodeStringElement(descriptor, 0),
    ComponentDecoration(
        decodeNullableBooleanElement(descriptor, 1),
        decodeNullableBooleanElement(descriptor, 2),
        decodeNullableBooleanElement(descriptor, 3),
        decodeNullableBooleanElement(descriptor, 4),
        decodeNullableBooleanElement(descriptor, 5)
    ),
    decodeNullableSerializableElement(descriptor, 6, NullableColourSerialiser),
    decodeNullableStringElement(descriptor, 7),
    decodeNullableSerializableElement(descriptor, 8, serializer()),
    decodeNullableSerializableElement(descriptor, 9, serializer()),
    decodeNullableSerializableElement(descriptor, 10, serializer()) ?: emptyList()
)

private fun CompositeDecoder.decodeNullableBooleanElement(descriptor: SerialDescriptor, index: Int): Boolean? {
    return if (decodeElementIndex(descriptor) == index) decodeBooleanElement(descriptor, index) else null
}

private fun CompositeDecoder.decodeNullableStringElement(descriptor: SerialDescriptor, index: Int): String? {
    return if (decodeElementIndex(descriptor) == index) decodeStringElement(descriptor, index) else null
}

private data class ComponentDataWrapper(
    val content: String,
    val decoration: ComponentDecoration,
    val colour: Colour?,
    val insertion: String?,
    val clickEvent: ClickEvent?,
    val hoverEvent: HoverEvent<String>?,
    val extra: List<Component>
)