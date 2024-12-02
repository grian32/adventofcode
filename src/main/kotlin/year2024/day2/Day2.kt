package me.grian.year2024.day2

import me.grian.Day
import me.grian.util.dropAt
import me.grian.util.getInputText
import me.grian.util.dropAt
import kotlin.math.abs

class Day2 : Day {
    override val input: String = getInputText(2024,2)

    override fun partOne(): String = parseInput().count(::checkRow).toString()

    override fun partTwo(): String = parseInput().count(::checkRowP2).toString()

    private fun checkRow(row: List<Int>): Boolean {
        val differences = row.zipWithNext { a, b -> a - b}

        if (differences.any { it == 0 || abs(it) > 3 }) return false
        if (differences.any { it > 0 } && differences.any { it < 0 }) return false

        return true
    }

    private fun checkRowP2(row: List<Int>): Boolean =
        checkRow(row) || row.indices.any {
            checkRow(row.dropAt(it))
        }


    private fun parseInput(): List<List<Int>> =
        input.lines().map {
            it.split(" ").map(String::toInt)
        }
}