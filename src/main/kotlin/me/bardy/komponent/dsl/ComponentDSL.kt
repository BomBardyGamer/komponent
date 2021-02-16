package me.bardy.komponent.dsl

import me.bardy.komponent.Component

@DslMarker
annotation class ComponentDSL


@ComponentDSL
inline fun component(builder: RootComponentBuilder.() -> Unit): Component {
    return RootComponentBuilder().apply(builder).build()
}

@ComponentDSL
class RootComponentBuilder : ComponentBuilder() {

    private var rootComponent: Component? = null

    @ComponentDSL
    fun text(value: String, builder: TextComponentBuilder.() -> Unit = {}) {
        if (rootComponent != null) throw rootComponentSet()
        rootComponent = TextComponentBuilder(value).apply(builder).build()
    }

    @ComponentDSL
    fun translation(key: String, with: TranslationComponentBuilder.() -> Unit = {}) {
        if (rootComponent != null) throw rootComponentSet()
        rootComponent = TranslationComponentBuilder(key).apply(with).build()
    }

    @ComponentDSL
    fun keybind(keybind: String, builder: KeybindComponentBuilder.() -> Unit = {}) {
        if (rootComponent != null) throw rootComponentSet()
        rootComponent = KeybindComponentBuilder(keybind).apply(builder).build()
    }

    private fun rootComponentSet() = UnsupportedOperationException("Root component has already been set!")

    // TODO: Figure out how to make this work, because I haven't a clue at the moment
    fun build(): Component {
        if (rootComponent == null) throw UnsupportedOperationException("Root component must be set!")
        return requireNotNull(rootComponent)
    }
}