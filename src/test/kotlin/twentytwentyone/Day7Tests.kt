package twentytwentyone

import res.asInts
import res.resourceOf
import kotlin.test.Test
import kotlin.test.assertEquals

class Day7Tests {
    private val sampleInput = resourceOf(
        "16,1,2,0,4,2,7,1,2,14",
        ","
    ).asInts()

    @Test
    fun `test part 1 givens`() {
        val actual = day7Part1(sampleInput)
        assertEquals(-1, actual)
    }

    @org.junit.Test
    fun `test part 2 givens`() {
        val actual = day7Part2(sampleInput)
        assertEquals(-1, actual)
    }
}