@file:Suppress("unused")
package me.bardy.komponent.dsl

import me.bardy.komponent.*
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
    fun text(text: String, builder: TextComponentBuilder.() -> Unit = {}): Component {
        val component = TextComponentBuilder(text).apply(builder).build()
        children += component
        return component
    }

    @ComponentDSL
    fun translation(key: String, builder: TranslationComponentBuilder.() -> Unit = {}): Component {
        val component = TranslationComponentBuilder(key).apply(builder).build()
        children += component
        return component
    }

    @ComponentDSL
    fun keybind(keybind: String, builder: KeybindComponentBuilder.() -> Unit = {}): Component {
        val component = KeybindComponentBuilder(keybind).apply(builder).build()
        children += component
        return component
    }

    @ComponentDSL
    fun score(score: Score, builder: ScoreComponentBuilder.() -> Unit = {}): Component {
        val component = ScoreComponentBuilder(score).apply(builder).build()
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

class TranslationComponentBuilder(private val key: String) : ComponentBuilder() {

    fun build(): TranslationComponent {
        val (bold, italic, underlined, strikethrough, obfuscated) = formatting.build()
        return TranslationComponent(key, bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, children)
    }
}

class KeybindComponentBuilder(private val keybind: String) : ComponentBuilder() {

    fun build(): KeybindComponent {
        val (bold, italic, underlined, strikethrough, obfuscated) = formatting.build()
        return KeybindComponent(keybind, bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, children)
    }
}

class ScoreComponentBuilder(private val score: Score) : ComponentBuilder() {

    fun build(): ScoreComponent {
        val (bold, italic, underlined, strikethrough, obfuscated) = formatting.build()
        return ScoreComponent(score, bold, italic, underlined, strikethrough, obfuscated, color, insertion, clickEvent, hoverEvent, children)
    }
}