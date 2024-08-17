package me.grian.year2023.day2

import me.grian.Day
import me.grian.util.getInputText

class Day2 : Day {
    override val input: String = getInputText(2)

    override fun partOne(): String {
        val parsedInput = parse()
        var sum = 0

        parsedInput.forEachIndexed { idx, allShowings ->
            val filter = allShowings.filter {
                it.amount > 12 && it.color == "red"
                        || it.amount > 13 && it.color == "green"
                        || it.amount > 14 && it.color == "blue"
            }

            if (filter.isEmpty()) sum += idx + 1
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        TODO("Not yet implemented")
    }

    private fun parse(): List<List<CubeSet>> {
        val lines = input.lines()
        /*
            game 1 = List ( List (3 blue, 1 red), List (4 blue, 5 green) )
            each game is a member of the mutable list
            the individual lists of sets are then flattened to one list as it's easier to work with
         */
        val sets: MutableList<List<CubeSet>> = mutableListOf()

        lines.forEach { line ->
            val (_, data) = line.split(": ")

            sets.add(
                data
                    .split("; ")
                    .map {
                        it.split(", ")
                            .map(CubeSet::fromString)
                    }.flatten()
            )
        }



        return sets.toList()
    }
}