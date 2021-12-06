package twentytwenty

import res.asInts
import res.load

data class JoltageDistribution(
        var ones: Int = 0,
        var twos: Int = 0,
        var threes: Int = 0,
)


fun findJoltageDistribution(joltages: List<Int>): JoltageDistribution {
    val maxAdapter = joltages.maxOrNull() ?: throw IllegalArgumentException("joltages was empty")
    val result = JoltageDistribution()
    var currentJoltage = 0

    while (currentJoltage < maxAdapter) {
        // Find the next adapter in the series
        val nextAdapter = joltages.filter { currentJoltage < it && it <= (currentJoltage + 3) }
                .minOrNull()
                ?: throw IllegalStateException("No valid next adapter from currentJoltage of $currentJoltage")
        // Increment the distribution
        when (nextAdapter - currentJoltage) {
            1 -> result.ones++
            2 -> result.twos++
            3 -> result.threes++
            else -> throw IllegalStateException("Difference was not one of 1, 2, or 3")
        }
        // update the joltage amount
        currentJoltage = nextAdapter
    }
    result.threes++ // Because the device is always three more than the max adapter
    return result
}


fun traverseAdapters(joltages: List<Int>, startingJoltage: Int, targetJoltage: Int): List<List<Int>> {
    var currentJoltage = startingJoltage
    val result = mutableListOf(startingJoltage)

    while (currentJoltage < targetJoltage) {
        // Find the next adapters in the series
        val nextAdapters = joltages.filter { currentJoltage < it && it <= (currentJoltage + 3) }

        when (nextAdapters.size) {
            0 -> {
                if (currentJoltage + 3 <= targetJoltage) {
                    result.add(targetJoltage)
                }
                return listOf(result)
            }
            1 -> {
                currentJoltage = nextAdapters.first()
                result.add(currentJoltage)
            }
            else -> {
                return nextAdapters.flatMap { joltage ->
                    traverseAdapters(joltages.filter { it > joltage }, joltage, targetJoltage)
                }.map { result + it }
            }
        }
    }
    result.add(targetJoltage)
    return listOf(result)
}


fun calculatePathsThenTraverse(joltages: List<Int>, startingJoltage: Int, targetJoltage: Int): Long {
    val jorts = listOf(0) + joltages.sorted() + listOf(targetJoltage)
    val pathways = mutableMapOf<Int, List<Int>>()

    for ((index, joltage) in jorts.withIndex()) {
        val destinations = mutableListOf<Int>()
        var nextIndex = index + 1
        while (nextIndex < jorts.size && jorts[nextIndex] <= joltage + 3) {
            destinations.add(jorts[nextIndex])
            nextIndex++
        }
        pathways[joltage] = destinations
    }

    return traverse(startingJoltage, targetJoltage, pathways)
}

val shortcuts = mutableMapOf<Int, Long>()

fun traverse(begin: Int, target: Int, pathways: Map<Int, List<Int>>): Long {
    if (begin in shortcuts) {
        return shortcuts[begin]!!
    }
    var acc = 0L
    for (path in pathways[begin] ?: listOf()) {
        if (path == target) {
            acc++
        } else {
            acc += traverse(path, target, pathways)
        }
    }
    shortcuts[begin] = acc
    return acc
}

fun findJoltageArrangements(joltages: List<Int>): List<List<Int>> {
    val target = joltages.maxOrNull()?.plus(3) ?: return listOf()
    return traverseAdapters(joltages, 0, target)
}

fun countJoltageArrangements(joltages: List<Int>): Long {
    val target = joltages.maxOrNull()?.plus(3) ?: return 0
    return calculatePathsThenTraverse(joltages, 0, target)
}

fun main() {
    val joltages = load("day10.txt").asInts()
    val distribution = findJoltageDistribution(joltages)
    val part1Result = distribution.ones * distribution.threes
    println("Part 1 Result: $part1Result")

    val part2Result = countJoltageArrangements(joltages)
    println("Part 2 Result: $part2Result")
}
