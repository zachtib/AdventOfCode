package twentytwentyone

import libadvent.util.isEven
import res.asStrings
import res.load
import util.*

class InvalidChunkToken(token: Char) : RuntimeException("$token is not a valid ChunkToken")

sealed class ChunkValidationResult {
    object Valid : ChunkValidationResult()
    data class Incomplete(val tokenStack: Stack<ChunkToken.Opening>) : ChunkValidationResult()
    data class Corrupted(val token: ChunkToken.Closing) : ChunkValidationResult()
}

sealed class ChunkToken {
    abstract val symbol: Char

    data class Opening(override val symbol: Char, val closedBy: Char, val points: Int) : ChunkToken()
    data class Closing(override val symbol: Char, val closes: Char, val points: Int) : ChunkToken() {
        fun isClosingTokenFor(other: ChunkToken): Boolean {
            return other is Opening && this.closes == other.symbol
        }
    }
}

enum class ChunkTokenPairs(open: Char, close: Char, errorPoints: Int, completionPoints: Int) {
    PARENS('(', ')', 3, 1),
    BRACKETS('[', ']', 57, 2),
    BRACES('{', '}', 1197, 3),
    ANGLES('<', '>', 25137, 4);

    val opening: ChunkToken.Opening = ChunkToken.Opening(open, close, completionPoints)
    val closing: ChunkToken.Closing = ChunkToken.Closing(close, open, errorPoints)
}

fun chunkTokenOf(symbol: Char): ChunkToken {
    return when (symbol) {
        '(' -> ChunkTokenPairs.PARENS.opening
        ')' -> ChunkTokenPairs.PARENS.closing
        '[' -> ChunkTokenPairs.BRACKETS.opening
        ']' -> ChunkTokenPairs.BRACKETS.closing
        '{' -> ChunkTokenPairs.BRACES.opening
        '}' -> ChunkTokenPairs.BRACES.closing
        '<' -> ChunkTokenPairs.ANGLES.opening
        '>' -> ChunkTokenPairs.ANGLES.closing
        else -> throw InvalidChunkToken(symbol)
    }
}

fun calculateCompletionScore(incomplete: ChunkValidationResult.Incomplete): Long {
    return incomplete.tokenStack.fold(0L) { acc: Long, token: ChunkToken.Opening ->
        acc * 5 + token.points
    }
}

fun validateChunkLine(inputString: String): ChunkValidationResult {
    val tokens = emptyStack<ChunkToken.Opening>()

    for (char in inputString) {
        when (val token = chunkTokenOf(char)) {
            is ChunkToken.Opening -> tokens.push(token)
            is ChunkToken.Closing -> {
                val other = tokens.pop() ?: return ChunkValidationResult.Corrupted(token)
                if (!token.isClosingTokenFor(other)) {
                    return ChunkValidationResult.Corrupted(token)
                }
            }
        }
    }

    return if (tokens.isEmpty()) {
        ChunkValidationResult.Valid
    } else {
        ChunkValidationResult.Incomplete(tokens)
    }
}

class NotVeryOddException : RuntimeException("That's odd, that's not odd")

private fun <T> List<T>.getMiddleItem(): T {
    if (size.isEven) throw NotVeryOddException()
    // Five element list would have indices 0, 1, 2, 3, 4
    val indices = this.indices
    // (4 - 0) / 2 == 2
    val middleIndex = (indices.last - indices.first) / 2
    return this[middleIndex]
}

fun day10Part1(lines: List<String>): Int {
    return lines.map { line -> validateChunkLine(line) }
        .filterIsInstance<ChunkValidationResult.Corrupted>()
        .sumOf { corrupted -> corrupted.token.points }
}

fun day10Part2(lines: List<String>): Long {
    return lines.map { line -> validateChunkLine(line) }
        .filterIsInstance<ChunkValidationResult.Incomplete>()
        .map { incomplete -> calculateCompletionScore(incomplete) }
        .sorted()
        .getMiddleItem()
}

fun main() {
    val input = load("2021/day10.txt").asStrings()

    part1 {
        day10Part1(input)
    }
    part2 {
        day10Part2(input)
    }
}