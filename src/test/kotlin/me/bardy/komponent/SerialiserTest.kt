package me.bardy.komponent

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.serializer
import me.bardy.komponent.colour.NamedColor
import me.bardy.komponent.dsl.component
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.openURL
import me.bardy.komponent.serialisers.*
import kotlin.test.Test
import kotlin.test.assertEquals

// TESTS ARE CURRENTLY BROKEN! IGNORE THEM!
class SerialiserTest {

    @Test
    fun `test click event serialisation`() {
        val json = Json {}
        val clickEvent = openURL("https://example.com")

        val expectedString = "{\"open_url\": \"https://example.com\"}"
        assertEquals(expectedString, json.encodeToString(clickEvent))
    }

    @Test
    fun `test text component serialisation`() {
        val component = component {
            text("Hello World!") {
                color = NamedColor.WHITE
                insertion = "I am Mr. Insertor"
                formatting {
                    bold = true
                }
                clickEvent = openURL("https://example.com")
            }
        }

        val serialisersModule = SerializersModule {
            polymorphic(Component::class) {
                subclass(TextComponent::class, TextComponentSerialiser)
                subclass(TranslationComponent::class, TranslationComponentSerialiser)
                subclass(KeybindComponent::class, KeybindComponentSerialiser)
            }

            polymorphic(ClickEvent::class) {
                subclass(ClickEvent.OpenURL::class, OpenURLSerialiser)
                subclass(ClickEvent.RunCommand::class, RunCommandSerialiser)
                subclass(ClickEvent.SuggestCommand::class, SuggestCommandSerialiser)
                subclass(ClickEvent.ChangePage::class, ChangePageSerialiser)
                subclass(ClickEvent.CopyToClipboard::class, CopyToClipboardSerialiser)
            }
        }

        val jsonString = Json { serializersModule = serialisersModule }.encodeToString(serializer(), component)

        println(jsonString)
    }
}