package twentytwentythree

import libadvent.part1
import libadvent.part2
import libadvent.resource.asStrings
import libadvent.resource.load

private val DIGIT_STRINGS = mapOf(
    "one" to '1',
    "two" to '2',
    "three" to '3',
    "four" to '4',
    "five" to '5',
    "six" to '6',
    "seven" to '7',
    "eight" to '8',
    "nine" to '9',
)

fun getCalibrationValue(string: String): Int {
    val firstDigit = string.first { it.isDigit() }
    val secondDigit = string.last { it.isDigit() }

    return "$firstDigit$secondDigit".toInt()
}

fun getAllDigits(string: String): List<Char> {
    return buildList {
        for (index in string.indices) {
            if (string[index].isDigit()) {
                add(string[index])
            } else {
                for ((digitString, digitValue) in DIGIT_STRINGS) {
                    try {
                        if (string.substring(index, index + digitString.length) == digitString) {
                            add(digitValue)
                        }
                    } catch (e: StringIndexOutOfBoundsException) {
                        // Continue
                    }
                }
            }
        }
    }
}

fun getSecondCalibrationValue(string: String): Int {
    val list = getAllDigits(string)

    val firstDigit = list.first()
    val secondDigit = list.last()

    return "$firstDigit$secondDigit".toInt()
}


fun day1Part1(input: List<String>): Int {
    return input.sumOf(::getCalibrationValue)
}

fun day1Part2(input: List<String>): Int {
    return input.sumOf(::getSecondCalibrationValue)
}

fun main() {
    val calibrationDocument = load("2023/day1.txt").asStrings()

    part1 { day1Part1(calibrationDocument) }
    part2 { day1Part2(calibrationDocument) }
}