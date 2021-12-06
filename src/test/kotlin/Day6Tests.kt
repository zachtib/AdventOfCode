import util.asType
import util.resource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Tests {

    private val sampleInput = resource(",") {
        "3,4,3,1,2"
    }.asType { LanternFish(it.toInt()) }

    @Test
    fun testPart1Givens() {
        val actual = day6Part1(sampleInput, printStatus = true)
        assertEquals(5934, actual)
    }

    @Test
    fun testPart1GivensFor18Days() {
        val actual = day6Part1(sampleInput, runForDays = 18, printStatus = true)
        assertEquals(26, actual)
    }

    @Test
    fun testLongFish() {
        val school = LargeLanternFishSchool(sampleInput)
        school.runForDays(18, printStatus = true)
        val actual = school.count()
        assertEquals(26L, actual)
    }

    @Test
    fun testLongFishAgain() {
        val school = LargeLanternFishSchool(sampleInput)
        school.runForDays(80, printStatus = true)
        val actual = school.count()
        assertEquals(5934L, actual)
    }

    @Test
    fun testLongFish256() {
        val school = LargeLanternFishSchool(sampleInput)
        school.runForDays(256, printStatus = true)
        val actual = school.count()

        assertEquals(26984457539, actual)
    }

    @Test
    fun testPart2Givens() {
        val actual: Long = day6Part2(sampleInput)
        assertEquals(26984457539, actual)
    }
}