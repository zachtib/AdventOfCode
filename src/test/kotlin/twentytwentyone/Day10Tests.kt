package twentytwentyone

import res.asStrings
import res.resourceOf
import kotlin.test.*

class Day10Tests {
    private val sampleInput = resourceOf(
        """
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]
        """.trimIndent()
    ).asStrings()

    @Test
    fun `test provided valid inputStrings`() {
        val inputStrings = listOf(
            "()", "[]", "([])", "{()()()}", "<([{}])>",
            "[<>({}){}[([])<>]]", "(((((((((())))))))))"
        )
        for (inputString in inputStrings) {
            val validationResult = validateChunkLine(inputString)
            assertEquals(ChunkValidationResult.Valid, validationResult)
        }
    }

    @Test
    fun `test provided corrupt inputStrings`() {
        val inputStrings = listOf("(]", "{()()()>", "(((()))}", "<([]){()}[{}])")
        for (inputString in inputStrings) {
            val validationResult = validateChunkLine(inputString)
            assertIs<ChunkValidationResult.Corrupted>(validationResult)
        }
    }

    @Test
    fun `test token enum usage`() {
        val openParen = chunkTokenOf('(')
        assertEquals(ChunkTokenPairs.PARENS.opening, openParen)
    }

    @Test
    fun `test token opening and closing methods`() {
        assertIs<ChunkToken.Opening>(ChunkTokenPairs.PARENS.opening)
        assertIs<ChunkToken.Closing>(ChunkTokenPairs.PARENS.closing)
        assertTrue(ChunkTokenPairs.PARENS.closing.isClosingTokenFor(ChunkTokenPairs.PARENS.opening))
        assertFalse(ChunkTokenPairs.PARENS.closing.isClosingTokenFor(ChunkTokenPairs.BRACES.opening))
    }

    @Test
    fun `test part 1 sample`() {
        val result = day10Part1(sampleInput)
        assertEquals(26397, result)
    }

    @Test
    fun `test part 2 sample`() {
        val result = day10Part2(sampleInput)
        assertEquals(288957L, result)
    }
}