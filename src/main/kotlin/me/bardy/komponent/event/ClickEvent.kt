@file:UseSerializers(
    OpenURLSerialiser::class,
    RunCommandSerialiser::class,
    SuggestCommandSerialiser::class,
    ChangePageSerialiser::class,
    CopyToClipboardSerialiser::class
)
package me.bardy.komponent.event

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import me.bardy.komponent.serialisers.*
import me.bardy.komponent.Component

/**
 * Represents a click event for a [Component]
 *
 * @author Callum Seabrook
 */
@Serializable
sealed class ClickEvent(
    val action: ClickAction,
    val content: String
) {

    @Suppress("unused") // API functions
    companion object {

        /**
         * Creates a new [ClickEvent] which opens a URL when clicked
         */
        fun openURL(url: String) = OpenURL(url)

        /**
         * Creates a new [ClickEvent] which runs a command when clicked
         */
        fun runCommand(command: String) = RunCommand(command)

        /**
         * Creates a new [ClickEvent] which suggests a command to run when clicked
         */
        fun suggestCommand(suggestedCommand: String) = SuggestCommand(suggestedCommand)

        /**
         * Creates a new [ClickEvent] which changes the current page of the book the user has opened
         * when clicked
         *
         * Requires that the user has a book opened to function correctly
         */
        fun changePage(page: Int) = ChangePage(page.toString())

        /**
         * Creates a new [ClickEvent] which copies the specified [value] to the user's clipboard when
         * clicked
         */
        fun copyToClipboard(value: String) = CopyToClipboard(value)
    }

    @Serializable
    data class OpenURL internal constructor(val url: String) : ClickEvent(ClickAction.OPEN_URL, url)

    @Serializable
    data class RunCommand internal constructor(val command: String) : ClickEvent(ClickAction.RUN_COMMAND, command)

    @Serializable
    data class SuggestCommand internal constructor(val suggestedCommand: String) : ClickEvent(ClickAction.SUGGEST_COMMAND, suggestedCommand)

    @Serializable
    data class ChangePage internal constructor(val page: String) : ClickEvent(ClickAction.CHANGE_PAGE, page)

    @Serializable
    data class CopyToClipboard internal constructor(val value: String) : ClickEvent(ClickAction.COPY_TO_CLIPBOARD, value)
}

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