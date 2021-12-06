package twentytwenty

import org.junit.Assert.*
import org.junit.Test

class Day17Tests {
    @Test
    fun `test neighbors`() {
        val point = Point3D(0, 0, 0)
        val actual = point.neighbors()
        val expected = listOf(
                Point3D(-1, -1, -1),
                Point3D(-1, -1, 0),
                Point3D(-1, -1, 1),

                Point3D(-1, 0, -1),
                Point3D(-1, 0, 0),
                Point3D(-1, 0, 1),

                Point3D(-1, 1, -1),
                Point3D(-1, 1, 0),
                Point3D(-1, 1, 1),

                Point3D(0, -1, -1),
                Point3D(0, -1, 0),
                Point3D(0, -1, 1),

                Point3D(0, 0, -1),
                Point3D(0, 0, 1),

                Point3D(0, 1, -1),
                Point3D(0, 1, 0),
                Point3D(0, 1, 1),

                Point3D(1, -1, -1),
                Point3D(1, -1, 0),
                Point3D(1, -1, 1),

                Point3D(1, 0, -1),
                Point3D(1, 0, 0),
                Point3D(1, 0, 1),

                Point3D(1, 1, -1),
                Point3D(1, 1, 0),
                Point3D(1, 1, 1),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test initialization`() {
        val input = """
            .#.
            ..#
            ###
        """.trimIndent()
        val cubes = ConwayCubes(input.map2dInputTo { x, y -> Point3D(x, y, z = 0) })

        assertTrue(cubes.isActive(Point3D(0, 1, 0)))
        assertFalse(cubes.isActive(Point3D(1, 1, 0)))
    }

    @Test
    fun `test startup`() {
        val input = """
            .#.
            ..#
            ###
        """.trimIndent()
        val cubes = ConwayCubes(input.map2dInputTo { x, y -> Point3D(x, y, z = 0) })
        cubes.runCycles(6)

        assertEquals(112, cubes.countActive())
    }

    @Test
    fun `test 4d startup`() {
        val input = """
            .#.
            ..#
            ###
        """.trimIndent()
        val cubes = ConwayCubes(input.map2dInputTo { x, y -> Point4D(x, y, z = 0, w = 0) })
        cubes.runCycles(6)

        assertEquals(848, cubes.countActive())
    }
}