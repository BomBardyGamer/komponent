package me.bardy.komponent

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import me.bardy.komponent.colour.Color
import me.bardy.komponent.colour.EmptyColor
import me.bardy.komponent.dsl.component
import me.bardy.komponent.event.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

// tests are a W.I.P
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
            text = "Hello World!",
            bold = true,
            italic = true,
            underlined = true,
            strikethrough = true,
            obfuscated = true,
            color = Color.fromRGB(255, 255, 255),
            insertion = "I am insertion!",
            clickEvent = openURL("https://example.com"),
            hoverEvent = showText("I am hover text!"),
            extra = listOf(
                TranslationComponent(
                    "i.am.a.translation.key",
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
            )
        )

        val jsonString = Json {}.encodeToString(serializer(), component)

        println(jsonString)
    }

    @Test
    fun `test serialisation of DSL`() {
        val component = component {
            text("Hello World!") {

            }
        }

        println(Json {}.encodeToString(Component.Companion, component))
    }

    @Test
    fun `test component deserialisation`() {
        val componentRaw = "{\"text\":\"I am text!\"}"

        val component = Json {}.decodeFromString<TextComponent>(componentRaw)

        println(component.toString())
    }
}