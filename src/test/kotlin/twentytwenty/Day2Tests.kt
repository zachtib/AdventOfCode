package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Test

class Day2Tests {
    @Test
    fun `test policy conversions`() {
        val input = """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc
        """.trimIndent()

        val result = input.split('\n').map { it.toPolicy() }

        val expected = listOf(
                PasswordPolicy(1..3, 'a', "abcde"),
                PasswordPolicy(1..3, 'b', "cdefg"),
                PasswordPolicy(2..9, 'c', "ccccccccc"),
        )
        assertEquals(expected, result)
    }

    @Test
    fun `test given part1`() {

        val input = """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc
        """.trimIndent()

        val passwords = input.split('\n').map { it.toPolicy() }

        val actual = countValidPasswords(passwords)
        assertEquals(2, actual)
    }

    @Test
    fun `test given part2`() {

        val input = """
            1-3 a: abcde
            1-3 b: cdefg
            2-9 c: ccccccccc
        """.trimIndent()

        val passwords = input.split('\n').map { it.toPolicy() }

        val actual = countValidPasswordsPart2(passwords)
        assertEquals(1, actual)
    }
}
