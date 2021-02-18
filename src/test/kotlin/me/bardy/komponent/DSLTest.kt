package me.bardy.komponent

import kotlinx.serialization.json.Json
import me.bardy.komponent.colour.NamedColor
import me.bardy.komponent.dsl.textComponent
import me.bardy.komponent.event.Item
import me.bardy.komponent.event.openURL
import me.bardy.komponent.event.showItem
import kotlin.test.Test
import kotlin.test.assertEquals

class DSLTest {

    @Test
    fun `test serialisation of DSL with a basic text component`() {
        val component = textComponent("Hello World!")

        val expectedString = """
            {
                "text": "Hello World!"
            }
        """.trimIndent()
        val jsonString = Json { prettyPrint = true }.encodeToString(Component.Companion, component)

        assertEquals(expectedString, jsonString)
    }

    @Test
    fun `test serialisation of DSL with something a bit more complex`() {
        val component = textComponent("Hello World!") {
            color = NamedColor.BLACK
            formatting {
                bold = true
                italic = true
                underlined = true
                strikethrough = true
                obfuscated = true
            }
            insertion = "I am insertion text!"
            clickEvent = openURL("https://example.com")
            hoverEvent = showItem(Item(3, 3))

            translation("i.am.a.translation.key") {
                color = NamedColor.AQUA
                formatting {
                    bold = true
                    italic = true
                }
            }

            keybind("key.forward") {
                formatting {
                    obfuscated = true
                }
            }

            score(Score("test", "test", "test")) {
                insertion = "Some text needs to go here!"
            }
        }

        val expectedString = """
            {
                "text": "Hello World!",
                "bold": true,
                "italic": true,
                "underlined": true,
                "strikethrough": true,
                "obfuscated": true,
                "color": "#000000",
                "insertion": "I am insertion text!",
                "clickEvent": {
                    "action": "open_url",
                    "value": "https://example.com"
                },
                "hoverEvent": {
                    "action": "show_item",
                    "contents": {
                        "id": 3,
                        "count": 3
                    }
                },
                "extra": [
                    {
                        "translate": "i.am.a.translation.key",
                        "bold": true,
                        "italic": true,
                        "color": "#55ffff"
                    },
                    {
                        "keybind": "key.forward",
                        "obfuscated": true
                    },
                    {
                        "score": {
                            "name": "test",
                            "objective": "test",
                            "value": "test"
                        },
                        "insertion": "Some text needs to go here!"
                    }
                ]
            }
        """.trimIndent()
        val jsonString = Json { prettyPrint = true }.encodeToString(Component.Companion, component)

        assertEquals(expectedString, jsonString)
    }
}