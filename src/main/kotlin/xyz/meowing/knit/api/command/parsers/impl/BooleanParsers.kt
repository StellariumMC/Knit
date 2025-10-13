package xyz.meowing.knit.api.command.parsers.impl

import xyz.meowing.knit.api.command.parsers.CommandParser
import com.mojang.brigadier.StringReader

/**
 * @author Stivais
 */
object BooleanParser : CommandParser<Boolean> {
    override fun parse(reader: StringReader): Boolean {
        return reader.readBoolean()
    }
}
