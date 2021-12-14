package twentytwentyone

import res.resourceOf
import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Tests {
    private val sampleInput = resourceOf("""
        NNCB

        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C
    """.trimIndent()
    )

    @Test
    fun `test part1 sample`() {
        val actual = day14Part1()
        assertEquals(0, actual)
    }

    @Test
    fun `test part2 sample`() {
        val actual = day14Part2()
        assertEquals(0, actual)
    }
}