import org.junit.Test
import util.asType
import util.resource
import kotlin.test.assertEquals

class Day2Tests {
    private val input = resource {
        """
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2
        """.trimIndent()
    }.asType { it.toSubmarineCommand() }

    @Test
    fun testPart1Givens() {
        val actual = day2Part1(input)
        assertEquals(150, actual)
    }

    @Test
    fun testPart2Givens() {
        val actual = day2Part2(input)
        assertEquals(900, actual)
    }
}