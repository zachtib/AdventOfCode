package twentytwentyone

import libadvent.part1Result
import libadvent.part2Result
import libadvent.util.isNotSupersetOf
import libadvent.util.isSubsetOf
import libadvent.util.isSupersetOf
import res.asType
import res.load
import util.*

data class SevenSegmentNotes(val signalPatterns: List<String>, val outputValue: List<String>)

fun String.asSevenSegmentNotes(): SevenSegmentNotes {
    val (signals, outputs) = this.split(" | ", limit = 2)
        .map { it.split(" ") }
    return SevenSegmentNotes(signals, outputs)
}

private const val ZEROSIXNINE_COUNT = 6
private const val ONE_COUNT = 2
private const val TWOTHREEFIVE_COUNT = 5
private const val FOUR_COUNT = 4
private const val SEVEN_COUNT = 3
private const val EIGHT_COUNT = 7

fun day8Part1(notes: List<SevenSegmentNotes>): Int {
    val allOutputValues = notes.flatMap { it.outputValue }
    val targetValues = listOf(ONE_COUNT, FOUR_COUNT, SEVEN_COUNT, EIGHT_COUNT)
    return allOutputValues.count { it.length in targetValues }
}

class SegmentDecoder(
    private val a: Char,
    private val b: Char,
    private val c: Char,
    private val d: Char,
    private val e: Char,
    private val f: Char,
    private val g: Char,
) {
    fun validate() {
        if (setOf(a, b, c, d, e, f, g).size != 7) {
            throw IllegalStateException("There appears to be overlap in the solution")
        }
    }

    fun decodeNumber(inputs: List<String>): Int {
        var result = 0
        for (digit in inputs) {
            val decodedDigit = decodeDigit(digit)
            result = result * 10 + decodedDigit
        }
        return result
    }

    fun decodeDigit(input: String): Int {
        val set = input.toSet()
        return if (e in set) {
            // 0, 2, 6, 8
            if (b in set) {
                // 0, 6, 8
                if (d in set) {
                    // 6, 8
                    if (c in set) {
                        8
                    } else {
                        6
                    }
                } else {
                    0
                }
            } else {
                2
            }
        } else {
            // 1, 3, 4, 5, 7, 9
            if (b in set) {
                // 4, 5, 9
                if (a in set) {
                    // 5, 9
                    if (c in set) {
                        9
                    } else {
                        5
                    }
                } else {
                    4
                }
            } else {
                // 1, 3, 7
                if (a in set) {
                    // 3, 7
                    if (d in set) {
                        3
                    } else {
                        7
                    }
                } else {
                    1
                }
            }
        }
    }
}

fun solveWithSets(notes: SevenSegmentNotes): Int {
    val signalPatterns = notes.signalPatterns.map { it.toSet() }

    // 1, 4, 7, and 8 each have a unique number of segments
    val one = signalPatterns.first { it.size == ONE_COUNT }
    val four = signalPatterns.first { it.size == FOUR_COUNT}
    val seven = signalPatterns.first { it.size == SEVEN_COUNT }
    val eight = signalPatterns.first { it.size == EIGHT_COUNT }

    // 0, 6, and 9 each have 6 segments
    val zeroSixNine = signalPatterns.filter { it.size == ZEROSIXNINE_COUNT }
    // 2, 3, and 5 each have 5 segments
    val twoThreeFive = signalPatterns.filter { it.size == TWOTHREEFIVE_COUNT }

    // 6 is the only number of 0, 6, or 9 to not contain all segments in 1
    val six = zeroSixNine.first { it isNotSupersetOf  one }
    // 3 is the only number of 2, 3, or 5 to contain all segments in 1
    val three = twoThreeFive.first { it isSupersetOf one }
    // 5 is the only number of 2, 3, or 5 to have all its segments contained in 6
    val five = twoThreeFive.first { it isSubsetOf six }
    // 2 by process of elimination
    val two = twoThreeFive.first { it != three && it != five }
    // 9 is the only number of 0, 6, or 9 to contain all segments in 3
    val nine = zeroSixNine.first { it isSupersetOf three }
    // 0 by process of elimination
    val zero = zeroSixNine.first { it != six && it != nine }

    val mapping = mapOf(
        zero to 0,
        one to 1,
        two to 2,
        three to 3,
        four to 4,
        five to 5,
        six to 6,
        seven to 7,
        eight to 8,
        nine to 9,
    )

    var acc = 0
    for (string in notes.outputValue) {
        acc = acc * 10 + mapping[string.toSet()]!!
    }
    return acc
}

fun solveSevenSegments(notes: SevenSegmentNotes): Int {
    val oneString = notes.signalPatterns.firstOrNull { it.length == ONE_COUNT } ?: return -1
    val fourString = notes.signalPatterns.firstOrNull { it.length == FOUR_COUNT} ?: return -1
    val sevenString = notes.signalPatterns.firstOrNull { it.length == SEVEN_COUNT } ?: return -1

    val cfSegments = oneString.toSet().requireSize(2)
    val bcdfSegments = fourString.toSet().requireSize(4)
    val acfSegments = sevenString.toSet().requireSize(3)

    // A solved for, 1/7
    val aSegment: Char = (acfSegments subtract cfSegments).requireOnlyOne()

    val bdSegments = (bcdfSegments subtract cfSegments).requireSize(2)

    // And these segments will be both in 5, but only one in 2
    val twoThreeFiveStrings = notes.signalPatterns.filter { it.length == TWOTHREEFIVE_COUNT }.requireSize(3)

    // where all of those overlap is the a, d, and g segments
    val adgSegments: Set<Char> = twoThreeFiveStrings.map { it.toSet() }.reduceRight { a, b -> a intersect  b}
    val dgSegments = (adgSegments subtract setOf(aSegment)).requireSize(2)

    // A and G are known
    val gSegment = (dgSegments subtract bdSegments).requireOnlyOne()

    // A, D, and G are known
    val dSegment = (dgSegments subtract setOf(gSegment)).requireOnlyOne()

    // A, B, D, and G are known
    val bSegment = (bdSegments subtract setOf(dSegment)).requireOnlyOne()

    // only 5 has the b segment lit up of these
    val fiveString = twoThreeFiveStrings.first { it.contains(bSegment) }
    val twoThreeStrings = twoThreeFiveStrings.filter { it != fiveString }.requireSize(2)

    // Where 2 and 3 intersect, but not 5 is the C segment
    val acdgSegments = twoThreeStrings.map { it.toSet() }.reduceRight { a, b -> a intersect b}.requireSize(4)

    // A, B, C, D, and G are known.
    val cSegment = (acdgSegments subtract fiveString.toSet()).requireOnlyOne()

    val efSegments = twoThreeStrings.map { it.toSet() }.reduceRight { a, b ->
        (a union b) subtract (a intersect b)
    }

    // of these, F is the one contained in oneString
    val fSegment = (cfSegments intersect efSegments).requireOnlyOne()
    val eSegment = (efSegments subtract setOf(fSegment)).requireOnlyOne()

    val solution = SegmentDecoder(aSegment, bSegment, cSegment, dSegment, eSegment, fSegment, gSegment)
    solution.validate()

    return solution.decodeNumber(notes.outputValue)
}

fun day8Part2(notes: List<SevenSegmentNotes>): Int {
    return notes.sumOf { solveSevenSegments(it) }
}

fun main() {
    val input = load("2021/day8.txt").asType { it.asSevenSegmentNotes() }

    day8Part1(input).part1Result()
    day8Part2(input).part2Result()
}