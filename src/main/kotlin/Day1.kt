import util.loadInts
import util.part1Result

fun day1Part1(input: List<Int>): Int {
    return input.windowed(2)
        .count { (first, second) -> second > first }
}

fun main() {
    val input = "day1.txt".loadInts()
    day1Part1(input).part1Result()
}