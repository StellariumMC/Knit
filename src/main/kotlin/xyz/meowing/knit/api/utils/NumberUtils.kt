package xyz.meowing.knit.api.utils

import java.util.*
import kotlin.math.*

object NumberUtils {
    private val suffixes = TreeMap<Long, String>().apply {
        this[1000L] = "k"
        this[1000000L] = "m"
        this[1000000000L] = "b"
        this[1000000000000L] = "t"
        this[1000000000000000L] = "p"
        this[1000000000000000000L] = "e"
    }

    private val romanSymbols = TreeMap(
        mapOf(
            1000 to "M",
            900 to "CM",
            500 to "D",
            400 to "CD",
            100 to "C",
            90 to "XC",
            50 to "L",
            40 to "XL",
            10 to "X",
            9 to "IX",
            5 to "V",
            4 to "IV",
            1 to "I"
        )
    )

    @JvmStatic
    fun format(value: Number): String {
        val longValue = value.toLong()
        when {
            longValue == Long.MIN_VALUE -> return format(Long.MIN_VALUE + 1)
            longValue < 0L -> return "-${format(-longValue)}"
            longValue < 1000L -> return longValue.toString()
        }

        val (divideBy, suffix) = suffixes.floorEntry(longValue)
        val truncated = longValue / (divideBy / 10)
        val hasDecimal = truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()

        return if (hasDecimal) "${truncated / 10.0}$suffix" else "${truncated / 10}$suffix"
    }

    @JvmStatic
    fun format(value: String): String = format(value.filter { it.isDigit() }.toLong())

    @JvmStatic
    fun unformat(value: String): Long {
        val suffix = value.filter { !it.isDigit() }.lowercase()
        val num = value.filter { it.isDigit() }.toLongOrNull() ?: 0L
        return num * (suffixes.entries.find { it.value.lowercase() == suffix }?.key ?: 1L)
    }

    fun Number.abbreviate(): String {
        val num = this.toDouble().absoluteValue
        val sign = if (this.toDouble() < 0) "-" else ""
        val (divisor, suffix) = when {
            num >= 1_000_000_000_000 -> 1_000_000_000_000.0 to "T"
            num >= 1_000_000_000 -> 1_000_000_000.0 to "B"
            num >= 1_000_000 -> 1_000_000.0 to "M"
            num >= 1_000 -> 1_000.0 to "k"
            else -> return sign + "%.0f".format(Locale.US, num)
        }
        val value = num / divisor
        val formatted = if (value % 1.0 == 0.0) {
            value.toInt().toString()
        } else {
            val decimal = "%.1f".format(Locale.US, value)
            if (decimal.endsWith(".0")) decimal.dropLast(2) else decimal
        }
        return sign + formatted + suffix
    }

    fun Number.formatWithCommas(): String = "%,.0f".format(Locale.US, this.toDouble())

    fun Long.toDuration(short: Boolean = false): String {
        val seconds = this / 1000
        val days = seconds / 86400
        val hours = (seconds % 86400) / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60

        if (short) {
            return when {
                days > 0 -> "${days}d"
                hours > 0 -> "${hours}h"
                minutes > 0 -> "${minutes}m"
                else -> "${remainingSeconds}s"
            }
        }

        return buildString {
            if (days > 0) append("${days}d ")
            if (hours > 0) append("${hours}h ")
            if (minutes > 0) append("${minutes}m ")
            if (remainingSeconds > 0) append("${remainingSeconds}s")
        }.trimEnd()
    }

    fun Double.toFixed(precision: Int): String {
        if (this.isNaN()) return toString()
        val scale = 10.0.pow(precision).toInt()
        val rounded = (this * scale).roundToInt().toDouble() / scale
        val parts = rounded.toString().split(".")

        return if (parts.size == 2) {
            "${parts[0]}.${parts[1].padEnd(precision, '0')}"
        } else {
            "${parts[0]}.${"0".repeat(precision)}"
        }
    }

    fun Float.toFixed(precision: Int): String {
        val scale = 10.0.pow(precision).toInt()
        val rounded = (this * scale).roundToInt().toFloat() / scale
        val parts = rounded.toString().split(".")

        return if (parts.size == 2) {
            "${parts[0]}.${parts[1].padEnd(precision, '0')}"
        } else {
            "${parts[0]}.${"0".repeat(precision)}"
        }
    }

    fun String.toFixed(precision: Int): String {
        val number = toDoubleOrNull() ?: return this
        val scale = 10.0.pow(precision).toInt()
        val rounded = (number * scale).roundToInt().toDouble() / scale
        val parts = rounded.toString().split(".")

        return if (parts.size == 2) {
            "${parts[0]}.${parts[1].padEnd(precision, '0')}"
        } else {
            "${parts[0]}.${"0".repeat(precision)}"
        }
    }

    fun Number.addSuffix(): String {
        val long = this.toLong()
        if (long in 11..13) return "${this}th"

        return when (long % 10) {
            1L -> "${this}st"
            2L -> "${this}nd"
            3L -> "${this}rd"
            else -> "${this}th"
        }
    }

    fun String.romanToDecimal(): Int {
        var decimal = 0
        var lastNumber = 0
        val romanNumeral = this.uppercase()

        for (x in romanNumeral.length - 1 downTo 0) {
            val currentValue = when (romanNumeral[x]) {
                'M' -> 1000
                'D' -> 500
                'C' -> 100
                'L' -> 50
                'X' -> 10
                'V' -> 5
                'I' -> 1
                else -> 0
            }
            decimal += if (currentValue < lastNumber) -currentValue else currentValue
            lastNumber = currentValue
        }

        return decimal
    }

    fun Number.toRoman(): String {
        val int = this.toInt()
        require(int > 0) { "$this must be positive!" }
        val l = romanSymbols.floorKey(int)
        return if (int == l) romanSymbols[int]!! else romanSymbols[l] + (int - l).toRoman()
    }

    fun Number.clamp(min: Number, max: Number): Double {
        return this.toDouble().coerceIn(min.toDouble(), max.toDouble())
    }

    fun Number.percentage(total: Number): Double {
        return if (total.toDouble() == 0.0) 0.0 else (this.toDouble() / total.toDouble()) * 100
    }

    operator fun Number.div(number: Number): Double = this.toDouble() / number.toDouble()
    operator fun Number.times(number: Number): Double = this.toDouble() * number.toDouble()
    operator fun Number.minus(number: Number): Double = this.toDouble() - number.toDouble()
    operator fun Number.plus(number: Number): Double = this.toDouble() + number.toDouble()
}