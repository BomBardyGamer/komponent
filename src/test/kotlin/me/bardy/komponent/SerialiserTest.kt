package me.bardy.komponent

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import me.bardy.komponent.colour.Color
import me.bardy.komponent.colour.NamedColor
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.openURL
import me.bardy.komponent.event.showText
import kotlin.test.Test
import kotlin.test.assertEquals

class SerialiserTest {

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
                TranslationComponent("i.am.a.translation.key"),
                KeybindComponent("key.forward"),
                ScoreComponent(Score("test", "test", "test"))
            )
        )

        val expectedString = """
            {
                "text": "Hello World!",
                "bold": true,
                "italic": true,
                "underlined": true,
                "strikethrough": true,
                "obfuscated": true,
                "color": "#ffffff",
                "insertion": "I am insertion!",
                "clickEvent": {
                    "action": "open_url",
                    "value": "https://example.com"
                },
                "hoverEvent": {
                    "action": "show_text",
                    "contents": "I am hover text!"
                },
                "extra": [
                    {
                        "translate": "i.am.a.translation.key"
                    },
                    {
                        "keybind": "key.forward"
                    },
                    {
                        "score": {
                            "name": "test",
                            "objective": "test",
                            "value": "test"
                        }
                    }
                ]
            }
        """.trimIndent()
        val jsonString = Json { prettyPrint = true }.encodeToString(serializer(), component)

        assertEquals(expectedString, jsonString)
    }

    @Test
    fun `test complex component deserialisation`() {
        val componentRaw = """
            {
                "text": "Hello World!",
                "bold": true,
                "italic": true,
                "underlined": true,
                "strikethrough": true,
                "obfuscated": true,
                "color": "#ffffff",
                "insertion": "I am insertion!",
                "clickEvent": {
                    "action": "open_url",
                    "value": "https://example.com"
                },
                "hoverEvent": {
                    "action": "show_text",
                    "contents": "I am hover text!"
                },
                "extra": [
                    {
                        "translate": "i.am.a.translation.key"
                    },
                    {
                        "keybind": "key.forward"
                    },
                    {
                        "score": {
                            "name": "test",
                            "objective": "test",
                            "value": "test"
                        }
                    }
                ]
            }
        """.trimIndent()

        val expected = TextComponent(
            text = "Hello World!",
            bold = true,
            italic = true,
            underlined = true,
            strikethrough = true,
            obfuscated = true,
            color = NamedColor.WHITE,
            insertion = "I am insertion!",
            clickEvent = openURL("https://example.com"),
            hoverEvent = showText("I am hover text!"),
            extra = listOf(
                TranslationComponent("i.am.a.translation.key"),
                KeybindComponent("key.forward"),
                ScoreComponent(Score("test", "test", "test"))
            )
        )
        val component = Json { prettyPrint = true }.decodeFromString<TextComponent>(componentRaw)

        assertEquals(expected, component)
    }
}