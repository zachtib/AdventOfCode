package twentytwentytwo

import libadvent.part1
import libadvent.part2
import libadvent.resource.asType
import libadvent.resource.load

enum class RockPaperScissors(
    val first: String,
    val second: String,
    val score: Int,
) {
    ROCK("A", "X", 1),
    PAPER("B", "Y", 2),
    SCISSORS("C", "Z", 3);
}

enum class RpsOutcome(val symbol: String, val score: Int) {
    WIN("Z", 6),
    LOSE("X", 0),
    DRAW("Y", 3);
}

data class RpsRound(
    val first: RockPaperScissors,
    val second: RockPaperScissors,
)

data class RpsRound2(
    val theirs: RockPaperScissors,
    val outcome: RpsOutcome,
)

private fun outcome(mine: RockPaperScissors, yours: RockPaperScissors): RpsOutcome {
    return when (mine) {
        RockPaperScissors.ROCK -> when (yours) {
            RockPaperScissors.ROCK -> RpsOutcome.DRAW
            RockPaperScissors.PAPER -> RpsOutcome.LOSE
            RockPaperScissors.SCISSORS -> RpsOutcome.WIN
        }

        RockPaperScissors.PAPER -> when (yours) {
            RockPaperScissors.ROCK -> RpsOutcome.WIN
            RockPaperScissors.PAPER -> RpsOutcome.DRAW
            RockPaperScissors.SCISSORS -> RpsOutcome.LOSE
        }

        RockPaperScissors.SCISSORS -> when (yours) {
            RockPaperScissors.ROCK -> RpsOutcome.LOSE
            RockPaperScissors.PAPER -> RpsOutcome.WIN
            RockPaperScissors.SCISSORS -> RpsOutcome.DRAW
        }
    }
}

private fun getNeeded(opponent: RockPaperScissors, result: RpsOutcome): RockPaperScissors {
    return when (opponent) {
        RockPaperScissors.ROCK -> when (result) {
            RpsOutcome.WIN -> RockPaperScissors.PAPER
            RpsOutcome.LOSE -> RockPaperScissors.SCISSORS
            RpsOutcome.DRAW -> RockPaperScissors.ROCK
        }

        RockPaperScissors.PAPER -> when (result) {
            RpsOutcome.WIN -> RockPaperScissors.SCISSORS
            RpsOutcome.LOSE -> RockPaperScissors.ROCK
            RpsOutcome.DRAW -> RockPaperScissors.PAPER
        }

        RockPaperScissors.SCISSORS -> when (result) {
            RpsOutcome.WIN -> RockPaperScissors.ROCK
            RpsOutcome.LOSE -> RockPaperScissors.PAPER
            RpsOutcome.DRAW -> RockPaperScissors.SCISSORS
        }
    }
}

private fun score(round: RpsRound): Int {
    return outcome(round.second, round.first).score + round.second.score
}

private fun score2(round: RpsRound2): Int {
    return round.outcome.score + getNeeded(round.theirs, round.outcome).score
}

fun String.asRpsRound(): RpsRound {
    val (first, second) = split(" ", limit = 2)
    return RpsRound(
        first = RockPaperScissors.values().first { it.first == first },
        second = RockPaperScissors.values().first() { it.second == second },
    )
}

fun String.asRpsRound2(): RpsRound2 {
    val (first, second) = split(" ", limit = 2)
    return RpsRound2(
        theirs = RockPaperScissors.values().first { it.first == first },
        outcome = RpsOutcome.values().first() { it.symbol == second },
    )
}

fun day2Part1(guide: List<RpsRound>): Int {
    return guide.sumOf { score(it) }
}

fun day2Part2(guide: List<RpsRound2>): Int {
    return guide.sumOf { score2(it) }
}

fun main() {
    val guide = load("2022/day2.txt")
    part1 { day2Part1(guide.asType { it.asRpsRound() }) }
    part2 { day2Part2(guide.asType { it.asRpsRound2() }) }
}