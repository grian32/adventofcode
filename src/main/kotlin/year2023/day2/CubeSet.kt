package me.grian.year2023.day2

data class CubeSet (
    val amount: Int,
    val color: String,
) {
    companion object {
        fun fromString(str: String): CubeSet {
            val (amount, color) = str.split(" ")

            return CubeSet(amount.toInt(), color)
        }
    }
}
