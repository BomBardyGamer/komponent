package me.bardy.komponent.colour

import kotlinx.serialization.KSerializer
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
 * Colour.fromHex("#ffffff") (# prefix is optional)
 * Colour.fromRGB(16777215)
 * Colour.fromRGB(255, 255, 255)
 * Colour.fromRGB(1.0f, 1.0f, 1.0f)
 * ```
 *
 * this method attempts to convert the value to a [NamedColour], or else returns an [RGBColour]
 * ```kotlin
 * Colour.fromInt(16777215)
 * ```
 *
 * @author Callum Seabrook
 *
 * @see NamedColour
 * @see RGBColour
 */
interface Colour {

    val value: Int

    @Suppress("unused") // API functions
    companion object {

        /**
         * Creates a new RGB colour from the [hex] string provided
         *
         * This can optionally be prefixed with the character '#'
         */
        fun fromHex(hex: String): Colour = RGBColour(hex.removePrefix("#").toInt(16))

        /**
         * Creates a new RGB colour from the [rgb] value provided by converting the [rgb] value to an integer
         *
         * @see RGB.toInt
         */
        fun fromRGB(rgb: RGB): Colour = RGBColour(rgb.toInt())

        /**
         * Creates a new RGB colour from the [red], [green] and [blue] values provided by converting them to
         * a single RGB integer
         *
         * @see RGB.toInt
         */
        fun fromRGB(red: Int, green: Int, blue: Int): Colour = RGBColour(RGB(red, green, blue).toInt())

        /**
         * Creates a new RGB colour from the [red], [green] and [blue] values provided by converting them to
         * RGB integers, then converting those integers to a single RGB integer
         *
         * @see RGB.fromFloatRGB
         * @see RGB.toInt
         */
        fun fromRGB(red: Float, green: Float, blue: Float): Colour = RGBColour(RGB.fromFloatRGB(red, green, blue).toInt())

        /**
         * Attempts to get a [NamedColour] with the specified RGB [value], or else creates a new RGB colour from
         * the [value] provided
         */
        fun fromInt(value: Int): Colour {
            val named = NamedColour.from(value)
            return named ?: RGBColour(value)
        }
    }
}

internal object ColourSerialiser : KSerializer<Colour> {

    override val descriptor = PrimitiveSerialDescriptor("Colour", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): Colour {
        val value = decoder.decodeString().removePrefix("#").toInt(16)
        return NamedColour.from(value) ?: RGBColour(value)
    }

    override fun serialize(encoder: Encoder, value: Colour) {
        encoder.encodeString(value.value.toHexString())
    }
}

internal object NullableColourSerialiser : KSerializer<Colour?> {

    override val descriptor = PrimitiveSerialDescriptor("Colour", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): Colour {
        val value = decoder.decodeString().removePrefix("#").toInt(16)
        return NamedColour.from(value) ?: RGBColour(value)
    }

    override fun serialize(encoder: Encoder, value: Colour?) {
        if (value != null) encoder.encodeString(value.value.toHexString())
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
fun Int.toHexString(): String = "#${Integer.toHexString(this)}"