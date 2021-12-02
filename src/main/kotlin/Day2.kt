import util.part1Result
import util.part2Result
import util.resource

enum class SubmarineDirection {
    FORWARD,
    DOWN,
    UP
}

data class SubmarineCommand(
    val direction: SubmarineDirection,
    val amount: Int,
)

class Submarine {

    var horizontal: Int = 0
        private set
    var depth: Int = 0
        private set

    fun perform(command: SubmarineCommand) {
        when (command.direction) {
            SubmarineDirection.FORWARD -> horizontal += command.amount
            SubmarineDirection.DOWN -> depth += command.amount
            SubmarineDirection.UP -> depth -= command.amount
        }
    }
}

fun String.toSubmarineCommand(): SubmarineCommand {
    val (directionString, amountString) = split(" ", limit = 2)
    val direction = SubmarineDirection.valueOf(directionString.uppercase())
    val amount = amountString.toInt()

    return SubmarineCommand(direction, amount)
}

fun day2Part1(input: List<SubmarineCommand>): Int {
    val submarine = Submarine()
    for (command in input) {
        submarine.perform(command)
    }
    return submarine.horizontal * submarine.depth
}

fun day2Part2(input: List<SubmarineCommand>): Int {
    return 0
}

fun main() {
    val input = resource("day2.txt").asType { it.toSubmarineCommand() }
    day2Part1(input).part1Result()
    day2Part2(input).part2Result()
}