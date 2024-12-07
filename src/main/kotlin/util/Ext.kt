package me.grian.util

fun List<Int>.mul(): Int {
    var total = this[0]

    val listWithoutFirst = this.subList(1, this.size)
    listWithoutFirst.forEach { total *= it }

    return total
}

fun List<String>.hasIndex(x: Int, y: Int): Boolean = x in indices && y in this[x].indices

fun <T> List<T>.dropAt(idx: Int): List<T> =
    toMutableList().apply { removeAt(idx) }

fun <T> List<T>.middle(): T = this[(size - 1) / 2]

fun <T> MutableList<T>.swap(firstIndex: Int, secondIndex: Int) {
    this[firstIndex] = this[secondIndex].also { this[secondIndex] = this[firstIndex] }
}

fun <T> List<T>.firstIndexOfInRange(value: T, range: IntRange): Int {
    forEachIndexed { idx, elem ->
        if (elem == value && idx in range) return idx
    }

    return -1
}

fun <T> List<T>.lastIndexOfInRange(value: T, range: IntRange): Int {
    forEachIndexed { idx, elem ->
        if (elem == value && idx in range) return idx
    }

    return -1
}

fun <T> Pair<T, T>.swap(): Pair<T, T> {
    return second to first
}