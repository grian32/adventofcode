package me.grian.year2015.day13

import me.grian.Day
import me.grian.util.WeightedGraph
import me.grian.util.getInputText
import me.grian.util.permuteUnique
import kotlin.math.max

class Day13 : Day {
    override val input: String
        get() = getInputText(2015, 13)

    override fun partOne(): String = findHighestHappiness(parseInput()).toString()

    override fun partTwo(): String {
        val graph = parseInput()

        for (i in graph.getAllNodes()) {
            graph.add(i, "Me", 0)
            graph.add("Me", i, 0)
        }

        return findHighestHappiness(graph).toString()
    }

    private fun findHighestHappiness(graph: WeightedGraph<String>): Int {
        val allPermutations = graph.getAllKeys().toList().permuteUnique()
        var sum = Int.MIN_VALUE

        for (perm in allPermutations) {
            var currentHappiness = 0

            for (i in 0..<perm.lastIndex) {
                currentHappiness += graph.getWeight(perm[i], perm[i+1])!!
                currentHappiness += graph.getWeight(perm[i+1], perm[i])!!
            }

            currentHappiness += graph.getWeight(perm.last(), perm.first())!!
            currentHappiness += graph.getWeight(perm.first(), perm.last())!!

            sum = max(sum, currentHappiness)
        }

        return sum
    }
    private fun parseInput(): WeightedGraph<String> {
        val graph: WeightedGraph<String> = WeightedGraph()

        for (i in input.lines()) {
            val firstSplit = i.split(" happiness units by sitting next to ")
            val nameGain = firstSplit[0].split(" would ")

            val gain = if (nameGain[1].contains("gain")) {
                nameGain[1].split(" ").last().toInt()
            } else {
                -nameGain[1].split(" ").last().toInt()
            }

            graph.add(nameGain[0], firstSplit[1].removeSuffix("."), gain)
        }

        return graph
    }
}