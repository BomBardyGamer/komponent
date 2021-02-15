package me.bardy.komponent.colour

import kotlinx.serialization.Serializable

/**
 * Represents a named colour (all of the legacy colours are in here)
 *
 * @author Callum Seabrook
 */
@Serializable(with = ColourSerialiser::class)
enum class NamedColor(override val value: Int) : Color {

    BLACK(0x000000),
    DARK_BLUE(0x0000AA),
    DARK_GREEN(0x00AA00),
    DARK_AQUA(0x00AAAA),
    DARK_RED(0xAA0000),
    DARK_PURPLE(0xAA00AA),
    GOLD(0xFFAA00),
    GRAY(0xAAAAAA),
    DARK_GRAY(0x555555),
    BLUE(0x5555FF),
    GREEN(0x55FF55),
    AQUA(0x55FFFF),
    RED(0xFF5555),
    LIGHT_PURPLE(0xFF55FF),
    YELLOW(0xFFFF55),
    WHITE(0xFFFFFF);

    override fun toString() = name.toLowerCase()

    companion object {

        fun from(value: Int): NamedColor? = when (value) {
            BLACK.value -> BLACK
            DARK_BLUE.value -> DARK_BLUE
            DARK_GREEN.value -> DARK_GREEN
            DARK_AQUA.value -> DARK_AQUA
            DARK_RED.value -> DARK_RED
            DARK_PURPLE.value -> DARK_PURPLE
            GOLD.value -> GOLD
            GRAY.value -> GRAY
            DARK_GRAY.value -> DARK_GRAY
            BLUE.value -> BLUE
            GREEN.value -> GREEN
            AQUA.value -> AQUA
            RED.value -> RED
            LIGHT_PURPLE.value -> LIGHT_PURPLE
            YELLOW.value -> YELLOW
            WHITE.value -> WHITE
            else -> null
        }
    }
}