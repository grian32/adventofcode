package me.grian.year2015.day16

import me.grian.Day
import me.grian.util.getInputText

class Day16 : Day {
    override val input: String
        get() = getInputText(2015, 16)

    override fun partOne(): String {
        val aunts = parseInput()

        for (i in aunts) {
            if (i.indices.all { i.getOrNull(it) == null || i[it] == GOOD_AUNT[it] }) {
                return (aunts.indexOf(i) + 1).toString()
            }
        }

        return ""
    }

    // fairly ehh soln here but alternative would be checking every property individually which is also kinda meh..
    override fun partTwo(): String {
        val aunts = parseInput()

        for (i in aunts) {
            // cats = 1; trees = 7 & pomeranians = 3; goldfish = 6;
            val cats = i.getOrNull(1)
            val trees = i.getOrNull(7)
            val pomeranians = i.getOrNull(3)
            val goldfish = i.getOrNull(6)

            val otherIndexes = listOf(0, 2, 4, 5, 8, 9)

            if (
                (cats == null || cats > GOOD_AUNT[1]) &&
                (trees == null || trees > GOOD_AUNT[7]) &&
                (pomeranians == null || pomeranians < GOOD_AUNT[3]) &&
                (goldfish == null || goldfish < GOOD_AUNT[6]) &&
                otherIndexes.all { i.getOrNull(it) == null || i[it] == GOOD_AUNT[it] }
            ) return (aunts.indexOf(i) + 1).toString()
        }

        return ""
    }

    private fun parseInput(): List<List<Int?>> {
        val lst: MutableList<List<Int?>> = mutableListOf()

        for (i in input.lines()) {
            val data = i.split(": ").drop(1).map { it.split(", ") }.flatten()
            val map = data.chunked(2).associate { it[0] to it[1].toInt() }

            lst.add(
                listOf(
                    map["children"],
                    map["cats"],
                    map["samoyeds"],
                    map["pomeranians"],
                    map["akitas"],
                    map["vizslas"],
                    map["goldfish"],
                    map["trees"],
                    map["cars"],
                    map["perfumes"]
                )
            )
        }

        return lst
    }

    companion object {
        val GOOD_AUNT = listOf(
            3,
            7,
            2,
            3,
            0,
            0,
            5,
            3,
            2,
            1
        )
    }
}