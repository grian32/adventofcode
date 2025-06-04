package me.grian.year2015.day6

import me.grian.Day
import me.grian.util.getInputText

class Day6 : Day {
    override val input: String
        get() = getInputText(2015, 6)

    override fun partOne(): String {
        val grid = MutableList(1000) { MutableList(1000) { false } }

        for (i in input.lines()) {
            var instruction = ""
            var firstCoord: Pair<Int, Int> = 0 to 0
            var secondCoord: Pair<Int, Int> = 0 to 0

            when {
                i.startsWith("toggle") -> {
                    val split = i.split(" ")

                    instruction = "toggle"
                    firstCoord = split[1].coordsToInts()
                    secondCoord = split[3].coordsToInts()
                }
                i.startsWith("turn") -> {
                    val split = i.split(" ")

                    instruction = if (split[1] == "on") "turn on" else "turn off"
                    firstCoord = split[2].coordsToInts()
                    secondCoord = split[4].coordsToInts()
                }
            }


            val xRange = firstCoord.first..secondCoord.first
            val yRange = firstCoord.second..secondCoord.second

            for (x in xRange) {
                for (y in yRange) {
                    when (instruction) {
                        "toggle" -> grid[x][y] = !grid[x][y]
                        "turn off" -> grid[x][y] = false
                        "turn on" -> grid[x][y] = true
                        else -> {}
                    }
                }
            }
        }

        return grid.sumOf { line -> line.count { it } }.toString()
    }

    override fun partTwo(): String {
        val grid = MutableList(1000) { MutableList(1000) { 0 } }

        for (i in input.lines()) {
            var instruction = ""
            var firstCoord: Pair<Int, Int> = 0 to 0
            var secondCoord: Pair<Int, Int> = 0 to 0

            when {
                i.startsWith("toggle") -> {
                    val split = i.split(" ")

                    instruction = "toggle"
                    firstCoord = split[1].coordsToInts()
                    secondCoord = split[3].coordsToInts()
                }
                i.startsWith("turn") -> {
                    val split = i.split(" ")

                    instruction = if (split[1] == "on") "turn on" else "turn off"
                    firstCoord = split[2].coordsToInts()
                    secondCoord = split[4].coordsToInts()
                }
            }


            val xRange = firstCoord.first..secondCoord.first
            val yRange = firstCoord.second..secondCoord.second

            for (x in xRange) {
                for (y in yRange) {
                    when (instruction) {
                        "toggle" -> grid[x][y] = grid[x][y] + 2
                        "turn off" -> grid[x][y] = (grid[x][y] - 1).coerceAtLeast(0)
                        "turn on" -> grid[x][y]++
                        else -> {}
                    }
                }
            }
        }

        return grid.flatten().sum().toString()
    }

    private fun String.coordsToInts(): Pair<Int, Int> {
        val splitStr = split(",").map(String::trim).map(String::toInt)

        return splitStr[0] to splitStr[1]
    }


}