package me.grian.year2015.day1

import me.grian.Day
import me.grian.util.getInputText

class Day1 : Day {
    override val input: String
        get() = getInputText(2015, 1)

    override fun partOne(): String {
        var sum = 0

        for (i in input) {
            if (i == '(') sum++ else sum--
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        var sum = 0

        input.forEachIndexed { index, c ->
            if (c == '(') sum++ else sum--
            if (sum == -1) return (index + 1).toString()
        }

        return "0"
    }

}