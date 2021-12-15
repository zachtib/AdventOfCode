package libadvent.geometry

import kotlin.test.Test
import kotlin.test.assertEquals

class CoordinateTests {
    @Test
    fun `test coordinate equality`() {
        val first = gridCoordinate(3, 4)
        val second = gridCoordinate(3, 4)
        assertEquals(first, second)
    }

    @Test
    fun `test mutable coordinate equality`() {
        val first = gridCoordinate(3, 4)
        val second = mutableGridCoordinate(3, 4)
        assertEquals(first, second)
    }
}