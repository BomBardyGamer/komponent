package me.bardy.komponent.dsl

import me.bardy.komponent.Component

@DslMarker
annotation class ComponentDSL


@ComponentDSL
inline fun component(builder: DSLBuilder.() -> Unit): Component {
    return DSLBuilder().apply(builder).build()
}

@ComponentDSL
class DSLBuilder : ComponentBuilder() {

    @ComponentDSL
    fun text(value: String, builder: TextComponentBuilder.() -> Unit = {}) {
        children += TextComponentBuilder(value).apply(builder).build()
    }

    @ComponentDSL
    fun translation(key: String, with: TranslationComponentBuilder.() -> Unit = {}) {
        children += TranslationComponentBuilder(key).apply(with).build()
    }

    @ComponentDSL
    fun keybind(keybind: String, builder: KeybindComponentBuilder.() -> Unit = {}) {
        children += KeybindComponentBuilder(keybind).apply(builder).build()
    }

    // TODO: Figure out how to make this work, because I haven't a clue at the moment
    fun build(): Component {
        return TextComponentBuilder("").build()
    }
}