package me.grian.year2015.day18

import me.grian.Day
import me.grian.util.getInputText

class Day18 : Day {
    override val input: String
        get() = getInputText(2015, 18)

    override fun partOne(): String {
        var grid = parseInput()

        repeat(100) {
            val newGrid = grid.map { it.toMutableList() }

            for (x in 0..grid.lastIndex) {
                for (y in 0..grid[0].lastIndex) {
                    val neighbours = VECTORS.mapNotNull {
                        grid.getOrNull(x + it.first)?.getOrNull(y + it.second)
                    }

                    val trueNeighbours = neighbours.filter { it }

                    val current = grid[x][y]

                    if (current && !(trueNeighbours.size == 2 || trueNeighbours.size == 3)) {
                        newGrid[x][y] = false
                    }

                    if (!current && trueNeighbours.size == 3) {
                        newGrid[x][y] = true
                    }
                }
            }

            grid = newGrid
        }

        return grid.sumOf { it.count { it } }.toString()
    }

    override fun partTwo(): String {
        var grid = parseInput()

        val lastIdxRow = grid[0].lastIndex

        val corners = listOf(
            0 to 0,
            0 to lastIdxRow,
            grid.lastIndex to 0,
            grid.lastIndex to lastIdxRow
        )

        for ((x, y) in corners) {
            grid[x][y] = true
        }

        repeat(100) {
            val newGrid = grid.map { it.toMutableList() }

            for (x in 0..grid.lastIndex) {
                for (y in 0..grid[0].lastIndex) {
                    if (x to y in corners) continue

                    val neighbours = VECTORS.mapNotNull {
                        grid.getOrNull(x + it.first)?.getOrNull(y + it.second)
                    }

                    val trueNeighbours = neighbours.filter { it }

                    val current = grid[x][y]

                    if (current && trueNeighbours.size !in 2..3) {
                        newGrid[x][y] = false
                    }

                    if (!current && trueNeighbours.size == 3) {
                        newGrid[x][y] = true
                    }
                }
            }
            grid = newGrid
        }

        return grid.sumOf { it.count { it } }.toString()
    }

    private fun parseInput(): List<MutableList<Boolean>> {
        // TODO: use bitset probably
        val grid: MutableList<MutableList<Boolean>> = mutableListOf()

        for (i in input.lines()) {
            val lst = mutableListOf<Boolean>()

            for (j in i) {
                lst.add(j == '#')
            }

            grid.add(lst)
        }

        return grid
    }

    private fun List<List<Boolean>>.print() {
        for (x in this.indices) {
            for (y in this[x].indices) {
                print(if (this[x][y]) "#" else ".")
            }
            println()
        }
    }

    companion object {
        val VECTORS = listOf(
            -1 to -1,
            -1 to 0,
            -1 to 1,
            1 to -1,
            1 to 0,
            1 to 1,
            0 to -1,
            0 to 1,
        )
    }
}