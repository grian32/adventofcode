package me.grian

import io.github.classgraph.ClassGraph

fun main(args: Array<String>) {
    val year = args[0]
    val day = args[1]

    val classGraph = ClassGraph().enableAllInfo().scan()

    val dayClass = classGraph.getClassesImplementing(Day::class.java).find { it.name == "me.grian.year${year}.Day${day}" }!!

    val instance = dayClass.loadClass().getDeclaredConstructor().newInstance() as Day

    println(instance.partOne())
    println(instance.partTwo())
}