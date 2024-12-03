package me.grian.year2024.day3

import me.grian.Day
import me.grian.util.getInputText
import me.grian.util.mul

class Day3 : Day {
    override val input: String = getInputText(2024, 3)

    override fun partOne(): String {
        val mulRegexp = Regex("mul\\((\\d{1,3},\\d{1,3})\\)")
        val mergedInput = input.replace("\n", "")

        val sum = mulRegexp.findAll(mergedInput).sumOf {
            it.groupValues[1].split(",").map(String::toInt).mul()
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        val mulRegexp = Regex("mul\\((\\d{1,3},\\d{1,3})\\)")
        val disabledRegexp = Regex("don't\\(\\).+?(?=do\\(\\))")

        val enabledInput = input.replace("\n", "").replace(disabledRegexp, "")

        val sum = mulRegexp.findAll(enabledInput).sumOf {
            it.groupValues[1].split(",").map(String::toInt).mul()
        }

        return sum.toString()
    }
}