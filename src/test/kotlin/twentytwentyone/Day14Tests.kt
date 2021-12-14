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
    ).asPolymerInputs()

    private val sampleTemplate = sampleInput.first
    private val samplePairInsertions = sampleInput.second

    @Test
    fun `test single insertion`() {
        val template = PolymerTemplate("AC")
        val insertions = listOf(PairInsertion('A' to 'C', 'B'))

        val actual = template.applyPairInsertions(insertions)
        assertEquals("ABC", actual.template)
    }

    @Test
    fun `test larger single insertion`() {
        val actual = sampleTemplate.applyPairInsertions(samplePairInsertions)
        assertEquals("NCNBCHB", actual.template)
    }

    @Test
    fun `test that after step 5, it has length 97`() {
        var polymer = sampleTemplate
        repeat(5) {
            polymer = polymer.applyPairInsertions(samplePairInsertions)
        }
        assertEquals(97, polymer.template.length)
    }

    @Test
    fun `test that after step 10, it has length 3073`() {
        var polymer = sampleTemplate
        repeat(10) {
            polymer = polymer.applyPairInsertions(samplePairInsertions)
        }
        assertEquals(3073, polymer.template.length)
    }

    @Test
    fun `test that after step 10, B occurs 1749 times`() {
        var polymer = sampleTemplate
        repeat(10) {
            polymer = polymer.applyPairInsertions(samplePairInsertions)
        }
        assertEquals(1749, polymer.template.count { it == 'B' })
    }

    @Test
    fun `test that after step 10, C occurs 298 times`() {

        var polymer = sampleTemplate
        repeat(10) {
            polymer = polymer.applyPairInsertions(samplePairInsertions)
        }
        assertEquals(298, polymer.template.count { it == 'C' })
    }

    @Test
    fun `test that after step 10, H occurs 161 times`() {
        var polymer = sampleTemplate
        repeat(10) {
            polymer = polymer.applyPairInsertions(samplePairInsertions)
        }
        assertEquals(161, polymer.template.count { it == 'H' })
    }

    @Test
    fun `test that after step 10, N occurs 865 times`() {
        var polymer = sampleTemplate
        repeat(10) {
            polymer = polymer.applyPairInsertions(samplePairInsertions)
        }
        assertEquals(865, polymer.template.count { it == 'N' })
    }

    @Test
    fun `test part1 sample`() {
        val actual = day14Part1(sampleTemplate, samplePairInsertions)
        assertEquals(1588, actual)
    }

    @Test
    fun `test part2 sample`() {
        val actual = day14Part2()
        assertEquals(0, actual)
    }
}