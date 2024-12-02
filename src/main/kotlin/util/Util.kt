package me.grian.util

import java.nio.file.Path
import kotlin.io.path.readText

fun getInputText(year: Int, day: Int): String = Path.of("src/main/resources/inputs/${year}/day${day}.txt").readText()

fun List<String>.hasIndex(x: Int, y: Int): Boolean = x in indices && y in this[x].indices

fun <T> List<T>.dropAt(idx: Int): List<T> {
    val newList = toMutableList()
    newList.removeAt(idx)
    return newList
}