package libadvent.grid

import libadvent.geometry.Bounds
import kotlin.test.Test
import kotlin.test.assertEquals

class BoundedGridTests {
    @Test
    fun `test building a boundedGrid`() {
        val bounds = Bounds(-2, -2, 2, 2)
        val boundedGrid = BoundedGrid(bounds) { c -> "(${c.x}, ${c.y})".padStart(9)}

        println(boundedGrid.joinToString { it })

        val actual = boundedGrid[2, 2].trim()
        assertEquals("(2, 2)", actual)
        val actual1 = boundedGrid[-2, -1].trim()
        assertEquals("(-1, -2)", actual1)
    }
}