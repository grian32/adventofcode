package me.grian.year2023.day3

import me.grian.Day
import me.grian.util.getInputText
import me.grian.util.hasIndex

class Day3 : Day {
    override val input: String = getInputText(3)

    private val symbolRegex = Regex("[^a-zA-Z0-9.\\n]")

    override fun partOne(): String {
        val lines = input.lines()

        val oneRegex = Regex("(?<!\\d)\\d{1}(?!\\d)")
        val twoRegex = Regex("(?<!\\d)\\d{2}(?!\\d)")
        val threeRegex = Regex("(?<!\\d)\\d{3}(?!\\d)")

        var sum = 0

        val oneOffsets = listOf(
            -1 to -1..1,
            0 to -1..-1,
            0 to 1..1,
            1 to -1..1
        )

        val twoOffsets = listOf(
            -1 to -2..1, // all above
            0 to -2..-2, // left, for typing reasons
            0 to 1..1, // right, for typing reasons
            1 to -2..1 // all below
        )

        val threeOffsets = listOf(
            -1 to -3..1,
            0 to -3..-3,
            0 to 1..1,
            1 to -3..1,
        )

        lines.forEachIndexed { x, line ->
            val oneMatches = oneRegex.findAll(line).toList()
            val twoMatches = twoRegex.findAll(line).toList()
            val threeMatches = threeRegex.findAll(line).toList()

            sum += processMatches(oneMatches, oneOffsets, lines, x)
            sum += processMatches(twoMatches, twoOffsets, lines, x)
            sum += processMatches(threeMatches, threeOffsets, lines, x)
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        return ""
    }

    private fun processMatches(matches: List<MatchResult>, offsets: List<Pair<Int, IntRange>>, lines: List<String>, x: Int): Int {
        var sum = 0

        matches.forEach { match ->
            val y = match.range.last

            offsets.forEach { offset ->
                for (i in offset.second) {
                    if (!lines.hasIndex(x + offset.first, y + i)) continue

                    if (lines[x + offset.first][y + i].toString().matches(symbolRegex)) sum += match.value.toInt()
                }
            }
        }

        return sum
    }
}