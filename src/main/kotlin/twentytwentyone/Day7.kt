package twentytwentyone

import res.asInts
import res.load
import util.EmptyCollectionException
import util.part1Result
import util.part2Result
import kotlin.math.abs


fun calculateFuelCosts(initialPositions: List<Int>, calculateMovementCost: (Int) -> Int): Map<Int, Int> {
    val minimumPosition = initialPositions.minOrNull() ?: return mapOf()
    val maximumPosition = initialPositions.maxOrNull() ?: return mapOf()
    val availablePositions = (minimumPosition..maximumPosition)

    return availablePositions.associateWith { potentialPosition ->
        initialPositions.sumOf { crabPosition ->
            calculateMovementCost(abs(crabPosition - potentialPosition))
        }
    }
}

fun calculateAllAlignmentFuelCosts(initialPositions: List<Int>): Map<Int, Int> {
    return calculateFuelCosts(initialPositions) { delta -> delta }
}

fun calculateRevisedFuelCosts(initialPositions: List<Int>): Map<Int, Int> {
    return calculateFuelCosts(initialPositions) { delta -> (0..delta).sum() }
}

fun day7Part1(initialPositions: List<Int>): Int {
    val alignmentFuelCosts = calculateAllAlignmentFuelCosts(initialPositions)

    return alignmentFuelCosts.minOfOrNull { (_, fuelCost) -> fuelCost } ?: throw EmptyCollectionException()
}

fun day7Part2(initialPositions: List<Int>): Int {
    val alignmentFuelCosts = calculateRevisedFuelCosts(initialPositions)

    return alignmentFuelCosts.minOfOrNull { (_, fuelCost) -> fuelCost } ?: throw EmptyCollectionException()
}

fun main() {
    val input = load("2021/day7.txt", ",").asInts()

    day7Part1(input).part1Result()
    day7Part2(input).part2Result()
}