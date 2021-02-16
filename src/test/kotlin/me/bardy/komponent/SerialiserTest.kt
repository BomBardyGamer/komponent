package me.bardy.komponent

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.serializer
import me.bardy.komponent.colour.ColourSerialiser
import me.bardy.komponent.colour.EmptyColor
import me.bardy.komponent.event.openURL
import kotlin.test.Test
import kotlin.test.assertEquals

// TESTS ARE CURRENTLY BROKEN! IGNORE THEM!
class SerialiserTest {

    @Test
    fun `test click event serialisation`() {
        val json = Json {}
        val clickEvent = openURL("https://example.com")

        val expectedString = "\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://example.com\"}"
        assertEquals(expectedString, json.encodeToString(clickEvent))
    }

    @Test
    fun `test text component serialisation`() {
        val component = TextComponent(
            "Hello World!",
            null,
            null,
            null,
            null,
            null,
            EmptyColor,
            null,
            null,
            null,
            emptyList()
        )

        val jsonString = Json {}.encodeToString(serializer(), component)

        println(jsonString)
    }
}