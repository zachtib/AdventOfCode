package twentytwentyone

import libadvent.resource.asGrid
import libadvent.resource.resourceOf
import kotlin.test.Test
import kotlin.test.assertEquals

class Day9Tests {

    private val sampleInput = resourceOf("""
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678
    """.trimIndent()).asGrid { it.digitToInt() }

    @Test
    fun `test 2dArray loading`() {
        assertEquals(2, sampleInput[0, 0])
        assertEquals(3, sampleInput[1, 0])
        assertEquals(9, sampleInput[2, 0])
        assertEquals(8, sampleInput[2, 1])
        assertEquals(5, sampleInput[2, 2])
    }

    @Test
    fun `test part 1 sample`() {
        val actual = day9Part1(sampleInput)
        assertEquals(15, actual)
    }

    @Test
    fun `test generating basin`() {
        val basin = sampleInput.calculateBasinFromLowPoint(0 to 1)

        assertEquals(setOf(0 to 0, 0 to 1, 1 to 0), basin)
    }

    @Test
    fun `test part 2 sample`() {
        val actual = day9Part2(sampleInput)
        assertEquals(1134, actual)
    }
}