# Komponent

An easy to use, fast and lightweight Minecraft chat component library, written in Kotlin

## Dependency
W.I.P

## Usage
Komponent has it's own DSL, for easy component creation, in a very idiomatic way.
```kotlin
component {
    text("I am text!") {
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

        children {
            translation("i.am.a.translation.key") {
                color = NamedColor.WHITE
            }
            
            keybind("key.forward") {
                color = Color.fromHex("#ff6600")
            }
        }
    }
}
```
NOTE: Due to the way the chat component API works, you can only use a single component type for the root component.

The following will throw an `UnsupportedOperationException`:
```kotlin
component {
    text("I am text!")
    text("I am some other text!") // Not allowed! Will throw an UnsupportedOperationException
}
```