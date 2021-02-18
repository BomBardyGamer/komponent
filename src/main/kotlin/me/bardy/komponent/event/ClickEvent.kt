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
import kotlinx.serialization.json.JsonPrimitive
import me.bardy.komponent.Component
import me.bardy.komponent.dsl.ComponentDSL

/**
 * Represents a click event for a [Component]
 *
 * @author Callum Seabrook
 */
@Serializable
data class ClickEvent internal constructor(
    val action: ClickAction,
    val value: JsonElement
)

/**
 * Creates a new [ClickEvent] which opens a URL when clicked
 */
@ComponentDSL
fun openURL(url: String) = ClickEvent(ClickAction.OPEN_URL, JsonPrimitive(url))

/**
 * Creates a new [ClickEvent] which runs a command when clicked
 */
@ComponentDSL
fun runCommand(command: String) = ClickEvent(ClickAction.RUN_COMMAND, JsonPrimitive(command))

/**
 * Creates a new [ClickEvent] which suggests a command to run when clicked
 */
@ComponentDSL
fun suggestCommand(suggestedCommand: String) = ClickEvent(ClickAction.SUGGEST_COMMAND, JsonPrimitive(suggestedCommand))

/**
 * Creates a new [ClickEvent] which changes the current page of the book the user has opened
 * when clicked
 *
 * Requires that the user has a book opened to function correctly
 */
@ComponentDSL
fun changePage(page: Int) = ClickEvent(ClickAction.CHANGE_PAGE, JsonPrimitive(page))

/**
 * Creates a new [ClickEvent] which copies the specified [value] to the user's clipboard when
 * clicked
 */
@ComponentDSL
fun copyToClipboard(value: String) = ClickEvent(ClickAction.COPY_TO_CLIPBOARD, JsonPrimitive(value))

/**
 * Actions for [ClickEvent]
 *
 * @author Callum Seabrook
 */
@Serializable
enum class ClickAction {

    @SerialName("open_url") OPEN_URL,
    @SerialName("run_command") RUN_COMMAND,
    @SerialName("suggest_command") SUGGEST_COMMAND,
    @SerialName("change_page") CHANGE_PAGE,
    @SerialName("copy_to_clipboard") COPY_TO_CLIPBOARD;

    override fun toString() = name.toLowerCase()
}