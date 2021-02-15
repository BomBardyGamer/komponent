package me.bardy.komponent.dsl

import me.bardy.komponent.Component

@DslMarker
annotation class ComponentDSL


@ComponentDSL
inline fun component(builder: DSLBuilder.() -> Unit): Component {
    return DSLBuilder().apply(builder).build()
}

@ComponentDSL
class DSLBuilder {

    private val components = mutableListOf<Component>()

    @ComponentDSL
    fun text(value: String, builder: TextComponentBuilder.() -> Unit = {}) {
        components += TextComponentBuilder(value).apply(builder).build()
    }

    @ComponentDSL
    fun translation(key: String, with: TranslationComponentBuilder.() -> Unit = {}) {
        components += TranslationComponentBuilder(key).apply(with).build()
    }

    @ComponentDSL
    fun keybind(keybind: String, builder: KeybindComponentBuilder.() -> Unit = {}) {
        components += KeybindComponentBuilder(keybind).apply(builder).build()
    }

    fun build(): Component {
        return components.fold(TextComponentBuilder("")) { acc: TextComponentBuilder, element: Component ->
            acc.apply { children { element } }
        }.build()
    }
}