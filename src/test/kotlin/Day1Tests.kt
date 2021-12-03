import util.asInts
import util.resource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Tests {
    private val sampleInput = resource {
        """
        199
        200
        208
        210
        200
        207
        240
        269
        260
        263
        """.trimIndent()
    }.asInts()

    @Test
    fun testGivens() {
        val actual = day1Part1(sampleInput)
        assertEquals(7, actual)
    }

    @Test
    fun testPart2Givens() {
        val actual = day1Part2(sampleInput)
        assertEquals(5, actual)
    }
}