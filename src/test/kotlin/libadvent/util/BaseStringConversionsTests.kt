package libadvent.util

import kotlin.test.Test
import kotlin.test.assertEquals

class BaseStringConversionsTests {
    @Test
    fun `test direct hex string conversion`() {
        val hex = "620080001611562C8802118E34"
        val result = hex.hexStringToBinary()
        val expected = "1100010000000001000000000000000000101100001000101010110001011001000100000000010000100011000111000110100"
        assertEquals(expected, result)
    }
}