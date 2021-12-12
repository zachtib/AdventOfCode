package libadvent

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringUtilsTests {

    @Test
    fun `test allUpperCase returns true for INPUT`() {
        val result = "INPUT".allUpperCase()
        assertTrue(result)
    }

    @Test
    fun `test allUpperCase returns false for Input`() {
        val result = "Input".allUpperCase()
        assertFalse(result)
    }

    @Test
    fun `test allUpperCase returns false for input`() {
        val result = "input".allUpperCase()
        assertFalse(result)
    }

    @Test
    fun `test allLowerCase returns false for INPUT`() {
        val result = "INPUT".allLowerCase()
        assertFalse(result)
    }

    @Test
    fun `test allLowerCase returns false for Input`() {
        val result = "Input".allLowerCase()
        assertFalse(result)
    }

    @Test
    fun `test allLowerCase returns true for input`() {
        val result = "input".allLowerCase()
        assertTrue(result)
    }
}