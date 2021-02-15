package me.bardy.komponent.dsl

import me.bardy.komponent.ComponentDecoration

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