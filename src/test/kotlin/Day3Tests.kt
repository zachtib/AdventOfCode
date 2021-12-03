import util.resource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Tests {
    private val input = resource {
        """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
        """.trimIndent()
    }.asStrings()

    @Test
    fun testPart1Givens() {
        val actual = day3Part1(input)
        assertEquals(198, actual)
    }

    @Test
    fun testPart2Givens() {
        val actual = day3Part2(input)
        assertEquals(230, actual)
    }
}