package me.bardy.komponent.dsl

import me.bardy.komponent.ComponentDecoration

class FormatBuilder internal constructor() {

    @ComponentDSL
    var bold = false

    @ComponentDSL
    var italic = false

    @ComponentDSL
    var underlined = false

    @ComponentDSL
    var strikethrough = false

    @ComponentDSL
    var obfuscated = false

    fun build() = ComponentDecoration(bold, italic, underlined, strikethrough, obfuscated)
}