package twentytwentytwo

import libadvent.resource.asListsOfInts
import libadvent.resource.resourceOf
import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Tests {

    private val calories = resourceOf(
        """
            1000
            2000
            3000

            4000

            5000
            6000

            7000
            8000
            9000

            10000
        """.trimIndent()
    ).asListsOfInts()

    @Test
    fun `test part 1 example`() {
        val result = day1Part1(calories)
        assertEquals(24000, result)
    }

    @Test
    fun `test part 2 example`() {
        val result = day1Part2(calories)
        assertEquals(45000, result)
    }
}
