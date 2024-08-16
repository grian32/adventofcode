package me.grian.year2023

import me.grian.Day
import me.grian.util.getInputText

class Day1 : Day {
    override val input: String = getInputText(1)

    override fun partOne(): String {
        val lines = input.lines()
        var sum = 0

        lines.forEach { line ->
            val digits = line.filter { it.isDigit() }
            // can't add chars
            sum += "${digits.first()}${digits.last()}".toInt()
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        var text = input
        var sum = 0

        val overlapMap = mapOf(
            "twone" to "twoone",
            "oneight" to "oneeight",
            "threeight" to "threeeight",
            "fiveight" to "fiveeight",
            "nineight" to "nineeight",
            "sevenine" to "sevennine",
            "eightwo" to "eighttwo",
            "eighthree" to "eightthree"
        )

        val numberMap = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )

        overlapMap.forEach {
            text = text.replace(it.key, it.value)
        }

        numberMap.forEach {
            text = text.replace(it.key, it.value.toString())
        }

        text.lines().forEach { line ->
            val digits = line.filter { it.isDigit() }
            // can't add chars
            sum += "${digits.first()}${digits.last()}".toInt()
        }

        return sum.toString()
    }
}