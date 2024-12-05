package me.grian.year2024.day5

import me.grian.Day
import me.grian.util.getInputText
import me.grian.util.middle
import me.grian.util.swap

class Day5 : Day {
    override val input: String = getInputText(2024, 5)

    override fun partOne(): String {
        val (rules, updates) = parseInput()

        val sum = updates.sumOf {
            processUpdate(rules, it)
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        val (rules, updates) = parseInput()

        val badUpdates = updates.filter {
            processUpdate(rules, it) == 0
        }

        val sum = badUpdates.sumOf { update ->
            val mutableUpdate = update.toMutableList()

            // basically a bubble sort lol
            mutableUpdate.indices.forEach { idx ->
                repeat(mutableUpdate.size - idx + 1) { _ ->
                    val s = mutableUpdate[idx]
                    val before = mutableUpdate.take(idx)
                    val after = mutableUpdate.takeLast(mutableUpdate.size - idx - 1)

                    before.forEach { beforeString ->
                        val beforeIdx = mutableUpdate.indexOf(beforeString)

                        if (!rules.contains("$beforeString|$s")) {
                            mutableUpdate.swap(beforeIdx, idx)

                            val afterSwap = processUpdate(rules, mutableUpdate)
                            if (afterSwap != 0) return@sumOf afterSwap
                        }
                    }

                    after.forEach { afterString ->
                        val afterIdx = mutableUpdate.indexOf(afterString)

                        if (!rules.contains("$s|$afterString")) {
                            mutableUpdate.swap(afterIdx, idx)

                            val afterSwap = processUpdate(rules, mutableUpdate)
                            if (afterSwap != 0) return@sumOf afterSwap
                        }
                    }
                }
            }

            return@sumOf 0
        }

        return sum.toString()
    }

    // Returns the middle number or 0 if the update is bad
    private fun processUpdate(rules: List<String>, update: List<String>): Int {
        update.forEachIndexed { idx, s ->
            val before = update.take(idx)
            val after = update.takeLast(update.size - idx - 1)

            before.forEach { beforeString ->
                if (!rules.contains("$beforeString|$s")) return 0
            }

            after.forEach { afterString ->
                if (!rules.contains("$s|$afterString")) return 0
            }
        }

        return update.middle().toInt()
    }

    private fun parseInput(): Pair<List<String>, List<List<String>>> {
        val (rules, updates) = input.split("\n\n")

        val processedUpdates = updates.split("\n").map { it.split(",").filter(String::isNotBlank) }

        return rules.split("\n") to processedUpdates
    }
}