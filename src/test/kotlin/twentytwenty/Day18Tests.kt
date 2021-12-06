package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Test

class Day18Tests {
    @Test
    fun `test tokenizer`() {
        val actual = tokenize("1 + 2 * 3 + 4 * 5 + 6")
        val expected = listOf('1', '+', '2', '*', '3', '+', '4', '*', '5', '+', '6')
        assertEquals(expected, actual)
    }

    @Test
    fun `test evaluation`() {
        val actual = evaluate("2 * 3 + (4 * 5)")
        assertEquals(26, actual)
    }

    @Test
    fun `test evaluation 2`() {
        val actual = evaluate("5 + (8 * 3 + 9 + 3 * 4 * 3)")
        assertEquals(437, actual)
    }

    @Test
    fun `test evaluation 3`() {
        val actual = evaluate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")
        assertEquals(12240, actual)
    }

    @Test
    fun `test evaluation 4`() {
        val actual = evaluate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")
        assertEquals(13632, actual)
    }

    @Test
    fun `test advanced evaluation`() {
        val actual = evaluate("2 * 3 + (4 * 5)", true)
        assertEquals(46, actual)
    }

    @Test
    fun `test advanced  evaluation 2`() {
        val actual = evaluate("5 + (8 * 3 + 9 + 3 * 4 * 3)", true)
        assertEquals(1445, actual)
    }

    @Test
    fun `test advanced  evaluation 3`() {
        val actual = evaluate("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", true)
        assertEquals(669060, actual)
    }

    @Test
    fun `test advanced  evaluation 4`() {
        val actual = evaluate("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", true)
        assertEquals(23340, actual)
    }
}