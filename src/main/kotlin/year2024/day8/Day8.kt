package me.grian.year2024.day8

import me.grian.Day
import me.grian.util.dropAt
import me.grian.util.filterIsNotEmpty
import me.grian.util.getInputText
import me.grian.util.hasIndex
import kotlin.math.max
import kotlin.math.min

class Day8 : Day {
    override val input: String = getInputText(2024, 8)

    override fun partOne(): String {
        // store the dx, dy between first letter and second letter? and then apply that to the second letter and put a
        // # there on a secondary grid (to account for overlaps between # and letters)

        val grid = parseInput()
        val antiNodeGrid: List<MutableList<String>> = List(grid.size) { MutableList(grid[0].size) { "." } }
        val allAntennas = findAllAntennas(grid)

        allAntennas.forEach { (key, list) ->
            list.forEachIndexed { idx, point ->
                list.dropAt(idx).indices.forEach other@ { next ->
                    val nextPoint = list[next]

                    val dx = nextPoint.x - point.x
                    val dy = nextPoint.y - point.y

                    val newX = nextPoint.x + dx
                    val newY = nextPoint.y + dy

                    val otherNewX = point.x - dx
                    val otherNewY = point.y - dy

                    if (
                        antiNodeGrid.hasIndex(newX, newY) &&
                        grid[newX][newY] != key
                    ) antiNodeGrid[newX][newY] = "#"

                    if (
                        antiNodeGrid.hasIndex(otherNewX, otherNewY) &&
                        grid[otherNewX][otherNewY] != key
                    ) antiNodeGrid[otherNewX][otherNewY] = "#"
                }
            }
        }

        val antiNodeCount = antiNodeGrid.sumOf {
            it.count { s -> s == "#" }
        }

        return antiNodeCount.toString()
    }

    override fun partTwo(): String {
        // while newx and newy and otherx and othery keep adding/subbing the difference onto them until u run out of
        // grid space
        val grid = parseInput()
        val antiNodeGrid: List<MutableList<String>> = List(grid.size) { MutableList(grid[0].size) { "." } }
        val allAntennas = findAllAntennas(grid)
        val xBound = antiNodeGrid.size
        val yBound = antiNodeGrid[0].size

        // A's going topleft in example input leave out the last 3 going top left

        allAntennas.forEach { (key, list) ->
            list.forEachIndexed { idx, point ->
                // set each letter as a #
                antiNodeGrid[point.x][point.y] = "#"

                list.dropAt(idx).forEach other@ { nextPoint ->

                    val dx = nextPoint.x - point.x
                    val dy = nextPoint.y - point.y

                    var newX = nextPoint.x + dx
                    var newY = nextPoint.y + dy

                    var otherNewX = point.x - dx
                    var otherNewY = point.y - dy

                    // TODO: refactor this piece of garbage im behind and no time :(((
                    if (dx == 0) {
                        repeat(yBound - newY) { _ ->
                            if (
                                antiNodeGrid.hasIndex(newX, newY)
                            ) antiNodeGrid[newX][newY] = "#"

                            newY += dy
                        }

                        repeat(yBound - otherNewY) { _ ->
                            if (
                                antiNodeGrid.hasIndex(otherNewX, otherNewY)
                            ) antiNodeGrid[otherNewX][otherNewY] = "#"

                            otherNewY -= dy
                        }
                    } else if (dy == 0) {
                        repeat(xBound - newX) { _ ->
                            if (
                                antiNodeGrid.hasIndex(newX, newY)
                            ) antiNodeGrid[newX][newY] = "#"

                            newX += dx
                        }

                        repeat(yBound - otherNewY) { _ ->
                            if (
                                antiNodeGrid.hasIndex(otherNewX, otherNewY)
                            ) antiNodeGrid[otherNewX][otherNewY] = "#"

                            otherNewY -= dy
                        }
                    } else {
                        while (true) {
                            if (
                                antiNodeGrid.hasIndex(newX, newY)
                            ) antiNodeGrid[newX][newY] = "#"

                            newX += dx
                            newY += dy

                            if ((newX < 0 || newX >= xBound)|| (newY < 0 || newY > yBound)) break
                        }

                        while (true) {
                            if (
                                antiNodeGrid.hasIndex(otherNewX, otherNewY)
                            ) antiNodeGrid[otherNewX][otherNewY] = "#"

                            otherNewX -= dx
                            otherNewY -= dy

                            if ((otherNewX < 0 || otherNewX >= xBound) || (otherNewY < 0 || otherNewY > yBound)) break
                        }
                    }
                }
            }
        }

        val antiNodeCount = antiNodeGrid.sumOf {
            it.count { s -> s == "#" }
        }

        return antiNodeCount.toString()
    }

    private fun parseInput(): List<List<String>> =
        input.lines().map {
            it.split("").filterIsNotEmpty()
        }

    private fun findAllAntennas(grid: List<List<String>>): MutableMap<String, MutableList<Point>> {
        val allAntennas = mutableMapOf<String, MutableList<Point>>()

        grid.forEachIndexed { x, row ->
            row.forEachIndexed inner@{ y, elem ->
                if (elem == ".") return@inner

                if (!allAntennas.containsKey(elem)) {
                    allAntennas[elem] = mutableListOf(Point(x, y))
                    return@inner
                }

                allAntennas[elem]!!.add(Point(x, y))
            }
        }

        return allAntennas
    }
}