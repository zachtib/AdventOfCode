package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class Day15Tests {

    private lateinit var memoryGame: MemoryGame

    @Before
    fun setUp() {
        memoryGame = MemoryGame()
    }

    @Test
    fun `test pt1 given 0`() {
        memoryGame.sayAll(0, 3, 6)
        assertEquals(0, memoryGame.next())
        assertEquals(3, memoryGame.next())
        assertEquals(3, memoryGame.next())
        assertEquals(1, memoryGame.next())
        assertEquals(0, memoryGame.next())
        assertEquals(4, memoryGame.next())
        assertEquals(0, memoryGame.next())
    }

    @Test
    fun `test pt1 given 1`() {
        memoryGame.sayAll(1, 3, 2)
        memoryGame.runUntilRound(2020)
        assertEquals(1, memoryGame.lastSpoken)
    }

    @Test
    fun `test pt1 given 2`() {
        memoryGame.sayAll(2, 1, 3)
        memoryGame.runUntilRound(2020)
        assertEquals(10, memoryGame.lastSpoken)
    }

    @Test
    fun `test pt1 given 3`() {
        memoryGame.sayAll(1, 2, 3)
        memoryGame.runUntilRound(2020)
        assertEquals(27, memoryGame.lastSpoken)
    }

    @Test
    fun `test pt1 given 4`() {
        memoryGame.sayAll(2, 3, 1)
        memoryGame.runUntilRound(2020)
        assertEquals(78, memoryGame.lastSpoken)
    }

    @Test
    fun `test pt1 given 5`() {
        memoryGame.sayAll(3, 2, 1)
        memoryGame.runUntilRound(2020)
        assertEquals(438, memoryGame.lastSpoken)
    }

    @Test
    fun `test pt1 given 6`() {
        memoryGame.sayAll(3, 1, 2)
        memoryGame.runUntilRound(2020)
        assertEquals(1836, memoryGame.lastSpoken)
    }

}