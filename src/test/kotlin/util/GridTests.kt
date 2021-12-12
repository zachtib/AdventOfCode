package util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GridTests {
    @Test
    fun `test grid indices`() {
        val grid = Grid(5, 5) { a, b -> a * b }
        val indices = grid.indices.asSequence().toList()
        assertEquals(25, indices.size)
    }

    @Test
    fun `test grid size`() {
        val grid = Grid(6, 5) { a, b -> a * b }
        assertEquals(30, grid.size)
    }

    @Test
    fun `test Grid isNotEmpty()`() {
        val grid = Grid(2, 2, ) { a, b -> a * b}
        assertTrue(grid.isNotEmpty())
    }

    @Test

    fun `test toMutableGrid then updating it doesn't affect original`() {
        val grid = Grid(3, 4) { row, column -> row * column }
        assertEquals(6, grid[2, 3])

        val mutableGrid = grid.toMutableGrid()
        mutableGrid[2, 3] = 4

        assertEquals(4, mutableGrid[2, 3])
        assertEquals(6, grid[2, 3])
    }
}