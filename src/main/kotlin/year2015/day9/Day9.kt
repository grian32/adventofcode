package me.grian.year2015.day9

import me.grian.Day
import me.grian.util.WeightedGraph
import me.grian.util.getInputText
import me.grian.util.permuteUnique
import kotlin.math.min

class Day9 : Day {
    override val input: String
        get() = getInputText(2015, 9)

    override fun partOne(): String {
        val graph = parseInput()
        val allPermutations = graph.getAllNodes().toList().permuteUnique()
        var smallest = Int.MAX_VALUE

        for (perm in allPermutations) {
            var totalDistance = 0

            for (i in 0..<perm.lastIndex) {
                totalDistance += graph.getWeight(perm[i] , perm[i+1]) ?: 0
            }

            smallest = min(totalDistance, smallest)
        }

        return smallest.toString()
    }

    override fun partTwo(): String {
        TODO("Not yet implemented")
    }

    private fun parseInput(): WeightedGraph<String> {
        val graph = WeightedGraph<String>()

        for (i in input.lines()) {
            val split = i.split(" = ")
            val nodes = split[0].split(" to ")

            graph.add(nodes[0], nodes[1], split[1].toInt())
            graph.add(nodes[1], nodes[0], split[1].toInt())
        }

        return graph
    }
}