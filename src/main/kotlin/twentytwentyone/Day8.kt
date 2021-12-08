package twentytwentyone

import res.Resource
import res.asComplexType
import res.asType
import res.load
import util.part1Result
import util.part2Result

data class SevenSegmentNotes(val signalPatterns: List<String>, val outputValue: List<String>)

fun Resource.asSevenSegmentNotes() = asComplexType("|") {
    SevenSegmentNotes(
        signalPatterns = takeOne { it.split(" ") },
        outputValue = takeOne { it.split(" ") }
    )
}

fun String.asSevenSegmentNotes(): SevenSegmentNotes {
    val (signals, outputs) = this.split(" | ", limit = 2)
        .map { it.split(" ") }
    return SevenSegmentNotes(signals, outputs)
}

class SevenSegmentDisplay(inputString: String) {
    companion object {

    }
}

fun day8Part1(notes: List<SevenSegmentNotes>): Int {
    throw NotImplementedError()
}

fun day8Part2() {
    throw NotImplementedError()
}

fun main() {
    val input = load("2021/day8.txt").asType { it.asSevenSegmentNotes() }

    day8Part1(input).part1Result()
    day8Part2().part2Result()
}