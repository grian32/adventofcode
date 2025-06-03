package me.grian.year2015.day2

import me.grian.Day
import me.grian.util.getInputText
import me.grian.util.minVar
import kotlin.math.min

class Day2 : Day {
    override val input: String
        get() = getInputText(2015, 2)

    override fun partOne(): String {
        var sum = 0

        for (i in input.lines()) {
            val (ls, ws, hs) = i.split("x")
            val l = ls.toInt()
            val w = ws.toInt()
            val h = hs.toInt()

            sum += 2*l*w + 2*w*h + 2*h*l + minVar(l*w, w*h, h*l)
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        var sum = 0

        for (i in input.lines()) {
            val (ls, ws, hs) = i.split("x")
            val l = ls.toInt()
            val w = ws.toInt()
            val h = hs.toInt()

            sum += minVar(2*l+2*w, 2*w+2*h, 2*h+2*l) + l*w*h
        }

        return sum.toString()
    }

}