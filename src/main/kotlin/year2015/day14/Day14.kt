package me.grian.year2015.day14

import me.grian.Day
import me.grian.util.getInputText
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

class Day14 : Day {
    override val input: String
        get() = getInputText(2015, 14)

    override fun partOne(): String {
        val horses = parseInput()
        val timeRan = 2503
        var maxDistance = Int.MIN_VALUE

        for (i in horses) {
            var dist = 0

            // add full cycles
            dist += (timeRan/(i.travelTime + i.restTime)) * i.travelTime * i.travelSpeed
            // add remainder
            dist += min(timeRan % (i.travelTime + i.restTime), i.travelTime) * i.travelSpeed

            maxDistance = max(maxDistance, dist)
        }

        return maxDistance.toString()
    }

    override fun partTwo(): String {
        TODO("Not yet implemented")
    }

    private fun parseInput(): List<Horse> {
        val list: MutableList<Horse> = mutableListOf()

        for (i in input.lines()) {
            val firstSplit = i.split(" seconds, but then must rest for ")
            val splitTravel = firstSplit[0].split(" ")

            val restTime = firstSplit[1].removeSuffix(" seconds.").toInt()
            val travelTime = splitTravel.last().toInt()
            val travelSpeed = splitTravel[3].toInt()

            list.add(Horse(
                restTime,
                travelTime,
                travelSpeed
            ))
        }

        return list
    }
}