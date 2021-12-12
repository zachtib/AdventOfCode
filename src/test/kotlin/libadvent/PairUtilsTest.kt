package libadvent

import kotlin.test.Test
import kotlin.test.assertEquals

class PairUtilsTest {
    @Test
    fun `test toPair`() {
        val actual = listOf(1, 2).toPair()

        assertEquals(1, actual.first)
        assertEquals(2, actual.second)
    }

    @Test
    fun `test itemsPairedWith`() {
        val collection = listOf(
            1 to 2,
            1 to 3,
            2 to 3,
            2 to 4,
            3 to 4,
            4 to 1,
            5 to 2,
            5 to 3,
        )
        val actual = collection.itemsPairedWith(1)
        val expected = setOf(2, 3, 4)
        assertEquals(expected, actual)
    }
}