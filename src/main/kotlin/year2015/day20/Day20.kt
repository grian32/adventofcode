package me.grian.year2015.day20

import me.grian.Day
import me.grian.util.getInputText
import kotlin.math.pow

class Day20 : Day {
    override val input: String
        get() = getInputText(2015, 20)

    override fun partOne(): String {
        val presentGoal = input.toInt()
        /**
         *
         * ok so house 6 gets 120 presents, i.e gets hit by elves 1, 2, 3, 6
         * 10+20+30+60 = 120
         * 6.allDivisors() = [1,2,3,6]
         * =>
         * house = houseNum.allDivisors().sum() * 10
         */
        for (i in 1..(presentGoal / 10)) {
            val houseSum = i.sumOfAllDivisors()
            if (houseSum * 10 >= presentGoal) return i.toString()
        }

        return ""
    }

    override fun partTwo(): String {
        val presentGoal = input.toInt()

        val houses = IntArray(presentGoal / 10 + 1) { 0 }

        /**
         * completely misunderstood this problem initially, there are infinite elves & infinite houses but each elf
         * only delivers to first 50 houses he can, so we can just bf and stop after 50 houses for each elf
         * (i.e i..i * 50 step i, since you step i you get no extra houses cuz of the * 50)and it's still quick,
         * even faster than p1
         */
        for (i in 1..<(presentGoal / 10)) {
            val min = minOf(presentGoal / 10, i * 50)
            for (j in i..min step i) {
                houses[j] += i * 11
            }
        }

        // also computing this is so slow that it's not worth it to return early lol
        return houses.indexOfFirst { it >= presentGoal }.toString()
    }

    // https://math.stackexchange.com/a/22723
    private fun Int.sumOfAllDivisors(): Int {
        var sum = 1

        val primeOccurrences = mutableMapOf<Int, Int>()

        for (factor in getPrimeFactors()) {
            primeOccurrences[factor] = primeOccurrences.getOrDefault(factor, 0) + 1
        }

        for ((prime, exp) in primeOccurrences) {
            sum *= ((1 - (prime.toDouble().pow(exp + 1))) /
                    (1 - prime)).toInt()
        }

        return sum
    }

    private fun Int.getPrimeFactors(): List<Int> {
        val factors = mutableListOf<Int>()
        var num = this

        while (num % 2 == 0) {
            factors.add(2)
            num /= 2
        }

        var odd = 3

        while (odd * odd <= num) {
            while (num % odd == 0) {
                factors.add(odd)
                num /= odd
            }
            odd += 2
        }

        if (num > 1) factors.add(num)

        return factors
    }
}