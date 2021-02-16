@file:Suppress("unused") // API functions
package me.bardy.komponent.event

import kotlinx.serialization.Serializable
import me.bardy.komponent.Component

/**
 * Represents a click event for a [Component]
 *
 * @author Callum Seabrook
 */
@Serializable
data class ClickEvent(
    val action: ClickAction,
    val value: String
)

/**
 * Creates a new [ClickEvent] which opens a URL when clicked
 */
fun openURL(url: String) = ClickEvent(ClickAction.OPEN_URL, url)

/**
 * Creates a new [ClickEvent] which runs a command when clicked
 */
fun runCommand(command: String) = ClickEvent(ClickAction.RUN_COMMAND, command)

/**
 * Creates a new [ClickEvent] which suggests a command to run when clicked
 */
fun suggestCommand(suggestedCommand: String) = ClickEvent(ClickAction.SUGGEST_COMMAND, suggestedCommand)

/**
 * Creates a new [ClickEvent] which changes the current page of the book the user has opened
 * when clicked
 *
 * Requires that the user has a book opened to function correctly
 */
fun changePage(page: Int) = ClickEvent(ClickAction.CHANGE_PAGE, page.toString())

/**
 * Creates a new [ClickEvent] which copies the specified [value] to the user's clipboard when
 * clicked
 */
fun copyToClipboard(value: String) = ClickEvent(ClickAction.COPY_TO_CLIPBOARD, value)

/**
 * Actions for [ClickEvent]
 *
 * @author Callum Seabrook
 */
enum class ClickAction {

    OPEN_URL,
    RUN_COMMAND,
    SUGGEST_COMMAND,
    CHANGE_PAGE,
    COPY_TO_CLIPBOARD;

    override fun toString() = name.toLowerCase()
}