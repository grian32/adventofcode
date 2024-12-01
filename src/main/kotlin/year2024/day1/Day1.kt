package me.grian.year2024.day1

import me.grian.Day
import me.grian.util.getInputText
import kotlin.math.abs

class Day1 : Day {
    override val input: String = getInputText(2024,1)

    override fun partOne(): String {
        val (leftList, rightList) = parseInput()

        val sortedLeft = leftList.sorted()
        val sortedRight = rightList.sorted()

        val sum = sortedLeft.zip(sortedRight).sumOf {
            abs(it.first - it.second)
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        val (leftList, rightList) = parseInput()

        val countMap = rightList.groupingBy { it }.eachCount()

        val sum = leftList.sumOf {
            it * countMap.getOrDefault(it, 0)
        }

        return sum.toString()
    }

    private fun parseInput(): Pair<List<Int>, List<Int>> = input.lines().map {
        val (left, right) = it.split("   ").map(String::toInt)
        left to right
    }.unzip()
}