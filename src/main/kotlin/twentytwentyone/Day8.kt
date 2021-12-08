package twentytwentyone

import res.asType
import res.load
import util.part1Result
import util.part2Result

data class SevenSegmentNotes(val signalPatterns: List<String>, val outputValue: List<String>)

fun String.asSevenSegmentNotes(): SevenSegmentNotes {
    val (signals, outputs) = this.split(" | ", limit = 2)
        .map { it.split(" ") }
    return SevenSegmentNotes(signals, outputs)
}

private const val ONE_COUNT = 2
private const val FOUR_COUNT = 4
private const val SEVEN_COUNT = 3
private const val EIGHT_COUNT = 7

fun day8Part1(notes: List<SevenSegmentNotes>): Int {
    val allOutputValues = notes.flatMap { it.outputValue }
    val targetValues = listOf(ONE_COUNT, FOUR_COUNT, SEVEN_COUNT, EIGHT_COUNT)
    return allOutputValues.count { it.length in targetValues }
}

private enum class Segment {
    A,
    B,
    C,
    D,
    E,
    F,
    G
}

const val SEGMENT_A = 'a'
const val SEGMENT_B = 'b'
const val SEGMENT_C = 'c'
const val SEGMENT_D = 'd'
const val SEGMENT_E = 'e'
const val SEGMENT_F = 'f'
const val SEGMENT_f = 'g'

data class SegmentMap(
    var a: Char? = null,
    var b: Char? = null,
    var c: Char? = null,
    var d: Char? = null,
    var e: Char? = null,
    var f: Char? = null,
    var g: Char? = null,
)

//
//fun SegmentMap.solve(): SolvedSegmentMap {
//    return if (a != null && b != null && c != null && d != null && e != null && f != null && g != null)
//}
//
//}
//
//data class SolvedSegmentMap()

val SegmentMap.isComplete: Boolean
    get() = a != null && b != null && c != null && d != null && e != null && f != null && g != null


fun solveSevenSegments(notes: SevenSegmentNotes): Int {
    val oneString = notes.signalPatterns.firstOrNull { it.length == ONE_COUNT } ?: return -1
    val sevenString = notes.signalPatterns.firstOrNull { it.length == SEVEN_COUNT } ?: return -1

    println(oneString)
    println(sevenString)

    val segmentsInOne = oneString.toSet()
    val segmentsInSeven = sevenString.toSet()

    val topSegments = segmentsInSeven.filterNot { it in segmentsInOne }
    if (topSegments.size != 1) {
        throw IllegalStateException("This should always be one")
    }
    val topSegment = topSegments.first()

    return 0
}

fun day8Part2(notes: List<SevenSegmentNotes>): Int {
    return notes.sumOf { solveSevenSegments(it) }
}

fun main() {
    val input = load("2021/day8.txt").asType { it.asSevenSegmentNotes() }

    day8Part1(input).part1Result()
    day8Part2(input).part2Result()
}