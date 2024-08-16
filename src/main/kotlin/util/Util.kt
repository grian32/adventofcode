package me.grian.util

import java.nio.file.Path
import kotlin.io.path.readText

fun getInputFile(day: Int): String = Path.of("src/main/resources/inputs/day${day}").readText()