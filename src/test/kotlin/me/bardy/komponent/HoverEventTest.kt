package me.bardy.komponent

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.bardy.komponent.event.*
import java.util.*
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class HoverEventTest {

    private val json = Json { prettyPrint = true }

    @Test
    fun `test show text hover event`() {
        val hoverEvent = showText("I am hover text!")

        val expectedString = """
            {
                "action": "show_text",
                "contents": "I am hover text!"
            }
        """.trimIndent()
        val jsonString = json.encodeToString(hoverEvent)

        assertEquals(expectedString, jsonString)
    }

    @Test
    fun `test show item hover event`() {
        val id = Random.nextInt(100)
        val count = Random.nextInt(100)

        val hoverEvent = showItem(Item(id, count))

        val expectedString = """
            {
                "action": "show_item",
                "contents": {
                    "id": $id,
                    "count": $count
                }
            }
        """.trimIndent()
        val jsonString = json.encodeToString(hoverEvent)

        assertEquals(expectedString, jsonString)
    }

    @Test
    fun `test show entity hover event`() {
        val uuid = UUID.randomUUID()
        val type = "minecraft:pig"
        val name = randomString(16)

        val hoverEvent = showEntity(Entity(uuid, type, name))

        val expectedString = """
            {
                "action": "show_entity",
                "contents": {
                    "id": "$uuid",
                    "type": "$type",
                    "name": "$name"
                }
            }
        """.trimIndent()
        val jsonString = json.encodeToString(hoverEvent)

        assertEquals(expectedString, jsonString)
    }
}

private val CHARACTERS = ('A'..'Z') + ('a'..'z') + ('0'..'9')

fun randomString(length: Int) = (1..length)
    .map { Random.nextInt(0, CHARACTERS.size) }
    .map(CHARACTERS::get)
    .joinToString("")