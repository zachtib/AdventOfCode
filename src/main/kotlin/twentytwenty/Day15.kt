package twentytwenty

import libadvent.part1Result
import libadvent.part2Result

class MemoryGame {

    var lastSpoken: Int? = null
        private set

    var round = 1
        private set

    private val memory = mutableMapOf<Int, Int>()

    private var lastDelta = -1

    fun say(number: Int) {
        lastDelta = memory[number]?.let { previous -> round - previous } ?: 0
        memory[number] = round
        lastSpoken = number
        round++
    }

    fun sayAll(vararg numbers: Int) {
        for (number in numbers) {
            say(number)
        }
    }

    fun next(): Int {
        requireNotNull(lastSpoken)
        say(lastDelta)
        return lastSpoken ?: 0
    }

    fun runUntilRound(round: Int) {
        while (this.round <= round) {
            next()
        }
    }
}

fun main() {
    val game = MemoryGame()
    game.sayAll(9, 6, 0, 10, 18, 2, 1)
    game.runUntilRound(2020)
    game.lastSpoken.part1Result()

    game.runUntilRound(30000000)
    game.lastSpoken.part2Result()
}