package libadvent

import kotlin.test.Test
import kotlin.test.assertEquals

class StackTests {
    @Test
    fun testStackIteration() {
        val stack = emptyStack<Int>()
        stack.push(1)
        stack.push(2)
        stack.push(3)

        val expected = mutableListOf(3, 2, 1)

        for (actual in stack) {
            assertEquals(expected.removeFirst(), actual)
        }
    }
}