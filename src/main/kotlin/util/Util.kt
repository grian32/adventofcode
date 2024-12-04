package me.grian.util

import java.nio.file.Path
import kotlin.io.path.readText

fun getInputText(year: Int, day: Int): String = Path.of("src/main/resources/inputs/${year}/day${day}.txt").readText()
