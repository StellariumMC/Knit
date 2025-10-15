@file:Suppress("UNCHECKED_CAST")

package xyz.meowing.knit.api.command.parsers

import com.mojang.brigadier.StringReader
import xyz.meowing.knit.api.command.parsers.impl.BooleanParser
import xyz.meowing.knit.api.command.parsers.impl.DoubleParser
import xyz.meowing.knit.api.command.parsers.impl.FloatParser
import xyz.meowing.knit.api.command.parsers.impl.FunctionParser
import xyz.meowing.knit.api.command.parsers.impl.GreedyStringParser
import xyz.meowing.knit.api.command.parsers.impl.IntParser
import xyz.meowing.knit.api.command.parsers.impl.LongParser
import xyz.meowing.knit.api.command.parsers.impl.StringParser
import xyz.meowing.knit.api.command.utils.GreedyString
import xyz.meowing.knit.api.command.utils.SyntaxException
import java.util.*

/**
 * # CommandParser
 *
 * This interface is responsible for reading a string input and parsing it into a valid output
 * that can be used for an [Executable][xyz.meowing.knit.api.command.nodes.Executable].
 *
 * Classes with pre-defined parsers include: [String], [GreedyString], [Int], [Long], [Float],
 * [Double], and [Boolean].
 *
 * @author Stivais
 */
interface CommandParser<T> {
    
    /**
     * Uses Brigadiers [StringReader] to parse an output.
     */
    fun parse(reader: StringReader): T

    /**
     * Used to provide a collection of suggested strings for command completion.
     */
    fun suggestions(): Collection<String> = Collections.emptyList()

    // im not quite sure what it does in modern versions so ill keep this here just in case it has a use
    fun examples(): Collection<String> = Collections.emptyList()

    companion object {
        /**
         * Map of available parsers.
         *
         * Key == Class, Value == Parser for the class.
         */
        private val parsers: MutableMap<Any?, CommandParser<*>> = mutableMapOf(
            String::class.java to StringParser,
            GreedyString::class.java to GreedyStringParser,
            Integer::class.java to IntParser, Int::class.java to IntParser,
            java.lang.Long::class.java to LongParser, Long::class.java to LongParser,
            java.lang.Float::class.java to FloatParser, Float::class.java to FloatParser,
            java.lang.Double::class.java to DoubleParser, Double::class.java to DoubleParser,
            java.lang.Boolean::class.java to BooleanParser, Boolean::class.java to BooleanParser
        )

        /**
         * Registers a [parser builder][CommandParser] to the [custom parsers map][parsers]].
         *
         * @param clazz the key for the map
         * @param builder the parser builder
         */
        fun <T> registerParser(clazz: Class<T>, builder: CommandParser<T>) {
            parsers[clazz] = builder
        }

        /**
         * Retrieves a parser for the given class type.
         *
         * If a parser for the class is already registered, it returns the existing parser.
         * If no parser is found but the class is annotated with [CommandParsable],
         * it attempts to create a parser using the primary constructor of the class.
         * All parameters of the constructor must also be parsable.
         *
         * @throws IllegalArgumentException if the class annotated with [CommandParsable]
         * does not have a primary constructor or if its parameters are not parsable
         */
        fun getParser(type: Class<*>): CommandParser<*>? {
            parsers[type]?.let { return it }

            val annotation = type.getAnnotation(CommandParsable::class.java)
            if (annotation != null) {
                if (type.isEnum) {
                    val parser = object : CommandParser<Any> {
                        val enumMap = buildMap<String, Any> {
                            for (constant in type.enumConstants) {
                                val str = constant.toString().lowercase().replace("_", " ")
                                put(str, constant)
                            }
                        }
                        override fun parse(reader: StringReader): Any {
                            val builder = StringBuilder()
                            while (reader.canRead()) {
                                builder.append(reader.read())
                                val value = enumMap[builder.toString()]
                                if (value != null) return value
                            }
                            throw SyntaxException(
                                //#if MC == 1.8.9
                                "Invalid argument (valid arguments: ${enumMap.keys.joinToString(separator = ", ") { it }}) for command"
                                //#else
                                //$$ "Invalid argument for command"
                                //#endif
                            )
                        }

                        override fun suggestions(): Collection<String> {
                            return enumMap.keys
                        }
                    }
                    registerParser(
                        type as Class<Any?>,
                        parser as CommandParser<Any?>
                    )
                    return parser
                } else {
                    try {
                        val constructors = type.constructors
                        if (constructors.isEmpty()) throw IllegalArgumentException("No constructor found")

                        val constructor = constructors[0]
                        val parser = FunctionParser { args: List<Any?> ->
                            constructor.newInstance(*args.toTypedArray())
                        }
                        registerParser(
                            type as Class<Any?>,
                            parser as CommandParser<Any?>
                        )
                        return parser
                    } catch (e: Exception) {
                        throw IllegalArgumentException("Failed to create parser for $type", e)
                    }
                }
            }
            return null
        }
    }
}