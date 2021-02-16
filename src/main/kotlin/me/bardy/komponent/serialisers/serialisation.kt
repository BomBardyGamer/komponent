package me.bardy.komponent.serialisers

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.CompositeEncoder
import me.bardy.komponent.Component
import me.bardy.komponent.colour.Color
import me.bardy.komponent.colour.ColourSerialiser
import me.bardy.komponent.colour.EmptyColor
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.HoverEvent

fun ClassSerialDescriptorBuilder.componentElements(extraName: String = "extra") {
    element<Boolean>("bold", isOptional = true)
    element<Boolean>("italic", isOptional = true)
    element<Boolean>("underlined", isOptional = true)
    element<Boolean>("strikethrough", isOptional = true)
    element<Boolean>("obfuscated", isOptional = true)
    element("color", PrimitiveSerialDescriptor("Color", PrimitiveKind.INT), isOptional = true)
    element<String>("insertion", isOptional = true)
    element<ClickEvent>("clickEvent", isOptional = true)
    element<HoverEvent>("hoverEvent", isOptional = true)
    element(extraName, Component.descriptor, isOptional = true)
}

fun CompositeEncoder.encodeComponentElements(descriptor: SerialDescriptor, component: Component) {
    if (component.bold != null) encodeBooleanElement(descriptor, 1, requireNotNull(component.bold))
    if (component.italic != null) encodeBooleanElement(descriptor, 2, requireNotNull(component.italic))
    if (component.underlined != null) encodeBooleanElement(descriptor, 3, requireNotNull(component.underlined))
    if (component.strikethrough != null) encodeBooleanElement(descriptor, 4, requireNotNull(component.strikethrough))
    if (component.obfuscated != null) encodeBooleanElement(descriptor, 5, requireNotNull(component.obfuscated))
    if (component.color != EmptyColor) encodeSerializableElement(descriptor, 6, ColourSerialiser, component.color)
    if (component.insertion != null) encodeStringElement(descriptor, 7, requireNotNull(component.insertion))
    if (component.clickEvent != null) encodeSerializableElement(descriptor, 8, ClickEvent.serializer(), requireNotNull(component.clickEvent))
    if (component.hoverEvent != null) encodeSerializableElement(descriptor, 9, HoverEvent.serializer(), requireNotNull(component.hoverEvent))
    if (component.extra.isNotEmpty()) encodeSerializableElement(descriptor, 10, ListSerializer(Component.Companion), component.extra)
}

fun CompositeDecoder.decodeComponent(descriptor: SerialDescriptor) = ComponentData(
    decodeNullableBoolean(descriptor, 1),
    decodeNullableBoolean(descriptor, 2),
    decodeNullableBoolean(descriptor, 3),
    decodeNullableBoolean(descriptor, 4),
    decodeNullableBoolean(descriptor, 5),
    decodeColor(descriptor, 6),
    decodeNullableString(descriptor, 7),
    decodeClickEvent(descriptor, 8),
    decodeHoverEvent(descriptor, 9),
    decodeComponentList(descriptor, 10)
)

fun CompositeDecoder.decodeNullableBoolean(descriptor: SerialDescriptor, index: Int) =
    if (decodeElementIndex(descriptor) == index) decodeBooleanElement(descriptor, index) else null

fun CompositeDecoder.decodeNullableString(descriptor: SerialDescriptor, index: Int) =
    if (decodeElementIndex(descriptor) == index) decodeStringElement(descriptor, index) else null

fun CompositeDecoder.decodeColor(descriptor: SerialDescriptor, index: Int) =
    if (decodeElementIndex(descriptor) == index) decodeSerializableElement(descriptor, index, ColourSerialiser) else EmptyColor

fun CompositeDecoder.decodeClickEvent(descriptor: SerialDescriptor, index: Int) =
    if (decodeElementIndex(descriptor) == index) decodeSerializableElement(descriptor, index, ClickEvent.serializer()) else null

fun CompositeDecoder.decodeHoverEvent(descriptor: SerialDescriptor, index: Int) =
    if (decodeElementIndex(descriptor) == index) decodeSerializableElement(descriptor, index, HoverEvent.serializer()) else null

fun CompositeDecoder.decodeComponentList(descriptor: SerialDescriptor, index: Int) =
    if (decodeElementIndex(descriptor) == index) decodeSerializableElement(descriptor, index, ListSerializer(Component.Companion)) else emptyList()

// An object primarily for use in component serialisers, for easy destructuring
data class ComponentData internal constructor(
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
) : Component()