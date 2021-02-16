package me.bardy.komponent.dsl

class FormatBuilder internal constructor() {

    @ComponentDSL
    var bold: Boolean? = null

    @ComponentDSL
    var italic: Boolean? = null

    @ComponentDSL
    var underlined: Boolean? = null

    @ComponentDSL
    var strikethrough: Boolean? = null

    @ComponentDSL
    var obfuscated: Boolean? = null

    fun build() = ComponentDecoration(bold, italic, underlined, strikethrough, obfuscated)
}

data class ComponentDecoration internal constructor(
    val bold: Boolean?,
    val italic: Boolean?,
    val underlined: Boolean?,
    val strikethrough: Boolean?,
    val obfuscated: Boolean?
)