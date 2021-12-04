import util.asComplexType
import util.resource
import util.toListOfInts
import kotlin.test.Test
import kotlin.test.assertEquals


class Day4Tests {
    private val sampleInput = resource {
        """
            7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

            22 13 17 11  0
             8  2 23  4 24
            21  9 14 16  7
             6 10  3 18  5
             1 12 20 15 19
            
             3 15  0  2 22
             9 18 13 17  5
            19  8  7 25 23
            20 11 10 24  4
            14 21 16 12  6
            
            14 21 17 24  4
            10 16 15  9 19
            18  8 23 26 20
            22 11 13  6  5
             2  0 12  3  7
        """.trimIndent()
    }.asComplexType {
        BingoBoardsWithInputs(
            inputs = takeOne { it.toListOfInts() },
            boards = takeRemaining { it.toBingoBoard() },
        )
    }

    @Test
    fun testResourceLoading() {
        assertEquals(27, sampleInput.inputs.size)
        assertEquals(3, sampleInput.boards.size)
    }

    @Test
    fun testPart1() {
        val actual = day4Part1(sampleInput)
        assertEquals(0, actual)
    }

    @Test
    fun testPart2() {
        val actual = day4Part2(sampleInput)
        assertEquals(0, actual)
    }
}