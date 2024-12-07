package me.grian.year2024.day6

import me.grian.Day
import me.grian.util.firstIndexOfInRange
import me.grian.util.getInputText
import me.grian.util.indexOf2D
import me.grian.util.swap
import kotlin.time.measureTime

// FIXME: this whole solution is a sad display
class Day6 : Day {
    override val input: String = getInputText(2024, 6)

    override fun partOne(): String {
        val grid = parseInput()

        val guardIndex = indexOf2D(grid, '^')
        val guard = Guard(
            guardIndex.first,
            guardIndex.second,
            Direction.LEFT // setting this to LEFT as last direction makes it go up straightaway
        )
        val squaresTraversed: MutableSet<Pair<Int, Int>> = mutableSetOf()

        outer@ while (true) {
            when (guard.lastDirection) {
                Direction.UP -> {
                    // need to go right
                    val row = grid[guard.x]
                    val obstacleIdx = row.firstIndexOfInRange('#', guard.y..grid[guard.x].lastIndex)

                    if (obstacleIdx == -1) {
                        // add the remaining squares if it goes off screen
                        (guard.y..row.lastIndex).forEach { yTraversed ->
                            squaresTraversed.add(guard.x to yTraversed)
                        }

                        break@outer
                    }

                    // guard.y..obstacleIdx-1
                    (guard.y..<obstacleIdx).forEach { yTraversed ->
                        squaresTraversed.add(guard.x to yTraversed)
                    }
                    guard.y = obstacleIdx - 1
                    guard.lastDirection = Direction.RIGHT
                }
                Direction.DOWN -> {
                    // need to go left
                    val row = grid[guard.x].slice(0..guard.y)
                    val obstacleIdx = row.lastIndexOf('#')

                    if (obstacleIdx == -1) {
                        (0..guard.y).forEach { yTraversed ->
                            squaresTraversed.add(guard.x to yTraversed)
                        }
                        break@outer
                    }

                    // obstacleIdx+1..guard.y
                    (obstacleIdx + 1..guard.y).forEach { yTraversed ->
                        squaresTraversed.add(guard.x to yTraversed)
                    }
                    guard.y = obstacleIdx + 1
                    guard.lastDirection = Direction.LEFT

                }
                Direction.LEFT -> {
                    // needs to go up
                    var found = false

                    for (x in (guard.x downTo 0)) {
                        if (grid[x][guard.y] == '#') {
                            // might be bad but working preliminary
                            (guard.x downTo x + 1).forEach { xTraversed ->
                                squaresTraversed.add(xTraversed to guard.y)
                            }
                            guard.x = x + 1
                            guard.lastDirection = Direction.UP
                            found = true
                            break
                        }
                    }

                    if (!found) {
                        (guard.x downTo 0).forEach { xTraversed ->
                            squaresTraversed.add(xTraversed to guard.y)
                        }
                        break@outer
                    }
                }
                Direction.RIGHT -> {
                    // need to go down
                    var found = false

                    for (x in (guard.x..grid.lastIndex)) {
                        if (grid[x][guard.y] == '#') {
                            // guard.x..x
                            (guard.x..<x).forEach { xTraversed ->
                                squaresTraversed.add(xTraversed to guard.y)
                            }
                            guard.x = x - 1
                            guard.lastDirection = Direction.DOWN
                            found = true
                            break
                        }
                    }

                    if (!found) {
                        (guard.x..grid.lastIndex).forEach { xTraversed ->
                            squaresTraversed.add(xTraversed to guard.y)
                        }
                        break@outer
                    }
                }
            }
        }

        return squaresTraversed.size.toString()
    }

