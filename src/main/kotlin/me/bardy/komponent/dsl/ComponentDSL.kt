@file:Suppress("unused")
package me.bardy.komponent.dsl

import me.bardy.komponent.Component
import me.bardy.komponent.Score

@DslMarker
annotation class ComponentDSL

@ComponentDSL
inline fun textComponent(text: String, builder: TextComponentBuilder.() -> Unit = {}): Component {
    return TextComponentBuilder(text).apply(builder).build()
}

@ComponentDSL
inline fun translationComponent(key: String, builder: TranslationComponentBuilder.() -> Unit = {}): Component {
    return TranslationComponentBuilder(key).apply(builder).build()
}

@ComponentDSL
inline fun keybindComponent(keybind: String, builder: KeybindComponentBuilder.() -> Unit = {}): Component {
    return KeybindComponentBuilder(keybind).apply(builder).build()
}

@ComponentDSL
inline fun scoreComponent(score: Score, builder: ScoreComponentBuilder.() -> Unit = {}): Component {
    return ScoreComponentBuilder(score).apply(builder).build()
}