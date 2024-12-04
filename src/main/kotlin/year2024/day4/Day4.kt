package me.grian.year2024.day4

import me.grian.Day
import me.grian.util.getInputText

class Day4 : Day {
    override val input: String = getInputText(2024, 4)

    private val size = input.lines().size

    override fun partOne(): String {
        val grid = parseInput()
        var xmasCount = 0

        for (x in 0..<size) {
            for (y in 0..<size) {
                if (grid[x][y] != "X") continue

                val coordinateSurroundings = grid.checkSurroundings(x, y, "M")
                if (coordinateSurroundings.isEmpty()) continue

                // not particularly clever but it's fairly efficient
                for (cs in coordinateSurroundings) {
                    when (cs.surrounding) {
                        Surrounding.TOP_LEFT ->
                            xmasCount += checkASLetters(-1, -1, -2, -2, grid, cs)
                        Surrounding.TOP ->
                            xmasCount += checkASLetters(-1, 0, -2, 0, grid, cs)
                        Surrounding.TOP_RIGHT ->
                            xmasCount += checkASLetters(-1, 1, -2, 2, grid, cs)
                        Surrounding.LEFT ->
                            xmasCount += checkASLetters(0, -1, 0, -2, grid, cs)
                        Surrounding.RIGHT ->
                            xmasCount += checkASLetters(0, 1, 0, 2, grid, cs)
                        Surrounding.BOTTOM_LEFT ->
                            xmasCount += checkASLetters(1, -1, 2, -2, grid, cs)
                        Surrounding.BOTTOM ->
                            xmasCount += checkASLetters(1, 0, 2, 0, grid, cs)
                        Surrounding.BOTTOM_RIGHT ->
                            xmasCount += checkASLetters(1, 1, 2, 2, grid, cs)
                    }
                }
            }
        }

        return xmasCount.toString()
    }

    override fun partTwo(): String {
        val grid = parseInput()
        var xmasCount = 0

        val letterSpots: List<Surrounding> = listOf(
            Surrounding.TOP_LEFT,
            Surrounding.TOP_RIGHT,
            Surrounding.BOTTOM_LEFT,
            Surrounding.BOTTOM_RIGHT,
        )

        val goodLetters = listOf(
            "SSMM",
            "MMSS",
            "MSMS",
            "SMSM"
        )

        for (x in 0..<size) {
            for (y in 0..<size) {
                if (grid[x][y] != "A") continue

                var letters = ""

                for (i in letterSpots) {
                    val dx = x + i.x
                    val dy = y + i.y

                    if (!gridHasKey(dx, dy)) continue

                    letters += grid[dx][dy]
                }

                if (letters.length < 4) continue

                if (letters in goodLetters) xmasCount++
            }
        }

        return xmasCount.toString()
    }

    private fun parseInput() = input.lines().map { it.split("").filter(String::isNotBlank) }

    /**
     * Checks all adjacent squares for that value and returns a List
     */
    private fun <T> List<List<T>>.checkSurroundings(x: Int, y: Int, value: T): List<CoordinateSurroundings> {
        val list: MutableList<CoordinateSurroundings> = mutableListOf()

        for (surrounding in Surrounding.entries) {
            val dx = x + surrounding.x
            val dy = y + surrounding.y

            if (!gridHasKey(dx, dy)) continue

            if (this[dx][dy] == value) {
                list.add(CoordinateSurroundings(
                    surrounding,
                    dx,
                    dy
                ))
            }
        }

        return list
    }

    private fun checkASLetters(axOffset: Int, ayOffset: Int, sxOffset: Int, syOffset: Int, grid: List<List<String>>, cs: CoordinateSurroundings): Int {
        val ax = cs.x + axOffset
        val ay = cs.y + ayOffset

        val sx = cs.x + sxOffset
        val sy = cs.y + syOffset

        if (!(gridHasKey(ax, ay) && gridHasKey(sx, sy))) return 0

        if (!(grid[ax][ay] == "A" && grid[sx][sy] == "S")) return 0

        return 1
    }

    private fun gridHasKey(x: Int, y: Int) = (x in 0..<size && y in 0..<size)
}