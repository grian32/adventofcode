package me.grian.year2015.day12

import kotlinx.serialization.json.*
import me.grian.Day
import me.grian.util.getInputText

class Day12 : Day {
    override val input: String
        get() = getInputText(2015, 12)

    override fun partOne(): String = input.getSumOfNumbers().toString()

    override fun partTwo(): String {
        val filteredString = Json.encodeToString(Json.parseToJsonElement(input).process())

        return filteredString.getSumOfNumbers().toString()
    }

    private fun JsonElement.containsRed(): Boolean {
        return when (this) {
            is JsonObject -> values.any { it is JsonPrimitive && it.isString && it.content == "red" }
            is JsonArray -> any { it.containsRed() }
            is JsonPrimitive -> content.contains("red")
        }
    }

    private fun JsonElement.process(): JsonElement? {
        return when (this) {
            is JsonObject -> {
                if (containsRed()) return null

                val map = mutableMapOf<String, JsonElement>()

                for ((k, v) in this) {
                    val processed = v.process()

                    if (processed != null) map[k] = processed
                }

                if (map.isEmpty()) null else JsonObject(map)
            }
            is JsonArray -> {
                val filtered = mapNotNull { it.process() }
                if (filtered.isEmpty()) null else JsonArray(filtered)
            }
            else -> if (containsRed()) null else this
        }
    }

    private fun String.getSumOfNumbers(): Int {
        var sum = 0

        var currentNum = ""

        for (i in this) {
            if (i.isDigit() || i == '-') {
                currentNum += i
            } else {
                sum += currentNum.toIntOrNull() ?: 0
                currentNum = ""
            }
        }

        return sum
    }
}