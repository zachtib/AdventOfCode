import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Tests {
    @Test
    fun testGivens() {
        val sampleInput = """
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
        """.toInts()
        val actual = day1Part1(sampleInput)
        assertEquals(7, actual)
    }

    @Test
    fun testPart2Givens() {
        val sampleInput = """
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
        """.toInts()
        val actual = day1Part2(sampleInput)
        assertEquals(5, actual)
    }
}