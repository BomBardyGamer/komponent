package me.bardy.komponent

import me.bardy.komponent.colour.Color
import me.bardy.komponent.colour.NamedColor
import me.bardy.komponent.colour.RGB
import me.bardy.komponent.colour.toHexString
import kotlin.test.Test
import kotlin.test.assertEquals

class ColourTest {

    @Test
    fun `test hex conversions`() {
        val hexString = "#ffffff"
        val colour = Color.fromHex(hexString)

        assertEquals(hexString, colour.value.toHexString())
    }

    @Test
    fun `test RGB conversions`() {
        val rgbFloat = RGB.fromFloatRGB(0.7f, 0.8f, 0.35f)
        val rgb = RGB(255, 255, 255)
        val rgbInt = rgb.toInt()

        val rgbFloatColour = Color.fromRGB(0.7f, 0.8f, 0.35f)
        val rgbColour = Color.fromRGB(rgb)
        val rgbIntColour = Color.fromInt(rgbInt)

        assertEquals(rgbInt, rgbColour.value)
        assertEquals(rgbInt, rgbIntColour.value)
        assertEquals(rgbFloat.toInt(), rgbFloatColour.value)
    }

    @Test
    fun `test named conversions`() {
        val rgb = RGB(0, 0, 0)

        val colour = Color.fromInt(rgb.toInt())

        assertEquals(colour::class, NamedColor::class)
    }
}