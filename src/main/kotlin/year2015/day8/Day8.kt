package me.grian.year2015.day8

import me.grian.Day
import me.grian.util.getInputText

class Day8 : Day {
    override val input: String
        get() = getInputText(2015, 8)

    override fun partOne(): String =
        input.lines().sumOf {
            it.length - (it.escaped().length - 2)
        }.toString()

    override fun partTwo(): String =
        input.lines().sumOf {
            it.unescaped().length - it.length
        }.toString()


    private fun String.escaped(): String = this
        .replace("\\\"", "\"") // single quote
        .replace("\\\\", "\\") // \\ -> \
        .replace(HEX_REGEX) {
            it.groups[1]!!.value.toInt(16).toChar().toString()
        }

    private fun String.unescaped(): String = this
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .let { "\"$it\"" }

    companion object {
        private val HEX_REGEX = Regex("\\\\x([0-9a-fA-F]{2})")
    }
}