@file:Suppress("UNUSED", "UNCHECKED_CAST")

package xyz.meowing.knit.api.command

import xyz.meowing.knit.api.command.nodes.Executable
import xyz.meowing.knit.api.command.nodes.LiteralNode
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal

/**
 * # Commodore
 *
 * A multi-version command library that simplifies the creation of command trees.
 * It leverages powerful Kotlin DSL features and reflection to easily build
 * complex command structures, streamlining the use of [Mojang's Brigadier](https://github.com/Mojang/brigadier).
 *
 * The command tree is constructed from a root [LiteralNode], with subsequent branches
 * formed by additional nodes.
 * The tree must always end in an [Executable][xyz.meowing.knit.api.command.nodes.Executable],
 * which serves as an exit point for a command.
 */
open class Commodore(private val root: LiteralNode) {

    constructor(
        vararg name: String,
        block: LiteralNode.() -> Unit
    ) : this(LiteralNode(name[0], name.drop(1))) {
        root.block()
    }

    constructor(
        vararg name: String
    ) : this(LiteralNode(name[0], name.drop(1)))

    /**
     * DSL access to the root node for object-style definitions.
     */
    fun runs(function: Function<Unit>) = root.runs(function)

    fun runs(block: () -> Unit) = root.runs(block)

    fun literal(string: String, block: LiteralNode.() -> Unit = {}): LiteralNode {
        return root.literal(string, block)
    }

    fun literal(vararg names: String, block: LiteralNode.() -> Unit = {}): LiteralNode {
        return root.literal(*names, block = block)
    }

    fun executable(block: Executable.() -> Unit): Executable {
        return root.executable(block)
    }

    /**
     * Registers this command to a dispatcher.
     *
     * For versions that support Brigadier (>=1.13), register the command to a dispatcher using:
     * ```kotlin
     * command.register(dispatcher)
     * ```
     *
     * For legacy versions, simply call the function and specify an error callback:
     * ```kotlin
     * command.register { problem: String, cause: LiteralNode ->
     *     println("$problem")
     * }
     * ```
     */
    fun register(dispatcher: CommandDispatcher<*>) {
        for (node in root.children) {
            node.build(root)
        }
        (dispatcher as CommandDispatcher<Any?>).register(root.builder)
        val rootCommand = root.builder.build()
        for (alias in root.aliases) {
            val aliasBuilder = literal<Any?>(alias)
            if (rootCommand.command != null) aliasBuilder.executes(rootCommand.command)
            aliasBuilder.redirect(rootCommand)
            dispatcher.register(aliasBuilder)
        }
    }
}