package me.grian.util

import kotlinx.coroutines.currentCoroutineContext

fun List<Int>.mul(): Int {
    var total = this[0]

    val listWithoutFirst = this.subList(1, this.size)
    listWithoutFirst.forEach { total *= it }

    return total
}

fun <T> List<List<T>>.hasIndex(x: Int, y: Int): Boolean = x in indices && y in this[x].indices

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

// from gippity lol
fun <T> List<T>.permute(length: Int): List<List<T>> {
    val result = mutableListOf<List<T>>()

    fun backtrack(current: MutableList<T>) {
        if (current.size == length) {
            result.add(current.toList())
            return
        }
        for (element in this) {
            current.add(element)
            backtrack(current)
            current.removeAt(current.size - 1)
        }
    }

    backtrack(mutableListOf())
    return result
}

fun <T> List<T>.permuteUnique(): List<List<T>> {
    val mut = toMutableList()
    val result = mutableListOf<List<T>>()

    fun generate(k: Int) {
        // https://en.wikipedia.org/wiki/Heap%27s_algorithm
        if (k == 1) {
            result.add(mut.toList())

            return
        }

        generate(k - 1)

        for (i in 0 until k - 1) {
            if (k % 2 == 0) {
                mut.swap(i, k - 1)
            } else {
                mut.swap(0, k - 1)
            }
            generate(k - 1)
        }
    }

    generate(size)

    return result
}

fun List<String>.filterIsNotEmpty(): List<String> =
    filter { it.isNotEmpty() }

fun String.containsAny(lst: List<String>): Boolean {
    for (i in lst) {
        if (this.contains(i)) return true
    }

    return false
}