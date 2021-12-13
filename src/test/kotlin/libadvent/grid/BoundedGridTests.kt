package libadvent.grid

import libadvent.geometry.Bounds
import kotlin.test.Test
import kotlin.test.assertEquals

class BoundedGridTests {
    @Test
    fun `test building a boundedGrid`() {
        val bounds = Bounds(-2, -2, 2, 2)
        val boundedGrid = BoundedGrid(bounds) { row, column -> "($row, $column)".padStart(9)}

        for (row in boundedGrid.rowIndices) {
            for (column in boundedGrid.columnIndices) {
                print(boundedGrid[row, column])
            }
            println()
        }

        val actual = boundedGrid[2, 2].trim()
        assertEquals("(2, 2)", actual)
        val actual1 = boundedGrid[-1, -2].trim()
        assertEquals("(-1, -2)", actual1)
    }
}