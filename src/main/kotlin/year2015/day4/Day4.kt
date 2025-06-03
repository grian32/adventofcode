package me.grian.year2015.day4

import me.grian.Day
import me.grian.util.getInputText
import java.security.MessageDigest

class Day4 : Day {
    override val input: String
        get() = getInputText(2015, 4)

    override fun partOne(): String {
        var md5 = ""
        var i = 0

        while (!md5.startsWith("00000")) {
            i++
            md5 = md5("$input$i")
        }

        return i.toString()
    }

    override fun partTwo(): String {
        var md5 = ""
        var i = 0

        while (!md5.startsWith("000000")) {
            i++
            md5 = md5("$input$i")
        }

        return i.toString()
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}