package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Test

class Day13Tests {
    @Test
    fun `test pt1 example`() {
        val input = """
            939
            7,13,x,x,59,x,31,19
        """.trimIndent()
        val (ts, buses) = parseInputAssumingOutOfServiceBuses(input)
        val result = findEarliestBus(ts, buses)
        assertEquals(59, result.id)
    }

    @Test
    fun `test pt1 example wait time`() {
        val input = """
            939
            7,13,x,x,59,x,31,19
        """.trimIndent()
        val (ts, buses) = parseInputAssumingOutOfServiceBuses(input)
        val result = findEarliestBusTimeWait(ts, buses)
        assertEquals(295, result)
    }

    @Test
    fun `test pt2 example 1`() {
        val buses = parseBuses("17,x,13,19")
        val actual = findTimeStampForBuses(buses)
        assertEquals(3417, actual)
    }

    @Test
    fun `test pt2 example 2`() {
        val buses = parseBuses("67,7,59,61")
        val actual = findTimeStampForBuses(buses)
        assertEquals(754018, actual)
    }

    @Test
    fun `test pt2 example 3`() {
        val buses = parseBuses("67,x,7,59,61")
        val actual = findTimeStampForBuses(buses)
        assertEquals(779210, actual)
    }

    @Test
    fun `test pt2 example 4`() {
        val buses = parseBuses("67,7,x,59,61")
        val actual = findTimeStampForBuses(buses)
        assertEquals(1261476, actual)
    }

    @Test
    fun `test pt2 example 5`() {
        val buses = parseBuses("1789,37,47,1889")
        val actual = findTimeStampForBuses(buses)
        assertEquals(1202161486, actual)
    }
}