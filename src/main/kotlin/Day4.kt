import util.asInts
import util.part1Result
import util.part2Result
import util.resource

data class BingoBoardsWithInputs(
    val inputs: List<Int>,
    val boards: List<BingoBoard>,
)

class BingoBoard(val board: String)


fun String.toBingoBoard(): BingoBoard = BingoBoard(this)

fun day4Part1(input: Any): Int {
    return -1
}

fun day4Part2(input: Any): Int {
    return -1
}

fun main() {
    val input = resource("day4.txt").asInts()
    day4Part1(input).part1Result()
    day4Part2(input).part2Result()
}