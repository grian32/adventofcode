package me.grian.year2023.day7

enum class Card(val power: Int) {
    A(14),
    K(13),
    Q(12),
    J(11),
    T(10),
    NINE(9),
    EIGHT(8),
    SEVEN(7),
    SIX(6),
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2);

    companion object {
        fun fromChar(chr: Char): Card {
            return when (chr) {
                'A' -> A
                'K' -> K
                'Q' -> Q
                'J' -> J
                'T' -> T
                '9' -> NINE
                '8' -> EIGHT
                '7' -> SEVEN
                '6' -> SIX
                '5' -> FIVE
                '4' -> FOUR
                '3' -> THREE
                '2' -> TWO
                else -> throw Error("Card does not exist")
            }
        }
    }
}