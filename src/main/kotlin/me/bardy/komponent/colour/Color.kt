package me.bardy.komponent.colour

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Represents an RGB colour. Stored as a single RGB integer, where the bits 16-23 represent
 * the red value, 8-15 represent the green value and 0-7 represent the blue value
 *
 * Usage:
 *
 * all these methods create an RGB colour with the RGB value 16777215
 * ```kotlin
 * Color.fromHex("#ffffff") (# prefix is optional)
 * Color.fromRGB(16777215)
 * Color.fromRGB(255, 255, 255)
 * Color.fromRGB(1.0f, 1.0f, 1.0f)
 * ```
 *
 * this method attempts to convert the value to a [NamedColor], or else returns an [RGBColor]
 * ```kotlin
 * Color.fromInt(16777215)
 * ```
 *
 * @author Callum Seabrook
 *
 * @see NamedColor
 * @see RGBColor
 */
@Serializable(with = ColourSerialiser::class)
interface Color {

    val value: Int

    @Suppress("unused") // API functions
    companion object {

        /**
         * Creates a new RGB colour from the [hex] string provided
         *
         * This can optionally be prefixed with the character '#'
         */
        fun fromHex(hex: String): Color = RGBColor(hex.removePrefix("#").toInt(16))

        /**
         * Creates a new RGB colour from the [rgb] value provided by converting the [rgb] value to an integer
         *
         * @see RGB.toInt
         */
        fun fromRGB(rgb: RGB): Color = RGBColor(rgb.toInt())

        /**
         * Creates a new RGB colour from the [red], [green] and [blue] values provided by converting them to
         * a single RGB integer
         *
         * @see RGB.toInt
         */
        fun fromRGB(red: Int, green: Int, blue: Int): Color = RGBColor(RGB(red, green, blue).toInt())

        /**
         * Creates a new RGB colour from the [red], [green] and [blue] values provided by converting them to
         * RGB integers, then converting those integers to a single RGB integer
         *
         * @see RGB.fromFloatRGB
         * @see RGB.toInt
         */
        fun fromRGB(red: Float, green: Float, blue: Float): Color = RGBColor(RGB.fromFloatRGB(red, green, blue).toInt())

        /**
         * Attempts to get a [NamedColor] with the specified RGB [value], or else creates a new RGB colour from
         * the [value] provided
         */
        fun fromInt(value: Int): Color {
            val named = NamedColor.from(value)
            return named ?: RGBColor(value)
        }
    }
}

internal object EmptyColor : Color {

    override val value = -1
}

internal object ColourSerialiser : KSerializer<Color> {

    override val descriptor = PrimitiveSerialDescriptor("Color", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): Color {
        val value = decoder.decodeString().removePrefix("#").toInt(16)
        if (value < 0) return EmptyColor
        return NamedColor.from(value) ?: RGBColor(value)
    }

    override fun serialize(encoder: Encoder, value: Color) {
        encoder.encodeString(value.value.toHexString())
    }
}

/**
 * Converts an integer to it's hex representation by converting it to base 16 and appending the '#'
 * character to the start
 *
 * For example, `16777215` becomes `#ffffff`
 *
 * @author Callum Seabrook
 */
fun Int.toHexString(): String = "#${Integer.toHexString(this).padEnd(6, '0')}"