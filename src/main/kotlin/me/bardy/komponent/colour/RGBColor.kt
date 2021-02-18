package me.bardy.komponent.colour

import kotlinx.serialization.Serializable

/**
 * Wrapper for unnamed RGB colours
 *
 * @author Callum Seabrook
 */
@Serializable(with = ColourSerialiser::class)
internal data class RGBColor(
    override val value: Int
) : Color

/**
 * Represents an RGB colour (separate values of red, green and blue)
 *
 * @author Callum Seabrook
 */
@Serializable
data class RGB(
    val red: Int,
    val green: Int,
    val blue: Int
) {

    init {
        // ensure provided values are within the bounds of RGB
        require(red in 0..255 && green in 0..255 && blue in 0..255) {
            "Provided values are not within the limits of RGB! Each value must be between 0-255!"
        }
    }

    /**
     * Converts this RGB colour to a single RGB integer
     */
    fun toInt() = (red shl 16) or (green shl 8) or blue

    @Suppress("unused") // API functions
    companion object {

        /**
         * Creates a new RGB colour from the specified [value] (RGB integer)
         */
        fun fromInt(value: Int) = RGB((value shr 16) and 0xFF, (value shr 8) and 0xFF, (value shr 0) and 0xFF)

        /**
         * Creates a new RGB colour from the specified [red], [green] and [blue] values
         */
        fun fromFloatRGB(red: Float, green: Float, blue: Float) = RGB((red * 0xFF).toInt(), (green * 0xFF).toInt(), (blue * 0xFF).toInt())
    }
}