package me.grian.year2015.day10

import me.grian.Day
import me.grian.util.getInputText

class Day10 : Day {
    override val input: String
        get() = getInputText(2015, 10)

    override fun partOne(): String = common(40).length.toString()

    // FIXME: slow as a dog, part 1 takes 10s, part 2 i left running while i was at gym for shits and giggles.. took 40 minutes lmao
    override fun partTwo(): String = common(50).length.toString()

    private fun common(count: Int): String {
        var currentSequence = input

        repeat(count) {
            currentSequence = currentSequence.convertDigits()
        }

        return currentSequence
    }

    private fun String.convertDigits(): String {
        if (toSet().size == 1) return "$length${get(0)}"

        var curr = get(0)
        var len = 1
        var str = ""

        for (i in 1..lastIndex) {
            if (curr == get(i)) {
                len++
            } else {
                str += "$len$curr"
                curr = get(i)
                len = 1
            }

            if (i == lastIndex) {
                str += "$len$curr"
            }
        }

        return str
    }
}