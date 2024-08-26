package me.grian.year2023.day7

enum class HandType(val power: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1);

    companion object {
        fun fromCardList(cards: List<Card>): HandType {
            // list is always 5 cards
            if (cards.size != 5) {
                throw Error("Can only process five cards")
            }

            val counts = cards.groupingBy { it }.eachCount()

            /*
            Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
One pair, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4
High card, where all cards' labels are distinct: 23456
             */

            if (counts.containsValue(5)) {
                return FIVE_OF_A_KIND
            } else if (counts.containsValue(4)) {
                return FOUR_OF_A_KIND
            } else if (counts.values.sorted() == listOf(2, 3)) {
                return FULL_HOUSE
            } else if (counts.containsValue(3) && counts.size == 3) {
                return THREE_OF_A_KIND
            } else if (counts.values.sorted() == listOf(1, 2, 2)) {
                return TWO_PAIR
            } else if (counts.containsValue(2) && counts.size == 4) {
                return ONE_PAIR
            } else if (counts.size == 5) {
                return HIGH_CARD
            } else {
                throw Error("Hand doesn't exist")
            }
        }
    }
}