package me.grian.year2023.day4

import me.grian.Day
import me.grian.util.getInputText
import kotlin.math.max
import kotlin.math.min

class Day4 : Day {
    override val input: String = getInputText(2023,4)

    override fun partOne(): String {
        val cards: List<Card> = parseInput()
        var sum = 0

        cards.forEach { card ->
            var points = 0

            card.cardNumbers.forEach {
                if (it in card.winningNumbers) {
                    // some cards may have no winning numbers so can't send to 1 point by default
                    if (points == 0) points = 1 else points *= 2
                }
            }

            sum += points
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        val cards: MutableList<Card> = parseInput().toMutableList()
        // game id to winning numbers
        val cardWinning = mutableMapOf<Int, Int>()
        val cardCounts = MutableList(cards.size) { 1 }

        cards.forEachIndexed { idx, card ->
            var amountOfWinning = 0

            card.cardNumbers.forEach {
                if (it in card.winningNumbers) amountOfWinning++
            }

            cardWinning[idx] = amountOfWinning
        }

        for (i in 1..cards.size) {
            for (j in 0..<(cardWinning[i - 1]!!)) {
                cardCounts[(i + j + 1).coerceAtMost(cards.size - 1)] += cardCounts[i]
            }
        }

        return cardCounts.sum().toString()
    }

    private fun parseInput(): List<Card> {
        val cards = mutableListOf<Card>()

        input.lines().forEach { line ->
            val card = Card.fromString(line)
            cards.add(card)
        }

        return cards
    }
}