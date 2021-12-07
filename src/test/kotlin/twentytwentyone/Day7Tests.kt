package twentytwentyone

import res.asInts
import res.resourceOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Day7Tests {
    private val sampleInput = resourceOf(
        "16,1,2,0,4,2,7,1,2,14",
        ","
    ).asInts()

    @Test
    fun `test part 1 givens`() {
        val actual = day7Part1(sampleInput)
        assertEquals(37, actual)
    }

    @Test
    fun `test that position 1 costs 41 fuel`() {
        val fuelCosts = calculateAllAlignmentFuelCosts(sampleInput)
        val fuelCost: Int? = fuelCosts[1]

        assertNotNull(fuelCost, "Result was missing value for position 1")
        assertEquals(41, fuelCost)
    }

    @Test
    fun `test that position 2 costs 37 fuel`() {
        val fuelCosts = calculateAllAlignmentFuelCosts(sampleInput)
        val fuelCost: Int? = fuelCosts[2]

        assertNotNull(fuelCost, "Result was missing value for position 2")
        assertEquals(37, fuelCost)
    }

    @Test
    fun `test that position 3 costs 39 fuel`() {
        val fuelCosts = calculateAllAlignmentFuelCosts(sampleInput)
        val fuelCost: Int? = fuelCosts[3]

        assertNotNull(fuelCost, "Result was missing value for position 3")
        assertEquals(39, fuelCost)
    }

    @Test
    fun `test that position 10 costs 71 fuel`() {
        val fuelCosts = calculateAllAlignmentFuelCosts(sampleInput)
        val fuelCost: Int? = fuelCosts[10]

        assertNotNull(fuelCost, "Result was missing value for position 10")
        assertEquals(71, fuelCost)
    }

    @Test
    fun `test part 2 givens`() {
        val actual = day7Part2(sampleInput)
        assertEquals(168, actual)
    }

    @Test
    fun `test that revised cost for position 5 is 168 fuel`() {
        val fuelCosts = calculateRevisedFuelCosts(sampleInput)
        val fuelCost: Int? = fuelCosts[5]

        assertNotNull(fuelCost, "Result was missing value for position 5")
        assertEquals(168, fuelCost)
    }

    @Test
    fun `test that revised cost for position 2 is 206 fuel`() {
        val fuelCosts = calculateRevisedFuelCosts(sampleInput)
        val fuelCost: Int? = fuelCosts[2]

        assertNotNull(fuelCost, "Result was missing value for position 2")
        assertEquals(206, fuelCost)
    }
}