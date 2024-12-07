package me.grian.year2024.day7

import me.grian.Day
import me.grian.util.getInputText
import me.grian.util.permute

class Day7 : Day {
    override val input: String = getInputText(2024, 7)

    override fun partOne(): String {
        val equations = parseInput()
        val operators = listOf("+", "*")

        val goodNumbers: MutableSet<Long> = mutableSetOf()

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
        }

        return goodNumbers.sum().toString()
    }

    // really slow bruteforce soln but it works
    override fun partTwo(): String {
        val equations = parseInput()
        val operators = listOf("+", "*", "||")

        val goodNumbers: MutableSet<Long> = mutableSetOf()

        equations.forEach { (finalValue, numbers) ->
            val equationStrings = getEquationStrings(numbers, operators)

            equationStrings.forEach { equation ->
                var currentString = equation[0]
                (0..equation.lastIndex - 2 step 2).forEach {
                    val op = equation[it + 1]
                    val nextString = equation[it + 2]
                    val nextLong = nextString.toLong()

                    when (op) {
                        "+" -> {
                            // this is really ass, but you have to deal with the concat stuff somehow
                            // cant even do a currentLong because then i'd have to update it too
                            currentString = (currentString.toLong() + nextLong).toString()
                        }
                        "*" -> {
                            currentString = (currentString.toLong() * nextLong).toString()
                        }
                        "||" -> {
                            currentString += nextString
                        }
                    }
                }

                if (currentString.toLong() == finalValue) goodNumbers.add(currentString.toLong())
            }
        }

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
            val list = numbers.zip(perm).map { it.toList().map { i -> i.toString() } }.flatten().toMutableList()
            list.add(numbers.last().toString())

            equationStrings.add(list)
        }

        return equationStrings
    }
}