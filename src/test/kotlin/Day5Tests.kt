import util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class Day5Tests {

    val sampleInput = resource {
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
    }.asType { it.toLine() }

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
    fun testPart1Givens() {

    }

    @Test
    fun testPart2Givens() {

    }
}