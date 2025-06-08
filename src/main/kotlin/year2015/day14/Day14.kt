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
        val horses = parseInput()
        val timesRan = 2503

        val chars = ('a'..'z').toList()
        val map = mutableMapOf<Char, Pair<Horse,Int>>()

        for (i in horses.indices) {
            map[chars[i]] = horses[i] to 0
        }

        repeat(timesRan) {
            for ((name, scoredHorse) in map) {
                val (horse, score) = scoredHorse

                val cycleTime = horse.travelTime + horse.restTime
                val secondInCycle = it % cycleTime

                if (secondInCycle < horse.travelTime) {
                    horse.distanceTravelled += horse.travelSpeed
                }
            }

            val highestDistance = map.maxOfOrNull { it.value.first.distanceTravelled }

            map.replaceAll { c, pair ->
                if (pair.first.distanceTravelled == highestDistance) {
                    pair.first to (pair.second + 1)
                } else {
                    pair
                }
            }
        }

        return map.maxOfOrNull { it.value.second }.toString()
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