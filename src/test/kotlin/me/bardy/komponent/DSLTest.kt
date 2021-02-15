package me.bardy.komponent

import me.bardy.komponent.colour.NamedColour
import me.bardy.komponent.dsl.component
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.HoverEvent

class DSLTest {

    fun `komponent DSL test`() {
        component {
            text("Hello World!") {
                colour = NamedColour.BLACK
                formatting {
                    bold = true
                    italic = true
                }
                clickEvent = ClickEvent.openURL("https://example.com")
                hoverEvent = HoverEvent.showText("Click me")

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