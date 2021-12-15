package twentytwentyone

import libadvent.geometry.Coordinate
import libadvent.geometry.get
import libadvent.grid.*
import libadvent.part1Result
import libadvent.part2Result
import libadvent.resource.asGrid
import libadvent.resource.load

fun Grid<Int>.findLowPoints(): List<Coordinate> {
    return buildList {
        forEachIndexed { reference: Coordinate, referenceValue: Int ->
            if (pointsInGridAdjacentTo(reference)
                    .map { point -> get(point) }
                    .all { referenceValue < it }
            ) {
                add(reference)
            }
        }
    }
}


fun Grid<Int>.calculateBasinFromLowPoint(lowPoint: Coordinate): Set<Coordinate> {
    val basin = mutableSetOf(lowPoint)
    val pointQueue = mutableListOf(lowPoint)

    while (pointQueue.isNotEmpty()) {
        val element = pointQueue.removeFirst()
        val heightAtElement = this[element]

        val elementsToAdd = pointsInGridAdjacentTo(element)
            .filter {
                val value = this[it]
                it !in basin && value != 9 && value > heightAtElement
            }
            .toList()

        basin.addAll(elementsToAdd)
        pointQueue.addAll(elementsToAdd)
    }

    return basin
}

fun day9Part1(input: Grid<Int>): Int {
    return input.findLowPoints().sumOf { coordinate ->
        input[coordinate] + 1
    }
}

fun day9Part2(input: Grid<Int>): Int {
    return input.findLowPoints()
        .map { lowPoint ->
            input.calculateBasinFromLowPoint(lowPoint).size
        }
        .sortedDescending()
        .take(3)
        .fold(1) { acc, i -> acc * i }
}

fun main() {
    val input = load("2021/day9.txt").asGrid { it.digitToInt() }

    day9Part1(input).part1Result()
    day9Part2(input).part2Result()
}