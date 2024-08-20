package me.grian.year2023.day4

data class Card(
    val winningNumbers: List<Int>,
    val cardNumbers: List<Int>,
) {
    companion object {
        fun fromString(str: String): Card {
            // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            val (_, allNumbers) = str.split(": ")
            val (winning, numbers) = allNumbers.split(" | ")
            val winningNumbers = winning
                .split(" ")
                .filter(String::isNotEmpty)
                .map(String::trim)
                .map(String::toInt)

            val cardNumbers = numbers
                .split(" ")
                .filter(String::isNotEmpty)
                .map(String::trim)
                .map(String::toInt)

            return Card(winningNumbers, cardNumbers)
        }
    }
}
