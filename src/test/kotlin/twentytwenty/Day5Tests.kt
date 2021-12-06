package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Test

class Day5Tests {

    @Test
    fun `test takeFront`() {
        val actual = (0..127).takeFront()
        assertEquals(0..63, actual)
    }

    @Test
    fun `test takeFront with nonzero index`() {
        val actual = (32..63).takeFront()
        assertEquals(32..47, actual)
    }

    @Test
    fun `test takeBack`() {
        val actual = (0..63).takeBack()
        assertEquals(32..63, actual)
    }

    @Test
    fun `test calculateRow`() {
        val actual = calculateRow(0..127, "FBFBBFF")
        assertEquals(44, actual)
    }

    @Test
    fun `test calculateColumn`() {
        val actual = calculateColumn(0..7, "RLR")
        assertEquals(5, actual)
    }

    @Test
    fun `test given examples`() {
        assertEquals(567, calculateSeat("BFFFBBFRRR"))
        assertEquals(119, calculateSeat("FFFBBBFRRR"))
        assertEquals(820, calculateSeat("BBFFBBFRLL"))
    }
}