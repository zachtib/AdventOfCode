import util.asInts
import util.part1Result
import util.part2Result
import util.resource

fun day1Part1(input: List<Int>): Int {
    return input.windowed(2)
        .count { (first, second) -> second > first }
}

fun day1Part2(input: List<Int>): Int {
    return input.windowed(3)
        .map { it.sum() }
        .windowed(2)
        .count { (first, second) -> second > first }
}

fun main() {
    val input = resource("day1.txt").asInts()
    day1Part1(input).part1Result()
    day1Part2(input).part2Result()
}
