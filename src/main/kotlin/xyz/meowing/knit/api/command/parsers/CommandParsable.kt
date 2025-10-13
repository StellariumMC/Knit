package xyz.meowing.knit.api.command.parsers

/**
 * # CommandParsable
 *
 * This annotation indicates that a parser should attempt to be generated, based on the primary constructor parameters.
 * If a parameter doesn't have a parser it will fail to generate.
 * @author Stivais
 */
annotation class CommandParsable // todo: suggestions as a parameter in array
