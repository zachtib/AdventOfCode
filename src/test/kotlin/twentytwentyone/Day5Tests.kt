package twentytwentyone

import libadvent.geometry.*
import res.asType
import res.resourceOf
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day5Tests {

    private val sampleInput = resourceOf(
        """
            0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2
        """.trimIndent()
    ).asType { it.toLine() }

    @Test
    fun testPointAndLineInit() {
        val p0 = Point(0, 0)
        val p1 = Point(3, 4)
        val p2 = Point(5, 5)

        val l0 = p0 lineTo p1
        val l1 = p1 lineTo p2

        assertEquals(0, l0.x1)
        assertEquals(0, l0.y1)
        assertEquals(3, l0.x2)
        assertEquals(4, l0.y2)
        assertEquals(3, l1.x1)
        assertEquals(4, l1.y1)
        assertEquals(5, l1.x2)
        assertEquals(5, l1.y2)
    }

    @Test
    fun testSampleParsing() {
        val line = sampleInput.first()

        assertEquals(0, line.x1)
        assertEquals(9, line.y1)
        assertEquals(5, line.x2)
        assertEquals(9, line.y2)
    }

    @Test
    fun testPointsAlongLine() {
        val line = sampleInput.first()
        val points = line.pointsAlong()

        assertEquals(listOf(
            Point(0, 9),
            Point(1, 9),
            Point(2, 9),
            Point(3, 9),
            Point(4, 9),
            Point(5, 9),
        ), points)
    }

    @Test
    fun testPointsAlongDiagonal1() {
        val line = "1,1 -> 3,3".toLine()
        assertContentEquals(listOf(
            Point(1, 1),
            Point(2, 2),
            Point(3, 3),
        ), line.pointsAlong())
    }

    @Test
    fun testPointsAlongDiagonal2() {
        val line = "9,7 -> 7,9".toLine()
        assertContentEquals(listOf(
            Point(9, 7),
            Point(8, 8),
            Point(7, 9),
        ), line.pointsAlong())
    }

    @Test
    fun testOtherDiagonadayl() {
        val line = "6,4 -> 2,0".toLine()
        assertTrue(line.isDiagonal)
    }

    @Test
    fun testDiagonal() {
        val line = "0,0 -> 8,8".toLine()
        assertTrue(line.isDiagonal)

        val points = line.pointsAlong()
        assertContentEquals(listOf(
            Point(0, 0),
            Point(1, 1),
            Point(2, 2),
            Point(3, 3),
            Point(4, 4),
            Point(5, 5),
            Point(6, 6),
            Point(7, 7),
            Point(8, 8),
        ), points)
    }

    @Test
    fun testReverseDiagonal() {
        val line = "8,0 -> 0,8".toLine()
        assertTrue(line.isDiagonal)

        val points = line.pointsAlong()
        assertContentEquals(listOf(
            Point(8, 0),
            Point(7, 1),
            Point(6, 2),
            Point(5, 3),
            Point(4, 4),
            Point(3, 5),
            Point(2, 6),
            Point(1, 7),
            Point(0, 8),
        ), points)
    }

    @Test
    fun testPart1Givens() {
        val actual = day5Part1(sampleInput)
        assertEquals(5, actual)
    }

    @Test
    fun testPart2Givens() {
        val actual = day5Part2(sampleInput)
        assertEquals(12, actual)
    }
}