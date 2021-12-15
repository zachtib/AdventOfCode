package libadvent.algorithms

import libadvent.resource.asGrid
import libadvent.resource.resourceOf
import kotlin.test.Test
import kotlin.test.assertEquals

class DijkstraTests {
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
        char.digitToInt()
    }

    @Test
    fun `test small dijkstra grid`() {
        val actual = solveDijkstraForGrid(sampleInput)
        assertEquals(40, actual)
    }
}