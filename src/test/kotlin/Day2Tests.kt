import org.junit.Test
import kotlin.test.assertEquals

class Day2Tests {
    @Test
    fun testPart1Givens() {
        val input = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
        """.asTestData { it.toSubmarineCommand() }
        val actual = day2Part1(input)
        assertEquals(150, actual)
    }

    @Test
    fun testPart2Givens() {
        val input = """
            
        """.asTestData { it.toSubmarineCommand() }
        val actual = day2Part2(input)
        assertEquals(-1, actual)
    }
}