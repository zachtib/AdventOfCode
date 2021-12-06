package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Day8Tests {

    private val instructions = """
        nop +0
        acc +1
        jmp +4
        acc +3
        jmp -3
        acc -99
        acc +1
        jmp -4
        acc +6
    """.trimIndent()

    @Test
    fun `test instruction parsing`() {
        val result = instructions.lines().map { Instruction.parse(it) }

        assertEquals(9, result.size)
        assertTrue(result.first() is Instruction.Nop)
    }

    @Test
    fun `test part 1 given`() {
        val computer = Computer()
        val program = instructions.lines().map { Instruction.parse(it) }
        val actual = computer.runUntilLoopDetected(program)

        assertEquals(5, actual)
    }

    @Test
    fun `test termination`() {
        val computer = Computer()
        val program = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            nop -4
            acc +6
        """.trimIndent().lines().map { Instruction.parse(it) }

        val result = computer.runToCompletion(program)
        assertEquals(8, result)
    }

    @Test
    fun `test repair program`() {
        val program = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6
        """.trimIndent().lines().map { Instruction.parse(it) }

        val actual = repairProgram(program)
        assertEquals(8, actual)
    }

}