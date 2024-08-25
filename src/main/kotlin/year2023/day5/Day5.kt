package me.grian.year2023.day5

import me.grian.Day
import me.grian.util.getInputText

class Day5 : Day {
    override val input: String = getInputText(2023, 5)

    override fun partOne(): String {
        val almanac = parseInput()
        val seeds = almanac.seeds.toMutableList()

        applyAlmanacRange(almanac.seedToSoil, seeds)
        applyAlmanacRange(almanac.soilToFertilizer, seeds)
        applyAlmanacRange(almanac.fertilizerToWater, seeds)
        applyAlmanacRange(almanac.waterToLight, seeds)
        applyAlmanacRange(almanac.lightToTemperature, seeds)
        applyAlmanacRange(almanac.temperatureToHumidity, seeds)
        applyAlmanacRange(almanac.humidityToLocation, seeds)


        return seeds.min().toString()
    }


    override fun partTwo(): String {
        val almanac = parseInputP2()
        val seeds = almanac.seeds.toMutableList()
        // the first issue i need to solve is: figure out how to store non consecutive numbers in low mem
        // speed??

        applyAlmanacRange(almanac.seedToSoil, seeds)
        applyAlmanacRange(almanac.soilToFertilizer, seeds)
        applyAlmanacRange(almanac.fertilizerToWater, seeds)
        applyAlmanacRange(almanac.waterToLight, seeds)
        applyAlmanacRange(almanac.lightToTemperature, seeds)
        applyAlmanacRange(almanac.temperatureToHumidity, seeds)
        applyAlmanacRange(almanac.humidityToLocation, seeds)


        return seeds.min().toString()
    }

    private fun parseInput(): Almanac {
        val seeds = input.lines()[0]
            .split(": ")[1]
            .split(" ")
            .filter(String::isNotEmpty)
            .map(String::toLong)

        val allMaps = input
            .split("\n\n")
            .drop(1)

        return Almanac(
            seeds,
            parseAlmanacRange(allMaps[0]),
            parseAlmanacRange(allMaps[1]),
            parseAlmanacRange(allMaps[2]),
            parseAlmanacRange(allMaps[3]),
            parseAlmanacRange(allMaps[4]),
            parseAlmanacRange(allMaps[5]),
            parseAlmanacRange(allMaps[6])
        )
    }

    private fun parseInputP2(): AlmanacP2 {
        val seeds = input.lines()[0]
            .split(": ")[1]
            .split(" ")
            .filter(String::isNotEmpty)
            .map(String::toLong)
            .chunked(2)
            .map {
                (it[0]..(it[0] + it[1]))
            }
            .flatten()

        val allMaps = input
            .split("\n\n")
            .drop(1)

        return AlmanacP2(
            seeds,
            parseAlmanacRange(allMaps[0]),
            parseAlmanacRange(allMaps[1]),
            parseAlmanacRange(allMaps[2]),
            parseAlmanacRange(allMaps[3]),
            parseAlmanacRange(allMaps[4]),
            parseAlmanacRange(allMaps[5]),
            parseAlmanacRange(allMaps[6])
        )
    }


    private fun parseAlmanacRange(str: String): List<AlmanacRange> {
        val parsedMap = str
            .split("\n")
            .drop(1)
            .map { it
                .split(" ")
                .map(String::toLong)
            }

        val rangeList = mutableListOf<AlmanacRange>()

        parsedMap.forEach {
            rangeList.add(
                AlmanacRange(
                    it[0]..<(it[0] + it[2]),
                    it[1]..<(it[1] + it[2])
                )
            )
        }

        return rangeList
    }

    private fun applyAlmanacRange(ranges: List<AlmanacRange>, seeds: MutableList<Long>) {
        val seedChanged = MutableList(seeds.size) { false }

        for (idx in seeds.indices) {
            for ((destinationRange, sourceRange) in ranges) {
                if (seedChanged[idx]) break

                val offset = destinationRange.first - sourceRange.first

                if (sourceRange.contains(seeds[idx])) {
                    seeds[idx] = seeds[idx] + offset
                    seedChanged[idx] = true
                }
            }
        }
    }
}