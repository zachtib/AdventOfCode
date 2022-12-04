package twentytwentytwo

import libadvent.resource.asType
import libadvent.resource.resourceOf
import kotlin.test.Test
import kotlin.test.assertEquals


class Day2Tests {

    val sampleInput = resourceOf(
        """
        A Y
        B X
        C Z
        """.trimIndent()
    )

    @Test
    fun `test part 1 example`() {
        val actual = day2Part1(sampleInput.asType { it.asRpsRound() })
        assertEquals(15, actual)
    }

    @Test
    fun `test part 2 example`() {
        val actual = day2Part2(sampleInput.asType { it.asRpsRound2() })
        assertEquals(12, actual)
    }
}