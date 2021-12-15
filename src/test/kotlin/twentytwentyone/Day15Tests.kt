package twentytwentyone

import libadvent.geometry.gridCoordinate
import libadvent.grid.gridOf
import libadvent.grid.map
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
    ).asGrid { char ->
        RiskLevel(char.digitToInt())
    }

    @Test
    fun `test finding paths`() {
        val grid = gridOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
            arrayOf(7, 8, 9),
        )
        val paths = findAllAvailablePaths(grid, gridCoordinate(0, 0))
        for (path in paths) {
            println(path)
        }
        assertEquals(12, paths.size)
    }

    @Test
    fun `test naive path`() {
        val grid = gridOf(
            arrayOf(1, 2, 5),
            arrayOf(4, 3, 6),
            arrayOf(7, 8, 9),
        ).map { RiskLevel(it) }

        val expectedPath = listOf(
            gridCoordinate(0, 0),
            gridCoordinate(0, 1),
            gridCoordinate(1, 1),
            gridCoordinate(1, 2),
            gridCoordinate(2, 2),
        )

        val (path, level) = grid.determineNaivePath()

        assertEquals(expectedPath, path)
        assertEquals(20, level.level)
    }

    @Test
    fun `test part 1 example`() {
        val actual = day15Part1(sampleInput)
        assertEquals(40, actual)
    }

    fun `test part 2 example`() {
        val actual = day15Part2(sampleInput)
        assertEquals(0, actual)
    }
}