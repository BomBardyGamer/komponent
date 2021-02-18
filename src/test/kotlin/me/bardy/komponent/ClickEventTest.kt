package me.bardy.komponent

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.bardy.komponent.event.*
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class ClickEventTest {

    private val json = Json { prettyPrint = true }

    @Test
    fun `test open url click event`() {
        val clickEvent = openURL("https://example.com")

        val expectedString = """
            {
                "action": "open_url",
                "value": "https://example.com"
            }
        """.trimIndent()
        val jsonString = json.encodeToString(clickEvent)

        assertEquals(expectedString, jsonString)
    }

    @Test
    fun `test run command click event`() {
        val command = randomString(16)
        val clickEvent = runCommand(command)

        val expectedString = """
            {
                "action": "run_command",
                "value": "$command"
            }
        """.trimIndent()
        val jsonString = json.encodeToString(clickEvent)

        assertEquals(expectedString, jsonString)
    }

    @Test
    fun `test suggest command click event`() {
        val command = randomString(20)
        val clickEvent = suggestCommand(command)

        val expectedString = """
            {
                "action": "suggest_command",
                "value": "$command"
            }
        """.trimIndent()
        val jsonString = json.encodeToString(clickEvent)

        assertEquals(expectedString, jsonString)
    }

    @Test
    fun `test change page click event`() {
        val page = Random.nextInt(100)
        val clickEvent = changePage(page)

        val expectedString = """
            {
                "action": "change_page",
                "value": $page
            }
        """.trimIndent()
        val jsonString = json.encodeToString(clickEvent)

        assertEquals(expectedString, jsonString)
    }

    @Test
    fun `test copy to clipboard click event`() {
        val value = randomString(10)
        val clickEvent = copyToClipboard(value)

        val expectedString = """
            {
                "action": "copy_to_clipboard",
                "value": "$value"
            }
        """.trimIndent()
        val jsonString = json.encodeToString(clickEvent)

        assertEquals(expectedString, jsonString)
    }
}