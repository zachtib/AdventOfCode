package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Test

class Day6Tests {
    private val given = """
            abc

            a
            b
            c

            ab
            ac

            a
            a
            a
            a

            b
        """.trimIndent()

    @Test
    fun `test given example`() {
        val actual = calculateGroupSum(given)
        assertEquals(11, actual)
    }

    @Test
    fun `test given example part 2`() {
        val actual = calculateGroupSumPart2(given)
        assertEquals(6, actual)
    }
}