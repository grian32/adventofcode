package me.grian.year2023.day5

data class Almanac(
    val seeds: List<Long>,
    val seedToSoil: List<AlmanacRange>,
    val soilToFertilizer: List<AlmanacRange>,
    val fertilizerToWater: List<AlmanacRange>,
    val waterToLight: List<AlmanacRange>,
    val lightToTemperature: List<AlmanacRange>,
    val temperatureToHumidity: List<AlmanacRange>,
    val humidityToLocation: List<AlmanacRange>,
)
