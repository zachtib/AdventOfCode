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

fun day8Part2() {
    throw NotImplementedError()
}

fun main() {
    val input = load("2021/day8.txt").asType { it.asSevenSegmentNotes() }

    day8Part1(input).part1Result()
    day8Part2().part2Result()
}