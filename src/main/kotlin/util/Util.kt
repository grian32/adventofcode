package me.grian.util

import java.nio.file.Path
import kotlin.io.path.readText

fun getInputText(day: Int): String = Path.of("src/main/resources/inputs/day${day}.txt").readText()