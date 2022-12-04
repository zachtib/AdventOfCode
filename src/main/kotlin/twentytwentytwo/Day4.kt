package twentytwentytwo

import libadvent.part1
import libadvent.part2
import libadvent.resource.asType
import libadvent.resource.load

private fun intRange(string: String): IntRange {
    val (lower, upper) = string.split("-", limit = 2)
    return IntRange(lower.toInt(), upper.toInt())
}

fun parseSections(line: String): Pair<IntRange, IntRange> {
    val (first, second) = line.split(",", limit = 2)
    return intRange(first) to intRange(second)
}

fun eitherContains(pair: Pair<IntRange, IntRange>): Boolean {
    return pair.first.all { it in pair.second } || pair.second.all { it in pair.first }
}

fun overlaps(pair: Pair<IntRange, IntRange>): Boolean {
    return pair.first.any { it in pair.second } || pair.second.any { it in pair.first }
}

fun day4Part1(lines: List<Pair<IntRange, IntRange>>): Int {
    return lines.count { eitherContains(it) }
}

fun day4Part2(lines: List<Pair<IntRange, IntRange>>): Int {
    return lines.count { overlaps(it) }
}

fun main() {
    val resource = load("2022/day4.txt")
    val pairs = resource.asType { parseSections(it) }

    part1 { day4Part1(pairs) }
    part2 { day4Part2(pairs) }
}