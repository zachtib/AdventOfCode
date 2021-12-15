package twentytwentyone

import libadvent.geometry.Coordinate
import libadvent.geometry.get
import libadvent.grid.Grid
import libadvent.grid.forEachIndexed
import libadvent.grid.map
import libadvent.grid.pointsInGridAdjacentOrDiagonalTo
import libadvent.part1
import libadvent.part2
import libadvent.resource.asGrid
import libadvent.resource.load

data class BioluminescentOctopus(
    var energyLevel: Int,
    var hasFlashed: Boolean = false,
)

fun BioluminescentOctopus.increaseEnergy() {
    if (!hasFlashed) {
        energyLevel++
    }
}

fun BioluminescentOctopus.shouldFlash(): Boolean {
    return if (energyLevel > 9 && !hasFlashed) {
        hasFlashed = true
        true
    } else {
        false
    }
}

fun Grid<BioluminescentOctopus>.resetFlashes() = forEach {
    if (it.hasFlashed) {
        it.energyLevel = 0
        it.hasFlashed = false
    }
}

fun Grid<BioluminescentOctopus>.increaseEnergyLevels() = forEach { it.increaseEnergy() }

fun Grid<BioluminescentOctopus>.increaseEnergyLevelsAtIndices(indices: List<Coordinate>) = indices.forEach { index ->
    this[index].increaseEnergy()
}

fun Grid<BioluminescentOctopus>.getFlashingIndices(): Collection<Coordinate> {
    return buildList {
        this@getFlashingIndices.forEachIndexed { index, octopus ->
            if (octopus.shouldFlash()) {
                add(index)
            }
        }
    }
}

fun Grid<BioluminescentOctopus>.step(): Int {
    var acc = 0
    // First, the energy level of each octopus increases by 1.
    increaseEnergyLevels()

    // Then, any octopus with an energy level greater than 9 flashes.
    var indicesOfFlashingOctopus = getFlashingIndices()
    while (indicesOfFlashingOctopus.isNotEmpty()) {
        acc += indicesOfFlashingOctopus.size
        // This increases the energy level of all adjacent octopuses by 1,
        // including octopuses that are diagonally adjacent.
        val adjacentIndices = indicesOfFlashingOctopus.flatMap(::pointsInGridAdjacentOrDiagonalTo)
        increaseEnergyLevelsAtIndices(adjacentIndices)

        // If this causes an octopus to have an energy level greater than 9, it also flashes.
        indicesOfFlashingOctopus = getFlashingIndices()
        // This process continues as long as new octopuses keep having their energy level increased beyond 9.
        // (An octopus can only flash at most once per step.)
    }

    // Finally, any octopus that flashed during this step has its energy level set to 0,
    // as it used all of its energy to flash.
    resetFlashes()

    return acc
}

fun Grid<BioluminescentOctopus>.atZero() : Boolean {
    for (oct in this) {
        if (oct.energyLevel > 0) {
            return false
        }
    }
    return true
}

fun day11Part1(input: Grid<Int>): Int {
    val octopuses = input.map { BioluminescentOctopus(it) }
    return (1..100).sumOf { octopuses.step() }
}

fun day11Part2(input: Grid<Int>): Int {
    val octopuses = input.map { BioluminescentOctopus(it) }
    var step = 1
    while (true) {
        octopuses.step()
        if (octopuses.atZero()) {
            return step
        }
        step++
    }
}

fun main() {
    val input = load("2021/day11.txt").asGrid { it.digitToInt() }
    part1 {
        day11Part1(input)
    }
    part2 {
        day11Part2(input)
    }
}