package me.grian.year2015.day17

import me.grian.Day
import me.grian.util.combinations
import me.grian.util.getInputText

class Day17 : Day {
    override val input: String
        get() = getInputText(2015, 17)

    override fun partOne(): String {
        var sum = 0
        val input = parseInput()

        for (i in 0..input.size) {
            for (j in input.combinations(i)) {
                if (j.sum() == 150) sum++
            }
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        var sum = 0
        val input = parseInput()

        for (i in 0..input.size) {
            for (j in input.combinations(i)) {
                if (j.sum() == 150) sum++
            }

            if (sum > 0) return sum.toString()
        }

        return sum.toString()
    }

    fun parseInput() = input.split("\n").map { it.toInt() }
}