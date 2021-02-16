package me.bardy.komponent.colour

import kotlinx.serialization.Serializable

/**
 * Represents a named colour (all of the legacy colours are in here)
 *
 * @author Callum Seabrook
 */
@Serializable(with = ColourSerialiser::class)
enum class NamedColor(override val value: Int, private val legacy: Char) : Color {

    BLACK(0x000000, '0'),
    DARK_BLUE(0x0000AA, '1'),
    DARK_GREEN(0x00AA00, '2'),
    DARK_AQUA(0x00AAAA, '3'),
    DARK_RED(0xAA0000, '4'),
    DARK_PURPLE(0xAA00AA, '5'),
    GOLD(0xFFAA00, '6'),
    GRAY(0xAAAAAA, '7'),
    DARK_GRAY(0x555555, '8'),
    BLUE(0x5555FF, '9'),
    GREEN(0x55FF55, 'a'),
    AQUA(0x55FFFF, 'b'),
    RED(0xFF5555, 'c'),
    LIGHT_PURPLE(0xFF55FF, 'd'),
    YELLOW(0xFFFF55, 'e'),
    WHITE(0xFFFFFF, 'f');

    override fun toString() = name.toLowerCase()

    /**
     * Converts this [NamedColor] to it's legacy format (a colour character followed by the corresponding
     * [legacy] character)
     *
     * For example, [NamedColor.AQUA] becomes [char] followed by 'b' (with Mojang's legacy code, this would
     * be §b)
     */
    @Suppress("unused") // API function
    fun toLegacy(char: Char = LEGACY_CHARACTER) = "$char$legacy"

    companion object {

        private const val LEGACY_CHARACTER = '§'

        /**
         * Attempts to convert the provided RGB integer [value] to a [NamedColor], or returns null if there
         * is no [NamedColor] with the specified RGB [value]
         *
         * For example, if [value] was 0, this would return [NamedColor.BLACK], but if [value] was 1, it
         * would return null
         */
        fun from(value: Int) = when (value) {
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