package twentytwentyone

import res.asGrid
import res.resourceOf
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Tests {

    private val sampleInput = resourceOf("""
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
    """.trimIndent())
        .asGrid { it.digitToInt() }

    @Test
    fun `test part 1 givens`() {
        val actual = day11Part1(sampleInput)
        assertEquals(1656, actual)
    }

    @Test
    fun `test part 2 givens`() {
        val actual = day11Part2()
        assertEquals(0, actual)
    }
}