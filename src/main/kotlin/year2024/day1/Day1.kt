package me.grian.year2024.day1

import me.grian.Day
import me.grian.util.getInputText
import kotlin.math.abs

class Day1 : Day {
    override val input: String = getInputText(2024,1)

    override fun partOne(): String {
        val (leftList, rightList) = parseInput()
        var sum = 0

        val sortedLeft = leftList.sorted()
        val sortedRight = rightList.sorted()

        sortedLeft.zip(sortedRight).forEach {
            sum += abs(it.first - it.second)
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        val (leftList, rightList) = parseInput()
        var sum = 0

        val countMap = rightList.groupingBy { it }.eachCount()

        leftList.forEach {
            sum += it * countMap.getOrDefault(it, 0)
        }

        return sum.toString()
    }

    private fun parseInput(): Pair<List<Int>, List<Int>> {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        input.lines().forEach {
            val (leftNum, rightNum) = it.split("   ").map(String::toInt)

            leftList.add(leftNum)
            rightList.add(rightNum)
        }

        return leftList to rightList
    }
}