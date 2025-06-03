package me.grian.year2015.day3

import me.grian.Day
import me.grian.util.Point
import me.grian.util.getInputText

class Day3 : Day {
    override val input: String
        get() = getInputText(2015, 3)

    override fun partOne(): String {
        val grid = mutableMapOf<Pair<Int, Int>, Boolean>()
        var x = 0
        var y = 0

        grid[0 to 0] = true

        for (i in input) {
            when (i) {
                '>' -> x++
                '<' -> x--
                'v' -> y--
                '^' -> y++
            }

            grid[x to y] = true
        }

        return grid.size.toString()
    }

    override fun partTwo(): String {
        val grid = mutableMapOf<Pair<Int, Int>, Boolean>()
        var x = 0
        var y = 0
        var x2 = 0
        var y2 = 0

        grid[0 to 0] = true

        input.forEachIndexed { index, c ->
            if (index % 2 == 0) {
                when (c) {
                    '>' -> x++
                    '<' -> x--
                    'v' -> y--
                    '^' -> y++
                }
            } else {
                when (c) {
                    '>' -> x2++
                    '<' -> x2--
                    'v' -> y2--
                    '^' -> y2++
                }
            }
            grid[x to y] = true
            grid[x2 to y2] = true
        }

        return grid.size.toString()
    }
}