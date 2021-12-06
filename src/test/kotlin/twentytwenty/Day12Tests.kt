package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Test

class Day12Tests {
    @Test
    fun `test movement parsing`() {
        val movement = "N12".toMovement()
        assertEquals(Direction.N, movement.direction)
        assertEquals(12, movement.amount)
    }

    @Test
    fun `test example directions`() {
        val movements = """
            F10
            N3
            F7
            R90
            F11
        """.trimIndent().lines().map { it.toMovement() }

        val ship = SimpleShip()
        for (movement in movements) {
            ship.move(movement)
        }
        assertEquals(17, ship.east)
        assertEquals(-8, ship.north)
        assertEquals(25, ship.manhattanDistance())
    }

    @Test
    fun `test waypoint directions`() {
        val movements = """
            F10
            N3
            F7
            R90
            F11
        """.trimIndent().lines().map { it.toMovement() }

        val ship = WaypointShip()
        for (movement in movements) {
            ship.move(movement)
        }
        assertEquals(286, ship.manhattanDistance())
    }
}