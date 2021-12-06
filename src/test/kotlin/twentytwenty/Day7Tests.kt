package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Day7Tests {
    private val given = """
        light red bags contain 1 bright white bag, 2 muted yellow bags.
        dark orange bags contain 3 bright white bags, 4 muted yellow bags.
        bright white bags contain 1 shiny gold bag.
        muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
        shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
        dark olive bags contain 3 faded blue bags, 4 dotted black bags.
        vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
        faded blue bags contain no other bags.
        dotted black bags contain no other bags.
    """.trimIndent()

    private val pt2Given = """
        shiny gold bags contain 2 dark red bags.
        dark red bags contain 2 dark orange bags.
        dark orange bags contain 2 dark yellow bags.
        dark yellow bags contain 2 dark green bags.
        dark green bags contain 2 dark blue bags.
        dark blue bags contain 2 dark violet bags.
        dark violet bags contain no other bags.
    """.trimIndent()

    @Test
    fun `test rule parsing`() {
        val rules = given.lines().map { parseBagRule(it) }
        assertEquals(9, rules.size)
    }

    @Test
    fun `test collect colors contains all colors`() {
        val rules = given.lines().map { parseBagRule(it) }
        val colors = collectColors(rules)

        assertTrue(colors.contains(BagColor("shiny gold")))
    }

    @Test
    fun `test given`() {
        val rules = given.lines().map { parseBagRule(it) }
        val actual = whatCanHold(rules, BagColor("shiny gold"))

        assertEquals(4, actual.size)
    }

    @Test
    fun `test given pt2`() {
        val rules = pt2Given.lines().map { parseBagRule(it) }
        val actual = numberOfBagsInside(rules, BagColor("shiny gold"))

        assertEquals(126, actual)
    }
}