package me.bardy.komponent

import me.bardy.komponent.colour.NamedColor
import me.bardy.komponent.dsl.component
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.HoverEvent
import me.bardy.komponent.event.openURL
import me.bardy.komponent.event.showText

class DSLTest {

    fun `komponent DSL test`() {
        component {
            text("Hello World!") {
                color = NamedColor.BLACK
                formatting {
                    bold = true
                    italic = true
                }
                clickEvent = openURL("https://example.com")
                hoverEvent = showText("Click me")

                children {
                    text("I am a child!")
                }
            }

            translation("chat.type.text") {

            }

            keybind("key.forward") {

            }
        }
    }
}