package xyz.meowing.knit.api.utils

/*
 * Some of the methods have been authored by Noamm9
 */
object StringUtils {
    private val spaceRegex = Regex("\\s")
    private val spaceCapsRegex1 = Regex("(?<=[a-z])(?=[A-Z])")
    private val spaceCapsRegex2 = Regex("(?<=[A-Z])(?=[A-Z][a-z])")
    private val romanValues = mapOf(
        'M' to 1000,
        'D' to 500,
        'C' to 100,
        'L' to 50,
        'X' to 10,
        'V' to 5,
        'I' to 1
    )

    @JvmStatic
    fun String.decodeRoman(): Int {
        var result = 0
        var prevValue = 0
        for (i in lastIndex downTo 0) {
            val currentValue = romanValues[uppercase()[i]] ?: 0
            result += if (currentValue < prevValue) -currentValue else currentValue
            prevValue = currentValue
        }
        return result
    }

    @JvmStatic
    fun String.startsWithOneOf(vararg prefixes: String): Boolean {
        return prefixes.any { startsWith(it) }
    }

    @JvmStatic
    fun String.endsWithOneOf(vararg suffixes: String): Boolean {
        return suffixes.any { endsWith(it) }
    }

    @JvmStatic
    fun String.containsAny(vararg values: String): Boolean {
        return values.any { contains(it) }
    }

    @JvmStatic
    fun String.containsAll(vararg values: String): Boolean {
        return values.all { contains(it) }
    }

    @JvmStatic
    fun String.spaceCaps(): String {
        return replace(spaceCapsRegex1, " ")
            .replace(spaceCapsRegex2, " ")
            .trim()
    }

    @JvmStatic
    fun String.toKebabCase(): String {
        return spaceCaps().lowercase().replace(" ", "-")
    }

    @JvmStatic
    fun String.toSnakeCase(): String {
        return spaceCaps().lowercase().replace(" ", "_")
    }

    @JvmStatic
    fun String.toCamelCase(): String {
        return spaceCaps().split(" ")
            .mapIndexed { i, s -> if (i == 0) s.lowercase() else s.capitalize() }
            .joinToString("")
    }

    @JvmStatic
    fun String.toPascalCase(): String {
        return spaceCaps().split(" ")
            .joinToString("") { it.capitalize() }
    }

    @JvmStatic
    fun String.remove(vararg patterns: String): String {
        return patterns.fold(this) { acc, s -> acc.replace(s, "") }
    }

    @JvmStatic
    fun String.remove(vararg patterns: Regex): String {
        return patterns.fold(this) { acc, r -> acc.replace(r, "") }
    }

    @JvmStatic
    fun String.removeSpace(): String {
        return replace(spaceRegex, "")
    }

    @JvmStatic
    fun String.removeWhitespace(): String {
        return filterNot { it.isWhitespace() }
    }

    @JvmStatic
    fun String.truncate(maxLength: Int, suffix: String = "..."): String {
        return if (length <= maxLength) this else take(maxLength - suffix.length) + suffix
    }

    @JvmStatic
    fun String.countOccurrences(substring: String): Int {
        return windowed(substring.length) { if (it == substring) 1 else 0 }.sum()
    }

    @JvmStatic
    fun String.wrapWith(prefix: String, suffix: String = prefix): String {
        return "$prefix$this$suffix"
    }

    @JvmStatic
    fun Regex.matchAndRun(text: String, func: (List<String>) -> Unit) {
        find(text)?.destructured?.let { match ->
            val values = match.toList()
            try { func(values) } catch (_: Exception) {}
        }
    }
}