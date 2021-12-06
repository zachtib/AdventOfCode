package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Day10Tests {
    private val example = """
        16
        10
        15
        5
        1
        11
        7
        19
        6
        12
        4
    """.trimIndent().lines().map { it.toInt() }

    val largerExample = """
            28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3
        """.trimIndent().lines().map { it.toInt() }

    @Test
    fun `test small example`() {
        val expected = JoltageDistribution(7, 0, 5)
        val actual = findJoltageDistribution(example)
        assertEquals(expected, actual)
    }


    @Test
    fun `test larger example`() {
        val expected = JoltageDistribution(22, 0, 10)
        val actual = findJoltageDistribution(largerExample)
        assertEquals(expected, actual)
    }

    @Test
    fun `traverse small example`() {
        val results = """
            (0), 1, 4, 5, 6, 7, 10, 11, 12, 15, 16, 19, (22)
            (0), 1, 4, 5, 6, 7, 10, 12, 15, 16, 19, (22)
            (0), 1, 4, 5, 7, 10, 11, 12, 15, 16, 19, (22)
            (0), 1, 4, 5, 7, 10, 12, 15, 16, 19, (22)
            (0), 1, 4, 6, 7, 10, 11, 12, 15, 16, 19, (22)
            (0), 1, 4, 6, 7, 10, 12, 15, 16, 19, (22)
            (0), 1, 4, 7, 10, 11, 12, 15, 16, 19, (22)
            (0), 1, 4, 7, 10, 12, 15, 16, 19, (22)
        """.trimIndent()
        val expected: List<List<Int>> = results.lines().map { line ->
            line.split(", ").map {
                it.removeSurrounding("(", ")").toInt()
            }
        }
        val actual = findJoltageArrangements(example)
        for (item in expected) {
            assertTrue(item in actual)
        }
    }

    @Test
    fun `traverse larger example`() {
        val results = """
            (0), 1, 2, 3, 4, 7, 8, 9, 10, 11, 14, 17, 18, 19, 20, 23, 24, 25, 28, 31, 32, 33, 34, 35, 38, 39, 42, 45, 46, 47, 48, 49, (52)
            (0), 1, 2, 3, 4, 7, 8, 9, 10, 11, 14, 17, 18, 19, 20, 23, 24, 25, 28, 31, 32, 33, 34, 35, 38, 39, 42, 45, 46, 47, 49, (52)
            (0), 1, 2, 3, 4, 7, 8, 9, 10, 11, 14, 17, 18, 19, 20, 23, 24, 25, 28, 31, 32, 33, 34, 35, 38, 39, 42, 45, 46, 48, 49, (52)
            (0), 1, 2, 3, 4, 7, 8, 9, 10, 11, 14, 17, 18, 19, 20, 23, 24, 25, 28, 31, 32, 33, 34, 35, 38, 39, 42, 45, 46, 49, (52)
            (0), 1, 2, 3, 4, 7, 8, 9, 10, 11, 14, 17, 18, 19, 20, 23, 24, 25, 28, 31, 32, 33, 34, 35, 38, 39, 42, 45, 47, 48, 49, (52)
            (0), 3, 4, 7, 10, 11, 14, 17, 20, 23, 25, 28, 31, 34, 35, 38, 39, 42, 45, 46, 48, 49, (52)
            (0), 3, 4, 7, 10, 11, 14, 17, 20, 23, 25, 28, 31, 34, 35, 38, 39, 42, 45, 46, 49, (52)
            (0), 3, 4, 7, 10, 11, 14, 17, 20, 23, 25, 28, 31, 34, 35, 38, 39, 42, 45, 47, 48, 49, (52)
            (0), 3, 4, 7, 10, 11, 14, 17, 20, 23, 25, 28, 31, 34, 35, 38, 39, 42, 45, 47, 49, (52)
            (0), 3, 4, 7, 10, 11, 14, 17, 20, 23, 25, 28, 31, 34, 35, 38, 39, 42, 45, 48, 49, (52)
        """.trimIndent()
        val expected: List<List<Int>> = results.lines().map { line ->
            line.split(", ").map {
                it.removeSurrounding("(", ")").toInt()
            }
        }
        val actual = findJoltageArrangements(largerExample)
        for (item in expected) {
            assertTrue(item in actual)
        }
    }

    @Test
    fun `count small paths`() {
        val actual = countJoltageArrangements(example)
        assertEquals(8L, actual)
    }
}