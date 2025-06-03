package me.grian

import io.github.classgraph.ClassGraph
import io.github.classgraph.ClassInfo
import kotlin.time.measureTime

fun main(args: Array<String>) {
    val year = args[0]

    val classGraph = ClassGraph().enableAllInfo().scan()

    if (args.size > 1) {
        val day = args[1]

        val dayClass = classGraph.getClassesImplementing(Day::class.java)
            .find { it.name == "me.grian.year${year}.day${day}.Day${day}" }!!

        common(dayClass, day, year)
    } else {
        val days = classGraph.getClassesImplementing(Day::class.java)
            .filter { it.name.startsWith("me.grian.year${year}") }

        for (dayClass in days) {
            val day = dayClass.name.last()

            common(dayClass, day.toString(), year)
        }
    }
}

fun common(dayClass: ClassInfo, day: String, year: String) {
    val instance = dayClass.loadClass().getDeclaredConstructor().newInstance() as Day

    var p1Result: String
    var p2Result: String

    val p1Time = measureTime {
        p1Result = instance.partOne()
    }

    println("y$year/d$day/p1 = $p1Result | in $p1Time")

    val p2Time = measureTime {
        p2Result = instance.partTwo()
    }

    println("y$year/d$day/p2 = $p2Result | in $p2Time")
}