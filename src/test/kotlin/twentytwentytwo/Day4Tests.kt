package twentytwentytwo

import libadvent.resource.asType
import libadvent.resource.resourceOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day4Tests {
    val sampleInput = resourceOf(
        """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
        """.trimIndent()
    )

    private val pairs = sampleInput.asType { parseSections(it) }

    @Test
    fun `test eitherContains`() {
        assertTrue(eitherContains(2..5 to 1..7))
        assertTrue(eitherContains(3..8 to 4..6))
        assertFalse(eitherContains(1..3 to 2..5))
        assertFalse(eitherContains(4..7 to 1..3))
    }

    @Test
    fun `test part 1 example`() {
        val actual = day4Part1(pairs)
        assertEquals(2, actual)
    }

    @Test
    fun `test part 2 example`() {
        val actual = day4Part2(pairs)
        assertEquals(4, actual)
    }
}