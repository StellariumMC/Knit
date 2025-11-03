# Knit

A utility library for Minecraft modding that provides cross-version compatibility APIs. Knit is a fork of [Deftu's OmniCore](https://github.com/Deftu/OmniCore) library, designed to work across multiple Minecraft versions and mod loaders.

## Features

- **Command system** - Powerful DSL for building commands using Brigadier
- **Event bus** - Simple and efficient event system
- **Input handling** - Unified keyboard and mouse APIs
- **Text components** - Easy text formatting and manipulation
- **Screen API** - Simplified GUI creation
- **Utility functions** - Number formatting, string operations, and more

## Versions
![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.8.9-forge?label=Knit%201.8.9%20Forge)

![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.20.1-fabric?label=Knit%201.20.1%20Fabric)
![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.20.1-forge?label=Knit%201.20.1%20Forge)

![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.21.5-fabric?label=Knit%201.21.5%20Fabric)
![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.21.5-forge?label=Knit%201.21.5%20Forge)
![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.21.5-neoforge?label=Knit%201.21.5%20NeoForge)

![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.21.8-fabric?label=Knit%201.21.8%20Fabric)
![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.21.8-forge?label=Knit%201.21.8%20Forge)
![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.21.8-neoforge?label=Knit%201.21.8%20NeoForge)

![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.21.9-fabric?label=Knit%201.21.9%20Fabric)
![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.21.9-forge?label=Knit%201.21.9%20Forge)
![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.21.9-neoforge?label=Knit%201.21.9%20NeoForge)

![Maven Central Version](https://img.shields.io/maven-central/v/xyz.meowing/knit-1.21.10-fabric?label=Knit%201.21.10%20Fabric)

## Installation

Adding Knit to your project:

### Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    // Replace VERSION and MINECRAFT_VERSION with your target versions
    modImplementation("xyz.meowing:knit-MINECRAFT_VERSION-LOADER:VERSION")
}
```

For example, for Minecraft 1.20.1 on Fabric:
```kotlin
modImplementation("xyz.meowing:knit-1.20.1-fabric:110")
```

## Usage Examples

### Commands

Create commands with an intuitive DSL:

```kotlin
object Command : Commodore("mycommand", "alias") {
    init {
        literal("param") {
            runs { name: String ->
                KnitChat.fakeMessage("Hello, $name!")
            }
        }

        literal("param2") {
            runs { x: Float, y: Float, z: Float ->
                println("$x, $y, $z")
            }
        }
        
        runs { 
            println("no parameters passed!")
        }
    }
}

// Register the command
command.register(dispatcher)
```

### Events

Register event handlers with priority support:

```kotlin
val eventBus = EventBus()

class MyCustomEvent : CancellableEvent()

eventBus.register<MyCustomEvent> { event ->
    println("Event fired: $event")
    if (someCondition) {
        event.cancel() // If using CancellableEvent
    }
}

// Post events
eventBus.post(MyCustomEvent())
```

### Text Components

Build formatted chat messages easily:

```kotlin
val message = KnitText
    .literal("Hello world!")
    .onHover("!dlrow olleH")
    .onClick(ClickEvent.RunCommand("/explode"))
    .toVanilla()

KnitChat.fakeMessage(message)

val message = buildText {
    text("Click here") {
        blue()
        bold()
        underlined()
        onClick("https://example.com")
        onHover("Opens example.com")
    }
    text(" to visit our website!") {
        gray()
    }
}

KnitChat.fakeMessage(message.toVanilla())
```

### Screen Creation

Create custom screens:

```kotlin
class MyScreen : KnitScreen("My Custom Screen") {
    override fun onInitGui() {
        // Initialize your screen
    }
    
    override fun onRender(mouseX: Int, mouseY: Int, deltaTicks: Float) {
        // Render your screen
    }
    
    override fun onMouseClick(mouseX: Int, mouseY: Int, button: Int) {
        // Handle mouse clicks
    }
    
    override fun onMouseMove(mouseX: Int, mouseY: Int) {
        // cool stuff
    }
    
    override fun onKeyType(typedChar: Char, keyCode: Int, scanCode: Int) {
        // Handle keyboard input
    }
}
```

### Utilities

Format numbers and strings:

```kotlin
val abbreviated = 1_500_000.abbreviate() // "1.5M"
val withCommas = 1500000.formatWithCommas() // "1,500,000"
val camelCase = "hello world".toCamelCase() // "helloWorld"
```

### Input Handling

Check keyboard and mouse state:

```kotlin
if (KnitKeys.KEY_SPACE.isPressed) {
    println("Space bar is pressed!")
}

if (KnitMouseButtons.LEFT.isPressed) {
    val x = KnitMouse.Scaled.x
    val y = KnitMouse.Scaled.y
    println("Left mouse button pressed at ($x, $y)")
}
```

## License

Knit is licensed under the GNU General Public License v3.0. See [LICENSE](LICENSE) for more information.

## Credits

- **Deftu** - Original OmniCore library
- **Stivais** - Commodore command system
- **StellariumMC** - Knit fork and maintenance

The command system (`xyz.meowing.knit.api.command` package) contains modified code from the [Commodore](https://github.com/Stivais/Commodore) library by Stivais.