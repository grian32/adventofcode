package me.grian.year2015.day7

data class Instruction(
    val first: String,
    val second: String?,
    val destination: String,
    val instruction: InstructionType
)

enum class InstructionType {
    AND,
    OR,
    LSHIFT,
    RSHIFT,
    NOT,
    ASSIGN
}
