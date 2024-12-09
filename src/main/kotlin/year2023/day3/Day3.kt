package me.grian.year2023.day3

import me.grian.Day
import me.grian.util.getInputText
import me.grian.util.hasIndex

class Day3 : Day {
    override val input: String = getInputText(2023,3)

    private val symbolRegex = Regex("[^a-zA-Z0-9.\\n]")

    private val oneOffsets = listOf(
        -1 to -1..1,
        0 to -1..-1,
        0 to 1..1,
        1 to -1..1
    )

    private val twoOffsets = listOf(
        -1 to -2..1, // all above
        0 to -2..-2, // left, for typing reasons
        0 to 1..1, // right, for typing reasons
        1 to -2..1 // all below
    )

    private val threeOffsets = listOf(
        -1 to -3..1,
        0 to -3..-3,
        0 to 1..1,
        1 to -3..1,
    )

    private val oneRegex = Regex("(?<!\\d)\\d(?!\\d)")
    private val twoRegex = Regex("(?<!\\d)\\d{2}(?!\\d)")
    private val threeRegex = Regex("(?<!\\d)\\d{3}(?!\\d)")

    override fun partOne(): String {
        val lines = input.lines()

        var sum = 0

        lines.forEachIndexed { x, line ->
            val oneMatches = oneRegex.findAll(line).toList()
            val twoMatches = twoRegex.findAll(line).toList()
            val threeMatches = threeRegex.findAll(line).toList()

            sum += processMatchesP1(oneMatches, oneOffsets, lines, x)
            sum += processMatchesP1(twoMatches, twoOffsets, lines, x)
            sum += processMatchesP1(threeMatches, threeOffsets, lines, x)
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        val lines = input.lines()
        /**
         * regex for stars, check each character around it and if its a number, check to the left and right and see
         * which side is a number, then go one further on that side and if its a dot write to the gear's digits, if
         * its a number, make sure its surrounded by symbols on either side and add it as the full number
         *
         * this is to prevent situations like the following:
         * 123 123
         *    *
         *    *   384
         * where you would register 3 top left, then move two spots to the right and get number "3 1"
         * and in the below case where it would get registered as 38 84 384 if you didnt check for symbols surrounding
         *
         */

        /**
         * find all stars, note down coordinates add to a map as key with x*y with empty list
         * get all numbers, where they border a start @ x, y, add it to that key's list
         */

        val starRegex = Regex("\\*")
        val starMap: MutableMap<Pair<Int, Int>, MutableList<Int>> = mutableMapOf()
        var totalPower = 0

        lines.forEachIndexed { x, line ->
            val starMatches = starRegex.findAll(line).toList()


            starMatches.forEach {
                val y = it.range.last
                starMap[x to y] = mutableListOf()
            }
        }

        lines.forEachIndexed { x, line ->
            val oneMatches = oneRegex.findAll(line).toList()
            val twoMatches = twoRegex.findAll(line).toList()
            val threeMatches = threeRegex.findAll(line).toList()

            processNumberMatchesP2(oneMatches, oneOffsets, lines, starMap, x)
            processNumberMatchesP2(twoMatches, twoOffsets, lines, starMap, x)
            processNumberMatchesP2(threeMatches, threeOffsets, lines, starMap, x)
        }

        starMap.filter { it.value.size == 2 }.forEach { (_, parts) ->
            totalPower += parts[0] * parts[1]
        }

        return totalPower.toString()
    }

    private fun processMatchesP1(matches: List<MatchResult>, offsets: List<Pair<Int, IntRange>>, lines: List<String>, x: Int): Int {
        var sum = 0

        matches.forEach { match ->
            val y = match.range.last

            offsets.forEach { offset ->
                for (i in offset.second) {
                    if (!lines.map { it.toCharArray().toList() }.hasIndex(x + offset.first, y + i)) continue

                    if (lines[x + offset.first][y + i].toString().matches(symbolRegex)) sum += match.value.toInt()
                }
            }
        }

        return sum
    }

    private fun processNumberMatchesP2(
        matches: List<MatchResult>,
        offsets: List<Pair<Int, IntRange>>,
        lines: List<String>,
        starMap: MutableMap<Pair<Int, Int>, MutableList<Int>>,
        x: Int
    ): MutableMap<Pair<Int, Int>, MutableList<Int>> {
        matches.forEach { match ->
            val y = match.range.last

            offsets.forEach { offset ->
                for (i in offset.second) {
                    if (!lines.map { it.toCharArray().toList() }.hasIndex(x + offset.first, y + i)) continue
                    if (lines[x + offset.first][y + i] == '*') {
                        starMap[(x + offset.first) to (y + i)]!!.add(match.value.toInt())
                    }
                }
            }
        }

        return starMap
    }
}