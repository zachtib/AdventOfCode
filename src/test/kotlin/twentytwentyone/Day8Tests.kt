package twentytwentyone

import res.asType
import res.resourceOf
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Day8Tests {
    private val sampleNote = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

    private val sampleInput = resourceOf(
        """
            be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
            edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
            fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
            fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
            aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
            fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
            dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
            bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
            egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
            gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
        """.trimIndent()
    ).asType { it.asSevenSegmentNotes() }

    @Test
    fun `test input parsing`() {
        val actual = sampleNote.asSevenSegmentNotes()

        assertContentEquals(listOf(
            "acedgfb",
            "cdfbe",
            "gcdfa",
            "fbcad",
            "dab",
            "cefabd",
            "cdfgeb",
            "eafb",
            "cagedb",
            "ab",
        ), actual.signalPatterns)
        assertContentEquals(listOf(
            "cdfeb",
            "fcadb",
            "cdfeb",
            "cdbaf",
        ), actual.outputValue)
    }

    @Test
    fun `test part 1 example`() {
        val actual = day8Part1(sampleInput)
        assertEquals(26, actual)
    }

    @Test
    fun `test part 2 example`() {
        val actual = day8Part2()
    }
}