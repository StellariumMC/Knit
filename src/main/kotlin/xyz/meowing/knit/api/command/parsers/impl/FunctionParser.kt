package xyz.meowing.knit.api.command.parsers.impl

import xyz.meowing.knit.api.command.functions.FunctionInvoker
import xyz.meowing.knit.api.command.parsers.CommandParser
import xyz.meowing.knit.api.command.parsers.CommandParser.Companion.getParser
import com.mojang.brigadier.StringReader

/**
 * ## FunctionParser
 *
 * A parser that uses a provided function to process input parameters and output a value.
 * The function's parameters define the expected input types, and its result is returned
 * after processing the input.
 *
 * Example:
 * ```kotlin
 * // This command expects two float values. If they are the same, it throws an error.
 * // The function outputs a Pair<Float, Float> if valid.
 * parser { x: Float, y: Float ->
 *     if (x == y) throw SyntaxException("Values cannot be equal")
 *     Pair(x, y)
 * }
 * ```
 *
 * Unlike an [Executable][xyz.meowing.knit.api.command.nodes.Executable] function, the parameters cannot be optional.
 *
 * @author Stivais
 */
class FunctionParser<T> : CommandParser<T> {
    private val funInvoker: FunctionInvoker<T>?
    private val constructorInvoker: ((List<Any?>) -> T)?
    private val parsers: ArrayList<CommandParser<*>> = arrayListOf()

    constructor(function: Function<T>, vararg parameterTypes: Class<*>) {
        funInvoker = if (parameterTypes.isEmpty()) {
            FunctionInvoker.from(function)
        } else {
            FunctionInvoker.from(function, *parameterTypes)
        }
        constructorInvoker = null
        initParsers()
    }

    constructor(invoker: (List<Any?>) -> T) {
        funInvoker = null
        constructorInvoker = invoker

        val constructor = invoker.javaClass.enclosingClass?.constructors?.firstOrNull()
            ?: throw IllegalStateException("Cannot find constructor")

        for (param in constructor.parameters) {
            val parser = getParser(param.type)
                ?: throw IllegalStateException("No parser found for parameter: ${param.name}(type=${param.type})")
            parsers.add(parser)
        }

        require(parsers.isNotEmpty()) { "You need at least one parameter in the function for this parser." }
    }

    private fun initParsers() {
        for (parameter in funInvoker!!.parameters) {
            val parser = getParser(parameter.type)
                ?: throw IllegalStateException("No parser found for parameter: ${parameter.name}(type=${parameter.type})")
            parsers.add(parser)
        }
        require(parsers.isNotEmpty()) { "You need at least one parameter in the function for this parser." }
    }

    override fun parse(reader: StringReader): T {
        val arguments = mutableListOf<Any?>()
        for (parser in parsers) {
            reader.skipWhitespace()
            arguments.add(parser.parse(reader))
        }

        return if (funInvoker != null) {
            funInvoker.invoke(arguments)
        } else {
            constructorInvoker!!(arguments)
        }
    }
}