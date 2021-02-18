# Komponent

An easy to use, fast and lightweight Minecraft chat component library, written in Kotlin

## Dependency
Komponent is now on Maven Central. You can retrieve it like this:

- Gradle
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'me.bardy:komponent'
}
```

## Usage
Komponent has it's own DSL, for easy component creation, in a very idiomatic way.
```kotlin
textComponent("I am text!") {
    color = NamedColor.BLACK
    formatting {
        bold = true
        italic = true
        underlined = false
        strikethrough = true
        obfuscated = false
    }
    insertion = "I am insertion text!"
    clickEvent = openURL("https://example.com")
    hoverEvent = showText("I am hover text!")

    translation("i.am.a.translation.key") {
        color = NamedColor.WHITE
    }

    keybind("key.forward") {
        color = Color.fromHex("#ff6600")
    }
}
```