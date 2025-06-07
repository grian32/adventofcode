package me.grian.year2015.day11

import me.grian.Day
import me.grian.util.containsAny
import me.grian.util.getInputText

class Day11 : Day {
    override val input: String
        get() = getInputText(2015, 11)

    override fun partOne(): String {
        var password = input

        while (!password.isValidPassword()) {
            password = incrementPassword(password.toCharArray(), password.lastIndex).joinToString("")
        }

        return password
    }

    override fun partTwo(): String {
        val firstPassword = partOne()
        // force increment once otherwise it's automatically valid
        var secondPassword = incrementPassword(firstPassword.toCharArray(), firstPassword.lastIndex).joinToString("")

        while (!secondPassword.isValidPassword()) {
            secondPassword = incrementPassword(secondPassword.toCharArray(), secondPassword.lastIndex).joinToString("")
        }

        return secondPassword
    }

    private fun incrementPassword(charArr: CharArray, place: Int): CharArray {
        val char = charArr[place]

        if (char == 'z') {
            charArr[place] = 'a'
            return incrementPassword(charArr, place - 1)
        } else {
            charArr[place] = char + 1
            return charArr
        }
    }

    private fun String.isValidPassword(): Boolean =
        !containsAny(listOf("i", "o", "l")) &&
        hasDoubleLetters() &&
        hasThreeConsecutiveLetters()

    private fun String.hasDoubleLetters(): Boolean {
        var counter = 0
        var idx = 0

        // tried chunked & it was like 10x slower lol
        while (idx < 8) {
            if (get(idx) == getOrNull(idx + 1)) {
                counter++
                idx++
            }
            idx++
        }

        return counter >= 2
    }

    private fun String.hasThreeConsecutiveLetters(): Boolean {
        for (i in indices) {
            val currCode = get(i).code
            if (getOrNull(i + 1)?.code == currCode + 1 && getOrNull(i + 2)?.code == currCode + 2) return true
        }

        return false
    }
}