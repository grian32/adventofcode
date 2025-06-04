package me.grian.year2015.day7

import me.grian.Day
import me.grian.util.getInputText

class Day7 : Day {
    override val input: String
        get() = getInputText(2015, 7)

    override fun partOne(): String {
        val instructionList: List<Instruction> = input.lines().map { it.parseInstruction()!! }

        return findValue("a", mutableMapOf(), instructionList).toString()
    }

    override fun partTwo(): String {
        /**
         * a little hacky but p2 is quicker than p1, and both are pretty speedy as is, so ¯\_(ツ)_/¯
         */
        val p1 = partOne()
        val modifiedInput = input.lines().toMutableList()
        modifiedInput.add(0, "$p1 -> b")

        val instructionList: List<Instruction> = modifiedInput.map { it.parseInstruction()!! }

        return findValue("a", mutableMapOf(), instructionList).toString()
    }

    private fun findValue(
        value: String,
        solutionMap: MutableMap<String, UShort>,
        instructionList: List<Instruction>,
    ): UShort {
        if (solutionMap[value] != null) return solutionMap[value]!!
        val instruction = instructionList.find { it.destination == value }!!

        when (instruction.instruction) {
            InstructionType.ASSIGN -> {
                solutionMap[instruction.destination] =
                    instruction.first.toUShortOrNull() ?: findValue(instruction.first, solutionMap, instructionList)
            }

            InstructionType.AND -> {
                val first = instruction.first.toUShortOrNull()
                    ?: findValue(instruction.first, solutionMap, instructionList)
                val second = instruction.second?.toUShortOrNull()
                    ?: findValue(instruction.second!!, solutionMap, instructionList)

                solutionMap[instruction.destination] = first and second
            }

            InstructionType.OR -> {
                val first = instruction.first.toUShortOrNull()
                    ?: findValue(instruction.first, solutionMap, instructionList)
                val second = instruction.second?.toUShortOrNull()
                    ?: findValue(instruction.second!!, solutionMap, instructionList)

                solutionMap[instruction.destination] = first or second
            }

            InstructionType.LSHIFT -> {
                val second = instruction.second!!.toInt()

                solutionMap[instruction.destination] =
                    (findValue(instruction.first, solutionMap, instructionList).toInt() shl second).toUShort()
            }

            InstructionType.RSHIFT -> {
                val second = instruction.second!!.toInt()

                solutionMap[instruction.destination] =
                    (findValue(instruction.first, solutionMap, instructionList).toInt() shr second).toUShort()
            }

            InstructionType.NOT -> {
                solutionMap[instruction.destination] = findValue(instruction.first, solutionMap, instructionList).inv()
            }
        }

        return solutionMap[value]!!
    }

    private fun String.parseInstruction(): Instruction? {
        val sides = split(" -> ")
        val dest = sides[1]

        val instructions = sides[0].split(" ")

        return when (instructions.size) {
            1 -> {
                Instruction(
                    instructions[0],
                    null,
                    dest,
                    InstructionType.ASSIGN
                )
            }

            2 -> {
                Instruction(
                    instructions[1],
                    null,
                    dest,
                    InstructionType.NOT
                )
            }

            3 -> {
                when (instructions[1]) {
                    "AND" -> Instruction(
                        instructions[0],
                        instructions[2],
                        dest,
                        InstructionType.AND
                    )

                    "OR" -> Instruction(
                        instructions[0],
                        instructions[2],
                        dest,
                        InstructionType.OR
                    )

                    "LSHIFT" -> Instruction(
                        instructions[0],
                        instructions[2],
                        dest,
                        InstructionType.LSHIFT
                    )

                    "RSHIFT" -> Instruction(
                        instructions[0],
                        instructions[2],
                        dest,
                        InstructionType.RSHIFT
                    )

                    else -> null
                }
            }

            else -> null
        }
    }
}