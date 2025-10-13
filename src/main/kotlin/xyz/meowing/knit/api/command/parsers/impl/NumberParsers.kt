package xyz.meowing.knit.api.command.parsers.impl

import xyz.meowing.knit.api.command.parsers.CommandParser
import com.mojang.brigadier.StringReader

/**
 * @author Stivais
 */
object LongParser : CommandParser<Long> {
    override fun parse(reader: StringReader): Long {
        return reader.readLong()
    }
}

/**
 * @author Stivais
 */
object IntParser : CommandParser<Int> {
    override fun parse(reader: StringReader): Int {
        return reader.readInt()
    }
}

/**
 * @author Stivais
 */
object FloatParser : CommandParser<Float> {
    override fun parse(reader: StringReader): Float {
        return reader.readFloat()
    }
}

/**
 * @author Stivais
 */
object DoubleParser : CommandParser<Double> {
    override fun parse(reader: StringReader): Double {
        return reader.readDouble()
    }
}