    override fun partTwo(): String {
        val origGrid = parseInput()
        val grids: MutableList<List<List<Char>>> = mutableListOf()
        var loopedGrids = 0

        origGrid.forEachIndexed { x, row ->
            row.forEachIndexed { y, elem ->
                if (elem == '.') {
                    val newGrid = origGrid.toMutableList().map(List<Char>::toMutableList)
                    newGrid[x][y] = '#'
                    grids.add(newGrid)
                }
            }
        }

        grids.forEach { grid ->
            val guardIndex = indexOf2D(grid, '^')
            val guard = Guard(
                guardIndex.first,
                guardIndex.second,
                Direction.LEFT // setting this to LEFT as last direction makes it go up straightaway
            )
            val pastSquaresTraversedSizes: MutableList<Int> = mutableListOf()
            val squaresTraversed: MutableSet<Pair<Int, Int>> = mutableSetOf()

            outer@ while (true) {
                when (guard.lastDirection) {
                    Direction.UP -> {
                        // need to go right
                        val row = grid[guard.x]
                        val obstacleIdx = row.firstIndexOfInRange('#', guard.y..grid[guard.x].lastIndex)

                        if (obstacleIdx == -1) {
                            // add the remaining squares if it goes off screen
                            (guard.y..row.lastIndex).forEach { yTraversed ->
                                squaresTraversed.add(guard.x to yTraversed)
                            }

                            break@outer
                        }

                        pastSquaresTraversedSizes.add(squaresTraversed.size)

                        if (
                            pastSquaresTraversedSizes.size > 15 && // ensures there's been enough passes
                            pastSquaresTraversedSizes.takeLast(5).distinct().size == 1
                        ) {
                            loopedGrids++
                            break@outer
                        }

                        // guard.y..obstacleIdx-1
                        (guard.y..<obstacleIdx).forEach { yTraversed ->
                            squaresTraversed.add(guard.x to yTraversed)
                        }
                        guard.y = obstacleIdx - 1
                        guard.lastDirection = Direction.RIGHT
                    }
                    Direction.DOWN -> {
                        // need to go left
                        val row = grid[guard.x].slice(0..guard.y)
                        val obstacleIdx = row.lastIndexOf('#')

                        if (obstacleIdx == -1) {
                            (0..guard.y).forEach { yTraversed ->
                                squaresTraversed.add(guard.x to yTraversed)
                            }
                            break@outer
                        }

                        pastSquaresTraversedSizes.add(squaresTraversed.size)

                        if (
                            pastSquaresTraversedSizes.size > 15 && // ensures there's been enough passes
                            pastSquaresTraversedSizes.takeLast(5).distinct().size == 1
                        ) {
                            loopedGrids++
                            break@outer
                        }
                        // obstacleIdx+1..guard.y
                        (obstacleIdx + 1..guard.y).forEach { yTraversed ->
                            squaresTraversed.add(guard.x to yTraversed)
                        }
                        guard.y = obstacleIdx + 1
                        guard.lastDirection = Direction.LEFT

                    }
                    Direction.LEFT -> {
                        // needs to go up
                        var found = false

                        for (x in (guard.x downTo 0)) {
                            if (grid[x][guard.y] == '#') {
                                pastSquaresTraversedSizes.add(squaresTraversed.size)

                                if (
                                    pastSquaresTraversedSizes.size > 15 && // ensures there's been enough passes
                                    pastSquaresTraversedSizes.takeLast(5).distinct().size == 1
                                ) {
                                    loopedGrids++
                                    break@outer
                                }
                                // might be bad but working preliminary
                                (guard.x downTo x + 1).forEach { xTraversed ->
                                    squaresTraversed.add(xTraversed to guard.y)
                                }
                                guard.x = x + 1
                                guard.lastDirection = Direction.UP
                                found = true
                                break
                            }
                        }

                        if (!found) {
                            (guard.x downTo 0).forEach { xTraversed ->
                                squaresTraversed.add(xTraversed to guard.y)
                            }
                            break@outer
                        }
                    }
                    Direction.RIGHT -> {
                        // need to go down
                        var found = false

                        for (x in (guard.x..grid.lastIndex)) {
                            if (grid[x][guard.y] == '#') {
                                pastSquaresTraversedSizes.add(squaresTraversed.size)

                                if (
                                    pastSquaresTraversedSizes.size > 15 && // ensures there's been enough passes
                                    pastSquaresTraversedSizes.takeLast(5).distinct().size == 1
                                ) {
                                    loopedGrids++
                                    break@outer
                                }
                                // guard.x..x
                                (guard.x..<x).forEach { xTraversed ->
                                    squaresTraversed.add(xTraversed to guard.y)
                                }
                                guard.x = x - 1
                                guard.lastDirection = Direction.DOWN
                                found = true
                                break
                            }
                        }

                        if (!found) {
                            (guard.x..grid.lastIndex).forEach { xTraversed ->
                                squaresTraversed.add(xTraversed to guard.y)
                            }
                            break@outer
                        }
                    }
                }
            }
        }

        return loopedGrids.toString()
    }

    private fun parseInput(): List<List<Char>> =
        input.split("\n").map(String::toCharArray).map(CharArray::toList)
}