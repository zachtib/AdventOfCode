package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Test

class Day3Tests {
    private val inputField = """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#
        """.trimIndent()

    @Test
    fun `test part 1 givens`() {
        val testField = TreeField(inputField.lines())

        val actual = testField.traverse(Slope(right = 3, down = 1))
        assertEquals(7, actual)
    }

    @Test
    fun `test part 2 givens`() {
        val testField = TreeField(inputField.lines())

        assertEquals(2, testField.traverse(Slope(right = 1, down = 1)))
        assertEquals(7, testField.traverse(Slope(right = 3, down = 1)))
        assertEquals(3, testField.traverse(Slope(right = 5, down = 1)))
        assertEquals(4, testField.traverse(Slope(right = 7, down = 1)))
        assertEquals(2, testField.traverse(Slope(right = 1, down = 2)))

        val actual = testField.multiTraverse(listOf(
                Slope(right = 1, down = 1),
                Slope(right = 3, down = 1),
                Slope(right = 5, down = 1),
                Slope(right = 7, down = 1),
                Slope(right = 1, down = 2),
        ))
        assertEquals(336, actual)
    }
}