package me.bardy.komponent

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ComponentTest {

    private val json = Json { prettyPrint = true }

    @Test
    fun `test text component serialisation`() {
        val component = TextComponent(
            "I am text!",
            extra = listOf(
                TranslationComponent("i.am.a.translation.key"),
                KeybindComponent("key.forward"),
                ScoreComponent(Score("test", "test", "test"))
            )
        )

        val expectedString = """
            {
                "text": "I am text!",
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
        val jsonString = json.encodeToString(component)

        assertEquals(expectedString, jsonString)
    }

    @Test
    fun `test translation component serialisation`() {
        val component = TranslationComponent("i.am.a.translation.key")

        val expectedString = """
            {
                "translate": "i.am.a.translation.key"
            }
        """.trimIndent()
        val jsonString = json.encodeToString(component)

        assertEquals(expectedString, jsonString)
    }

    @Test
    fun `test keybind component serialisation`() {
        val component = KeybindComponent("key.forward")

        val expectedString = """
            {
                "keybind": "key.forward"
            }
        """.trimIndent()
        val jsonString = json.encodeToString(component)

        assertEquals(expectedString, jsonString)
    }

    @Test
    fun `test score component serialisation`() {
        val component = ScoreComponent(Score("test", "test", "test"))

        val expectedString = """
            {
                "score": {
                    "name": "test",
                    "objective": "test",
                    "value": "test"
                }
            }
        """.trimIndent()
        val jsonString = json.encodeToString(component)

        assertEquals(expectedString, jsonString)
    }
}