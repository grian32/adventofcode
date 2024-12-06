package me.grian.util

import java.nio.file.Path
import kotlin.io.path.readText

fun getInputText(year: Int, day: Int): String = Path.of("src/main/resources/inputs/${year}/day${day}.txt").readText()

/**
 * Returns a pair of x to y, if it's not found it returns -1 to -1
 */
fun <T> indexOf2D(list: List<List<T>>, value: T): Pair<Int, Int> {
    list.forEachIndexed { i, inner ->
        inner.forEachIndexed { j, elem ->
            if (value == elem) return i to j
        }
    }

    return -1 to -1
}