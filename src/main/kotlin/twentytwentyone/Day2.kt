package twentytwentyone

import libadvent.resource.asType
import libadvent.resource.load
import libadvent.part1Result
import libadvent.part2Result

enum class SubmarineDirection {
    FORWARD,
    DOWN,
    UP
}

data class SubmarineCommand(
    val direction: SubmarineDirection,
    val amount: Int,
)

open class Submarine {

    var horizontal: Int = 0
        protected set
    var depth: Int = 0
        protected set

    open fun perform(command: SubmarineCommand) {
        when (command.direction) {
            SubmarineDirection.FORWARD -> horizontal += command.amount
            SubmarineDirection.DOWN -> depth += command.amount
            SubmarineDirection.UP -> depth -= command.amount
        }
    }
}

class AdvancedSubmarine : Submarine() {
    var aim: Int = 0
        private set

    override fun perform(command: SubmarineCommand) {
        when (command.direction) {
            SubmarineDirection.FORWARD -> {
                horizontal += command.amount
                depth += aim * command.amount
            }
            SubmarineDirection.DOWN -> aim += command.amount
            SubmarineDirection.UP -> aim -= command.amount
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
    val submarine = AdvancedSubmarine()
    for (command in input) {
        submarine.perform(command)
    }
    return submarine.horizontal * submarine.depth
}

fun main() {
    val input = load("2021/day2.txt").asType { it.toSubmarineCommand() }
    day2Part1(input).part1Result()
    day2Part2(input).part2Result()
}
