package me.grian.year2023.day4

import me.grian.Day
import me.grian.util.getInputText

class Day4 : Day {
    override val input: String = getInputText(4)

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
        TODO("Not yet implemented")
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