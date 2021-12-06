package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Test

class Day16Tests {
    private val sample = """
        class: 1-3 or 5-7
        row: 6-11 or 33-44
        seat: 13-40 or 45-50

        your ticket:
        7,1,14

        nearby tickets:
        7,3,47
        40,4,50
        55,2,20
        38,6,12
    """.trimIndent()

    @Test
    fun `test parsing`() {
        val actual = parseInput(sample)
        val expected = ParsedInput(
                rules = listOf(
                        Rule("class", 1..3, 5..7),
                        Rule("row", 6..11, 33..44),
                        Rule("seat", 13..40, 45..50),
                ),
                yourTicket = listOf(7, 1, 14),
                otherTickets = listOf(
                        listOf(7, 3, 47),
                        listOf(40, 4, 50),
                        listOf(55, 2, 20),
                        listOf(38, 6, 12),
                ),
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test scanning error rate`() {
        val sampleInput = parseInput(sample)
        val actual = calculateScanningErrorRate(sampleInput.rules, sampleInput.otherTickets)
        assertEquals(71, actual)
    }

    private val pt2Sample = """
        class: 0-1 or 4-19
        row: 0-5 or 8-19
        seat: 0-13 or 16-19

        your ticket:
        11,12,13

        nearby tickets:
        3,9,18
        15,1,5
        5,14,9
    """.trimIndent()

    @Test
    fun `test solving`() {
        val sampleInput = parseInput(pt2Sample)
        val actual = solveMyTicket(sampleInput.rules, sampleInput.yourTicket, sampleInput.otherTickets.filter {
            isTicketValid(it, sampleInput.rules)
        })
        val expected = mapOf(
                "class" to 12,
                "row" to 11,
                "seat" to 13,
        )
        assertEquals(expected, actual)
    }
}