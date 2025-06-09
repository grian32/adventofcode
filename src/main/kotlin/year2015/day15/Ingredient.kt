package me.grian.year2015.day15

data class Ingredient(
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
) {
    fun sum() = capacity+durability+flavor+texture
}
