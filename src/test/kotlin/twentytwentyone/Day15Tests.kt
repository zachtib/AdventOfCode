package twentytwentyone

import libadvent.grid.Grid
import libadvent.grid.joinToString
import libadvent.resource.asGrid
import libadvent.resource.resourceOf
import kotlin.test.Test
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
    ).asGrid { char -> char.digitToInt() }

    @Test
    fun `test part 1 example`() {
        val actual = day15Part1(sampleInput)
        assertEquals(40, actual)
    }

    @Test
    fun `test scaled grid`() {
        val risks = Grid(2, 2) { row, column ->
            row + column
        }
        println(risks.joinToString(", ") {
            it.toString().padStart(2)
        })
        val scaledGrid = ScaledGrid(risks, 5)
        assertEquals(10, scaledGrid.rows)
        assertEquals(10, scaledGrid.columns)

        println(scaledGrid.joinToString(", ") {
            it.toString().padStart(2)
        })
        assertEquals(9, scaledGrid[9, 8])
        assertEquals(1, scaledGrid[9, 9])
    }

    @Test
    fun `test part 2 example`() {
        val actual = day15Part2(sampleInput)
        assertEquals(315, actual)
    }
}