package me.bardy.komponent.dsl

import me.bardy.komponent.Component
import me.bardy.komponent.KeybindComponent
import me.bardy.komponent.TextComponent
import me.bardy.komponent.TranslationComponent
import me.bardy.komponent.colour.Color
import me.bardy.komponent.colour.EmptyColor
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.HoverEvent

abstract class ComponentBuilder internal constructor() {

    protected val children: MutableList<Component> = mutableListOf()

    protected val formatting = FormatBuilder()

    @ComponentDSL
    var color: Color = EmptyColor

    @ComponentDSL
    var insertion: String? = null

    @ComponentDSL
    var clickEvent: ClickEvent? = null

    @ComponentDSL
    var hoverEvent: HoverEvent? = null

    @ComponentDSL
    fun formatting(builder: FormatBuilder.() -> Unit) = formatting.apply(builder)

    @ComponentDSL
    fun children(builder: RootComponentBuilder.() -> Unit): Component {
        val component = RootComponentBuilder().apply(builder).build()
        children += component
        return component
    }
}

class TextComponentBuilder(private val text: String) : ComponentBuilder() {

    fun build(): TextComponent {
        val (bold, italic, underlined, strikethrough, obfuscated) = formatting.build()
        return TextComponent(text, bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, children)
    }
}

class TranslationComponentBuilder internal constructor(private val key: String) : ComponentBuilder() {

    fun build(): TranslationComponent {
        val (bold, italic, underlined, strikethrough, obfuscated) = formatting.build()
        return TranslationComponent(key, bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, children)
    }
}

class KeybindComponentBuilder internal constructor(private val keybind: String) : ComponentBuilder() {

    fun build(): KeybindComponent {
        val (bold, italic, underlined, strikethrough, obfuscated) = formatting.build()
        return KeybindComponent(keybind, bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, children)
    }
}