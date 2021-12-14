package twentytwentyone

import res.asInts
import res.load
import libadvent.part1Result
import libadvent.part2Result

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
    val input = load("2021/day1.txt").asInts()
    day1Part1(input).part1Result()
    day1Part2(input).part2Result()
}
