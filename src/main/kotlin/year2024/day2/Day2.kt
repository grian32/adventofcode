package me.grian.year2024.day2

import me.grian.Day
import me.grian.util.getInputText
import kotlin.math.abs

class Day2 : Day {
    override val input: String = getInputText(2024,2)

    override fun partOne(): String {
        val grid = parseInput()
        var safeReports = 0

        grid.forEachIndexed { idx, row ->
            val differences = mutableListOf<Int>()

            repeat(row.size - 1) {
                differences.add(grid[idx][it] - grid[idx][it + 1])
            }

            if (differences.any { it == 0 || abs(it) > 3 }) return@forEachIndexed
            if (differences.any { it > 0 } && differences.any { it < 0 }) return@forEachIndexed

            safeReports++
        }

        return safeReports.toString()
    }

    override fun partTwo(): String {
        val grid = parseInput()
        var safeReports = 0

        grid.forEach grid@ {
            if (checkRowP2(it)) {
                safeReports++
                return@grid
            }

            it.indices.forEach { idx ->
                val listWithoutLevel = it.toMutableList()
                listWithoutLevel.removeAt(idx)

                if (checkRowP2(listWithoutLevel)) {
                    safeReports++
                    return@grid
                }
            }
        }

        return safeReports.toString()
    }

    private fun checkRowP2(row: List<Int>): Boolean {
        val differences = mutableListOf<Int>()

        repeat(row.size - 1) {
            differences.add(row[it] - row[it + 1])
        }

        if (differences.any { it == 0 || abs(it) > 3 }) return false
        if (differences.any { it > 0 } && differences.any { it < 0 }) return false

        return true
    }

    private fun parseInput(): List<List<Int>> =
        input.lines().map {
            it.split(" ").map(String::toInt)
        }
}