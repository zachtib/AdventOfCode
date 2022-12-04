package twentytwentytwo

import libadvent.resource.asStrings
import libadvent.resource.resourceOf
import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Tests {

    val sampleInput = resourceOf(
        """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent()
    ).asStrings()

    @Test
    fun `test halving string`() {
        val (first, second) = "abcdef".halve()
        assertEquals("abc", first)
        assertEquals("def", second)
    }

    @Test
    fun `test part 1 example`() {
        val actual = day3Part1(sampleInput)
        assertEquals(157, actual)
    }

    @Test
    fun `test part 2 example`() {
        val actual = day3Part2(sampleInput)
        assertEquals(70, actual)
    }
}