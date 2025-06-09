package me.grian.year2015.day15

import me.grian.Day
import me.grian.util.getInputText
import me.grian.util.permuteUnique
import kotlin.math.max

class Day15 : Day {
    override val input: String
        get() = getInputText(2015, 15)

    override fun partOne(): String = common(parseInput(), false).toString()

    override fun partTwo(): String = common(parseInput(), true).toString()

    // kinda ugly bruteforce but runs quickly enough and the problem is weird anyway, think better would be some sort
    // of binary search in those ranges but cba..
    private fun common(input: List<Ingredient>, calories: Boolean): Int {
        var highest = Int.MIN_VALUE

        for (i in 0..100) {
            for (j in 0..(100 - i)) {
                for (k in 0..(100 - i - j)) {
                    val l = 100 - i - j - k

                    fun getEach(lst: List<Int>) = lst[0] * i + lst[1] * j + lst[2] * k + lst[3] * l

                    val score = getEach(input.map { it.capacity }).coerceAtLeast(0) *
                            getEach(input.map { it.flavor }).coerceAtLeast(0) *
                            getEach(input.map { it.texture }).coerceAtLeast(0) *
                            getEach(input.map { it.durability }).coerceAtLeast(0)

                    if (calories) {
                        val calorieAmount = getEach(input.map { it.calories })

                        if (calorieAmount != 500) continue
                    }

                    highest = max(score, highest)
                }
            }
        }

        return highest
    }

    private fun parseInput(): List<Ingredient> {
        val lst: MutableList<Ingredient> = mutableListOf()

        for (i in input.lines()) {
            val data = i.split(": ")[1].split(", ").map { it.split(" ") }

            lst.add(
                Ingredient(
                    data[0][1].toInt(),
                    data[1][1].toInt(),
                    data[2][1].toInt(),
                    data[3][1].toInt(),
                    data[4][1].toInt()
                )
            )
        }

        return lst
    }
}