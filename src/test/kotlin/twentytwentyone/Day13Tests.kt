package twentytwentyone

import libadvent.resource.resourceOf
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Tests {

    private val sampleInput = resourceOf("""
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0

        fold along y=7
        fold along x=5
    """.trimIndent()).asTransparentOrigami()

    private val samplePoints = sampleInput.first
    private val sampleFolds = sampleInput.second

    @Test
    fun `test part 1 example`() {
        val actual = day13Part1(samplePoints, sampleFolds, printGridSteps = true)
        assertEquals(17, actual)
    }

    @Test
    fun `test part 2 example`() {
        val expected = """
            #####
            #...#
            #...#
            #...#
            #####
            .....
            .....
        """.trimIndent()
        val actual = day13Part2(samplePoints, sampleFolds, printGridSteps = true)
        assertEquals(expected, actual)
    }
}