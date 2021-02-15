package me.bardy.komponent.dsl

import me.bardy.komponent.Component
import me.bardy.komponent.KeybindComponent
import me.bardy.komponent.TextComponent
import me.bardy.komponent.TranslationComponent
import me.bardy.komponent.colour.Colour
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.HoverEvent

sealed class ComponentBuilder : Component.Builder {

    protected val children: MutableList<Component> = mutableListOf()

    protected val formatting = FormatBuilder()

    @ComponentDSL
    override var colour: Colour? = null

    @ComponentDSL
    override var insertion: String? = null

    @ComponentDSL
    override var clickEvent: ClickEvent? = null

    @ComponentDSL
    override var hoverEvent: HoverEvent<String>? = null

    @ComponentDSL
    override fun formatting(builder: FormatBuilder.() -> Unit) = formatting.apply(builder)

    @ComponentDSL
    override fun children(builder: DSLBuilder.() -> Unit): Component {
        val component = DSLBuilder().apply(builder).build()
        children += component
        return component
    }
}

class TextComponentBuilder internal constructor(private val text: String) : ComponentBuilder() {

    override fun build() = TextComponent(text, formatting.build(), colour, insertion, clickEvent, hoverEvent, children)
}

class TranslationComponentBuilder internal constructor(private val key: String) : ComponentBuilder() {

    override fun build() = TranslationComponent(key, formatting.build(), colour, insertion, clickEvent, hoverEvent, children)
}

class KeybindComponentBuilder internal constructor(private val keybind: String) : ComponentBuilder() {

    override fun build() = KeybindComponent(keybind, formatting.build(), colour, insertion, clickEvent, hoverEvent, children)
}