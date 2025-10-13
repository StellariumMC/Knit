package xyz.meowing.knit.api.command.parsers.impl

import xyz.meowing.knit.api.command.parsers.CommandParser
import com.mojang.brigadier.StringReader
import xyz.meowing.knit.api.command.utils.GreedyString

/**
 * @author Stivais
 */
object GreedyStringParser : CommandParser<GreedyString> {
    override fun parse(reader: StringReader): GreedyString {
        val string = reader.remaining
        reader.cursor = reader.totalLength
        return GreedyString(string)
    }
}

/**
 * @author Stivais
 */
object StringParser : CommandParser<String> {
    override fun parse(reader: StringReader): String {
        return reader.readString()
    }
}