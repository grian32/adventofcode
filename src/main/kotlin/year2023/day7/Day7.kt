package me.grian.year2023.day7

import me.grian.Day
import me.grian.util.getInputText
import kotlin.Comparator

class Day7 : Day {
    override val input: String = getInputText(2023, 7)

    override fun partOne(): String {
        val input = parseInput()
        var sum = 0

        val sortedInput = input.sortedWith(
            Comparator { hand, other ->
                val typePower = hand.handType.power - other.handType.power

                // if the hand types are diff can just return the diff
                if (typePower != 0) return@Comparator typePower

                for (idx in hand.cards.indices) {
                    val cardPower = hand.cards[idx].power - other.cards[idx].power
                    if (cardPower == 0) continue
                    return@Comparator cardPower
                }

                // if the hand has the same type and all cards are the same then they are equal and should
                // be sorted as such
                return@Comparator 0
            }
        )

        sortedInput.forEachIndexed { idx, hand ->
            val rank = idx + 1

            sum += hand.bet * rank
        }

        return sum.toString()
    }

    override fun partTwo(): String {
        TODO("Not yet implemented")
    }

    private fun parseInput(): List<Hand> {
        val lines = input.lines()
        val list = mutableListOf<Hand>()

        lines.forEach {
            val (cardString, bet) = it.split(" ")
            val cards = cardString.toMutableList().map(Card::fromChar)
            val handType = HandType.fromCardList(cards)

            list.add(
                Hand(
                    cards,
                    handType,
                    bet.toInt()
                )
            )
        }

        return list
    }
}