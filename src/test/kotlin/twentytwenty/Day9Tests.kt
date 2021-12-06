package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Day9Tests {

    private val input = """
        35
        20
        15
        25
        47
        40
        62
        55
        65
        95
        102
        117
        150
        182
        127
        219
        299
        277
        309
        576
    """.trimIndent()
            .lines()
            .map { it.toLong() }

    @Test
    fun `test given`() {
        val decoder = XmasDecoder(5, input)
        val actual = decoder.findFirstInvalidElement()

        assertEquals(127L, actual)
    }

    @Test
    fun `test given pt2`() {
        val decoder = XmasDecoder(5, input)
        val actual = decoder.findRangeForInvalidElement()

        assertEquals(62L, actual)
    }

    @Test
    fun `test take`() {
        val iter = (1..10).iterator()

        val actual = iter.take(5)
        assertEquals(listOf(1, 2, 3, 4, 5), actual)
        assertTrue(iter.hasNext())
    }
}