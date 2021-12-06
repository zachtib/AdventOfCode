package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Test

class Day1Tests {
    @Test
    fun `test list sum pairs`() {
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val actual = numbers.getPairsSummingTo(10)
        val expected = listOf(
                1 to 9,
                2 to 8,
                3 to 7,
                4 to 6,
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test list sum triples`() {
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val actual = numbers.getTriplesSummingTo(10)
        val expected = listOf(
                Triple(1, 2, 7),
                Triple(1, 3, 6),
                Triple(1, 4, 5),
                Triple(2, 3, 5),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test triple product function`() {
        val triple = Triple(2, 3, 4)
        val expected = 24
        val actual = triple.product()
        assertEquals(expected, actual)
    }

    @Test
    fun `test part1 given`() {
        val given = listOf(1721, 979, 366, 299, 675, 1456)

        val pairs = given.getPairsSummingTo(2020)
        assertEquals(1, pairs.size)
        val pair = pairs.first()
        assertEquals(1721 to 299, pair)

        val expected = 514579
        val actual = getPairsProduct(given)
        assertEquals(expected, actual)
    }

    @Test
    fun `test part2 given`() {
        val given = listOf(1721, 979, 366, 299, 675, 1456)

        val triples = given.getTriplesSummingTo(2020)
        assertEquals(1, triples.size)
        val triple = triples.first()
        assertEquals(Triple(979, 366, 675), triple)

        val expected = 241861950
        val actual = getTriplesProduct(given)
        assertEquals(expected, actual)
    }
}