package me.grian.year2023.day6

import me.grian.Day
import me.grian.util.getInputText

class Day6 : Day {
    override val input: String = getInputText(2023, 6)

    override fun partOne(): String {
        val inputMap = parseInputP1()
        var allWinAmounts = 0

        inputMap.forEach { (time, distance) ->
            var wins = 0
            // holding for 0 or max means loss always
            (1..<time).forEach {
                val distanceReached = (time - it) * it
                if (distanceReached > distance) wins++
            }

            if (allWinAmounts == 0) allWinAmounts = wins else allWinAmounts *= wins
        }

        return allWinAmounts.toString()
    }

    override fun partTwo(): String {
        val input = parseInputP2()
        var wins = 0

        (1..<input.first).forEach {
            val distanceReached = (input.first - it) * it
            if (distanceReached > input.second) wins++
        }

        return wins.toString()
    }

    private fun parseInputP1(): Map<Int, Int> {
        val map = mutableMapOf<Int, Int>()

        val time = input
            .lines()[0]
            .split(": ")[1]
            .split(" ")
            .filter(String::isNotEmpty)
            .map(String::toInt)

        val distance = input
            .lines()[1]
            .split(": ")[1]
            .split(" ")
            .filter(String::isNotEmpty)
            .map(String::toInt)

        time.forEachIndexed { idx, i ->
            map[i] = distance[idx]
        }

        return map
    }

    private fun parseInputP2(): Pair<Long, Long> {
        val time = input
            .lines()[0]
            .split(": ")[1]
            .replace(" ", "")
            .toLong()

        val distance = input
            .lines()[1]
            .split(": ")[1]
            .replace(" ", "")
            .toLong()

        return Pair(time, distance)
    }
}