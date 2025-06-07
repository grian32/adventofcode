package me.grian.year2015.day12

import me.grian.Day
import me.grian.util.getInputText

class Day12 : Day {
    override val input: String
        get() = getInputText(2015, 12)

    override fun partOne(): String = input.getSumOfNumbers().toString()

    override fun partTwo(): String {
        val filteredString = ""

        // todo filter out objects containing red

        return filteredString.getSumOfNumbers().toString()
    }

    private fun String.getSumOfNumbers(): Int {
        var sum = 0

        var currentNum = ""

        for (i in this) {
            if (i.isDigit() || i == '-') {
                currentNum += i
            } else {
                sum += currentNum.toIntOrNull() ?: 0
                currentNum = ""
            }
        }

        return sum
    }
}