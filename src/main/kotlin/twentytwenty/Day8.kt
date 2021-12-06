package twentytwenty

import res.asType
import res.load

sealed class Instruction {
    data class Acc(val value: Int) : Instruction()
    data class Jmp(val offset: Int): Instruction()
    data class Nop(val value: Int) : Instruction()

    companion object {
        fun parse(input: String): Instruction {
            val (ins, arg) = input.split(' ', limit = 2)
            return when (ins) {
                "acc" -> Acc(arg.toInt())
                "jmp" -> Jmp(arg.toInt())
                "nop" -> Nop(arg.toInt())
                else -> throw IllegalArgumentException("twentytwenty.Instruction must be one of acc, jmp, nop")
            }
        }
    }
}

class InfiniteLoopException : RuntimeException()

class Computer {
    private var pointer = 0
    private var accumulator = 0
    private val executedInstructions = mutableSetOf<Int>()

    fun runToCompletion(instructions: List<Instruction>): Int {
        pointer = 0
        accumulator = 0
        executedInstructions.clear()

        while (pointer >= 0 && pointer < instructions.size) {
            if (pointer in executedInstructions) {
                throw InfiniteLoopException()
            }
            executedInstructions.add(pointer)
            when(val instruction = instructions[pointer]) {
                is Instruction.Acc -> {
                    accumulator += instruction.value
                    pointer += 1
                }
                is Instruction.Jmp -> pointer += instruction.offset
                is Instruction.Nop -> pointer += 1
            }
        }
        return accumulator
    }

    fun runUntilLoopDetected(instructions: List<Instruction>): Int {
        try {
            runToCompletion(instructions)
        } catch (e: InfiniteLoopException) {
            return accumulator
        }
        return 0
    }
}

fun Instruction.flip(): Instruction = when(this) {
    is Instruction.Acc -> this
    is Instruction.Jmp -> Instruction.Nop(offset)
    is Instruction.Nop -> Instruction.Jmp(value)
}

fun repairProgram(program: List<Instruction>): Int {
    val computer = Computer()
    for ((index, value) in program.withIndex()) {
        if (value is Instruction.Acc) {
            continue
        }
        val newProgram = program.toMutableList()
        newProgram[index] = value.flip()

        try {
            return computer.runToCompletion(newProgram)
        } catch (e: InfiniteLoopException) {
            continue
        }
    }
    return -1
}

fun main() {
    val computer = Computer()
    val program = load("day8.txt").asType { Instruction.parse(it) }
    val part1Result = computer.runUntilLoopDetected(program)
    println("Part 1 result: $part1Result")

    val part2Result = repairProgram(program)
    println("Part 2 result: $part2Result")
}