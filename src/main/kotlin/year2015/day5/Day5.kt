package me.grian.year2015.day5

import me.grian.Day
import me.grian.util.containsAny
import me.grian.util.getInputText

class Day5 : Day {
    override val input: String
        get() = getInputText(2015, 5)

    override fun partOne(): String {
        var sum = 0

        for (i in input.lines()) {
            if (i.containsAny(DISALLOWED_SUBSTRINGS) || !i.hasDuplicateConsecutiveLetters()) continue
            var vowelCount = 0

            for (c in i) {
                if (c in "aeiou") {
                    vowelCount++
                }
            }

            if (vowelCount > 2) sum++
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        var sum = 0

        for (i in input.lines()) {
            if (!i.hasDoubleDuplicateConsecutiveLetters() || !i.hasSeparatedDuplicateLetters()) continue
            sum++
        }

        return sum.toString()
    }

    private fun String.hasDuplicateConsecutiveLetters(): Boolean {
        for (i in windowed(2)) {
            if (i[0] == i[1]) {
                return true
            }
        }
        return false
    }

    private fun String.hasDoubleDuplicateConsecutiveLetters(): Boolean {
        for (i in 1..lastIndex) {
            val double = get(i-1).toString() + get(i)

            if (substring(i+1..lastIndex).contains(double)) return true
        }

        return false
    }

    private fun String.hasSeparatedDuplicateLetters(): Boolean {
        for (i in windowed(3)) {
            if (i.getOrNull(2) == null) continue
            if (i[0] == i[2]) return true
        }

        return false
    }

    companion object {
        val DISALLOWED_SUBSTRINGS = listOf(
            "ab",
            "cd",
            "pq",
            "xy"
        )
    }
}