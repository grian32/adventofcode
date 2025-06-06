package me.grian.year2015.day10

import me.grian.Day
import me.grian.util.getInputText

class Day10 : Day {
    override val input: String
        get() = getInputText(2015, 10)

    override fun partOne(): String = common(40).size.toString()

    override fun partTwo(): String = common(50).size.toString()

    private fun common(count: Int): ByteArray {
        var currentSequence = input.toByteArray()

        repeat(count) {
            currentSequence = currentSequence.convertDigits()
        }

        return currentSequence
    }

    private fun ByteArray.convertDigits(): ByteArray {
        // maybe char arrays? see how that goes..
        var curr = get(0)
        var len = 1
        var byteArray = mutableListOf<Byte>()


        for (i in 1..lastIndex) {
            if (curr == get(i)) {
                len++
            } else {
                byteArray.addAll(len.toCharCode())
                byteArray.add(curr)
                curr = get(i)
                len = 1
            }

            if (i == lastIndex) {
                byteArray.addAll(len.toCharCode())
                byteArray.add(curr)
            }
        }

        return byteArray.toByteArray()
    }

    private fun Int.toCharCode(): List<Byte> {
        return toString().map { it.code }.map { it.toByte() }
    }
}