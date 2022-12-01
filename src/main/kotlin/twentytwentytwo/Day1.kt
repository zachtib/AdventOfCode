package twentytwentytwo

import libadvent.part1
import libadvent.part2
import libadvent.resource.asListsOfInts
import libadvent.resource.load

fun day1Part1(calories: List<List<Int>>): Int {
    return calories.maxOf { it.sum() }
}

fun day1Part2(calories: List<List<Int>>): Int {
    return calories.map { it.sum() }
        .sortedDescending()
        .take(3)
        .sum()
}

fun main() {
    val calories = load("2022/day1.txt").asListsOfInts()
    part1 { day1Part1(calories) }
    part2 { day1Part2(calories) }
}
