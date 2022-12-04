package twentytwentytwo

import libadvent.part1
import libadvent.part2
import libadvent.resource.asStrings
import libadvent.resource.load

fun String.halve(): Pair<String, String> {
    require(length % 2 == 0)
    return substring(0, length / 2) to substring(length / 2)
}

private const val LOWERCASE_OFFSET = 'a'.code - 1
private const val UPPERCASE_OFFSET = 'A'.code - 27

fun priorityOf(char: Char): Int {
    return when (char) {
        in 'a'..'z' -> char.code - LOWERCASE_OFFSET
        in 'A'..'Z' -> char.code - UPPERCASE_OFFSET
        else -> 0
    }
}

fun characterInBoth(strings: Pair<String, String>): Char {
    for (char in strings.first) {
        if (char in strings.second) {
            return char
        }
    }
    return ' '
}

fun day3Part1(input: List<String>): Int {
    return input.sumOf {
        val strings = it.halve()
        val char = characterInBoth(strings)
        priorityOf(char)
    }
}

private fun commonCharacter(strings: List<String>): Char {
    val sets = strings.map { it.toSet() }
    val chars = sets.reduce { first, second ->
        first.intersect(second)
    }
    require(chars.size == 1)
    return chars.first()
}

fun day3Part2(input: List<String>): Int {
    return input.windowed(3, step = 3)
        .sumOf { group ->
            val badge = commonCharacter(group)
            priorityOf(badge)
        }
}

fun main() {
    val input = load("2022/day3.txt").asStrings()
    part1 { day3Part1(input) }
    part2 { day3Part2(input) }
}