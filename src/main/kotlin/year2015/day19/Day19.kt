package me.grian.year2015.day19

import me.grian.Day
import me.grian.util.getInputText

class Day19 : Day {
    override val input: String
        get() = getInputText(2015, 19)

    override fun partOne(): String {
        val (replacements, starter) = parseInput()
        val distinctMolecules = mutableSetOf<String>()

        for (i in replacements) {
            for (j in starter.indices) {
                if (starter.getOrNull(j + 1) == null) continue

                val endIdx = j + i.first.length

                if (starter.substring(j..<endIdx) == i.first) {
                    distinctMolecules.add(
                        starter.replaceRange(j..<endIdx, i.second)
                    )
                }
            }
        }

        return distinctMolecules.size.toString()
    }

    override fun partTwo(): String {
        var (replacements, end) = parseInput()
        var sum = 0

        while (end != "e") {
            for (i in replacements) {
                // literally going backwards through the string, so dumb lol, was more hail mary than anything, think
                // i got really lucky with the input however since i've seen other's solns that are literally the same
                // but they had to shuffle the the replacements for it to go through eventually..
                if (end.contains(i.second)) {
                    val idx = end.lastIndexOf(i.second)
                    end = end.replaceRange(idx..<idx+i.second.length, i.first)
                    sum++
                }
            }
        }

        return sum.toString()
    }

    // replacements, starter
    fun parseInput(): Pair<List<Pair<String, String>>, String> {
        val input = input.split("\n\n")
        val starter = input[1]

        val replacements = input[0]
            .split("\n")
            .map { it.split(" => ") }
            .map { it[0] to it[1] }

        return replacements to starter
    }
}