package twentytwentyone

import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Tests {

    private val sampleInput = "target area: x=20..30, y=-10..-5".toTargetArea()

    @Test
    fun `test part 1 example`() {
        val actual = day17Part1(sampleInput)
        val expected = 45
        assertEquals(expected, actual)
    }

    @Test
    fun `test part 2 example`() {
        val actual = day17Part2(sampleInput)
        val expected = 112
        assertEquals(expected, actual)
    }

}