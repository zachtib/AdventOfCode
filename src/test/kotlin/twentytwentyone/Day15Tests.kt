package twentytwentyone

import libadvent.resource.asGrid
import libadvent.resource.resourceOf
import kotlin.test.assertEquals

class Day15Tests {
    private val sampleInput = resourceOf(
        """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
        """.trimIndent()
    ).asGrid { char ->
        RiskLevel(char.digitToInt())
    }


    fun `test part 1 example`() {
        val actual = day15Part1(sampleInput)
        assertEquals(0, actual)
    }

    fun `test part 2 example`() {
        val actual = day15Part2(sampleInput)
        assertEquals(0, actual)
    }
}