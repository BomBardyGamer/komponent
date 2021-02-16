package me.bardy.komponent

import kotlinx.serialization.json.Json
import me.bardy.komponent.colour.NamedColor
import me.bardy.komponent.dsl.component
import me.bardy.komponent.event.openURL
import me.bardy.komponent.event.showText
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

// tests are a W.I.P
class DSLTest {

    @Test
    fun `DSL serialises properly`() {
        val component = component {
            text("Hello world!") {
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
                hoverEvent = showText("I am hover text!")

                children {
                    translation("i.am.a.translation.key") {
                        color = NamedColor.AQUA
                        formatting {
                            bold = false
                            italic = false
                        }
                    }
                }
            }
        }

        println(Json {}.encodeToString(Component.Companion, component))
    }

    @Test
    fun `DSL throws exception when multiple root components are defined`() {
        assertThrows<UnsupportedOperationException> {
            component {
                text("")
                translation("")
            }
        }
    }
}