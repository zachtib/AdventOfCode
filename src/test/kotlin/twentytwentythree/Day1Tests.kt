package twentytwentythree

import libadvent.resource.asStrings
import libadvent.resource.resourceOf
import org.junit.Test
import kotlin.test.assertEquals

class Day1Tests {
    private val input = resourceOf(
        """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
        """.trimIndent()
    ).asStrings()

    private val secondInput = resourceOf(
        """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
        """.trimIndent()
    ).asStrings()

    @Test
    fun `test part 1 example`() {
        val result = day1Part1(input)
        assertEquals(142, result)
    }

    @Test
    fun `test part 2 example`() {
        val result = day1Part2(secondInput)
        assertEquals(281, result)
    }
}