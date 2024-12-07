package me.grian.year2024.day7

import me.grian.Day
import me.grian.util.getInputText
import me.grian.util.permute
import kotlin.time.measureTime

class Day7 : Day {
    override val input: String = getInputText(2024, 7)

    override fun partOne(): String {
        val equations = parseInput()
        val operators = listOf("+", "*")

        val goodNumbers: MutableSet<Long> = mutableSetOf()
        val time = measureTime {
        // cant use sum of because there are duplicates so i need a set
        equations.forEach { (finalValue, numbers) ->
            val equationStrings = getEquationStrings(numbers, operators)

            equationStrings.forEach { equation ->
                var currentNumber = equation[0].toLong()
                (0..equation.lastIndex - 2 step 2).forEach {
                    val op = equation[it + 1]
                    val nextNumber = equation[it + 2].toLong()

                    if (op == "+") currentNumber += nextNumber else currentNumber *= nextNumber
                }

                if (currentNumber == finalValue) goodNumbers.add(currentNumber)
            }
        }}

        println("p1: $time")

        return goodNumbers.sum().toString()
    }

    override fun partTwo(): String {
        val equations = parseInput()
        val operators = listOf("+", "*", "||")

        val goodNumbers: MutableSet<Long> = mutableSetOf()

        val time = measureTime {
        equations.forEach { (finalValue, numbers) ->
            val equationStrings = getEquationStrings(numbers, operators)

            equationStrings.forEach { equation ->
                var currentNumber = equation[0]
                (0..equation.lastIndex - 2 step 2).forEach {
                    val op = equation[it + 1]
                    val nextNumber = equation[it + 2]

                    when (op) {
                        "+" -> {
                            // this is really ass, but you have to deal with the concat stuff somehow
                            currentNumber = (currentNumber.toLong() + nextNumber.toLong()).toString()
                        }
                        "*" -> {
                            currentNumber = (currentNumber.toLong() * nextNumber.toLong()).toString()
                        }
                        "||" -> {
                            currentNumber += nextNumber
                        }
                    }
                }

                if (currentNumber.toLong() == finalValue) goodNumbers.add(currentNumber.toLong())
            }
        }}

        println("p2: $time")

        return goodNumbers.sum().toString()
    }

    private fun parseInput(): Map<Long, List<Int>> {
        return buildMap {
            input.lines().forEach { line ->
                val (final, list) = line.split(": ")
                val finalProcessed = final.toLong()
                val listProcessed = list.split(" ").map(String::toInt)

                put(finalProcessed, listProcessed)
            }
        }
    }

    private fun getEquationStrings(numbers: List<Int>, operators: List<String>): List<List<String>> {
        val operatorsNeeded = numbers.lastIndex
        val perms = operators.permute(operatorsNeeded)

        val equationStrings: MutableList<List<String>> = mutableListOf()

        perms.forEach { perm ->
            val list = numbers.zip(perm).map { it.toList().map { ot -> ot.toString() } }.flatten().toMutableList()
            list.add(numbers.last().toString())

            equationStrings.add(list)
        }

        return equationStrings
    }
}