@file:Suppress("unused") // API functions
package me.bardy.komponent.event

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import me.bardy.komponent.Component
import me.bardy.komponent.dsl.ComponentDSL
import java.util.UUID

/**
 * Represents a hover event for a [Component]
 *
 * @author Callum Seabrook
 */
@Serializable
data class HoverEvent internal constructor(
    val action: HoverAction,
    val contents: JsonElement
)

/**
 * Creates a new [HoverEvent] that shows text when hovered
 */
@ComponentDSL
fun showText(text: String) = HoverEvent(HoverAction.SHOW_TEXT, JsonPrimitive(text))

/**
 * Creates a new [HoverEvent] that shows an item when hovered
 */
@ComponentDSL
fun showItem(item: Item) = HoverEvent(HoverAction.SHOW_ITEM, JsonObject(item.toJSON()))

/**
 * Creates a new [HoverEvent] that shows an entity when hovered
 */
@ComponentDSL
fun showEntity(entity: Entity) = HoverEvent(HoverAction.SHOW_ENTITY, JsonObject(entity.toJSON()))

@Serializable
enum class HoverAction {

    @SerialName("show_text") SHOW_TEXT,
    @SerialName("show_item") SHOW_ITEM,
    @SerialName("show_entity") SHOW_ENTITY;

    override fun toString() = name.toLowerCase()
}

@Serializable
data class Item(
    val id: Int,
    val count: Int
) {

    fun toJSON() = mapOf(
        "id" to JsonPrimitive(id),
        "count" to JsonPrimitive(count)
    )
}

@Serializable
data class Entity(
    @Serializable(with = UUIDSerialiser::class) val id: UUID,
    val type: String,
    val name: String? = null
) {

    fun toJSON() = mapOf(
        "id" to JsonPrimitive(id.toString()),
        "type" to JsonPrimitive(type),
        name.let { "name" to JsonPrimitive(name) }
    )
}

object UUIDSerialiser : KSerializer<UUID> {

    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: UUID) = encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder): UUID = UUID.fromString(decoder.decodeString())
}