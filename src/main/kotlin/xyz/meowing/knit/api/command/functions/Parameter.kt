package xyz.meowing.knit.api.command.functions

/**
 * Represents a parameter for a function.
 *
 * @see FunctionInvoker
 * @author Stivais
 */
data class Parameter<T>(val name: String, val type: Class<T>, val isNullable: Boolean)
