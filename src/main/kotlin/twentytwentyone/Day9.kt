package twentytwentyone

import libadvent.geometry.Point
import libadvent.grid.*
import res.asGrid
import res.load
import util.*

fun Grid<Int>.findLowPoints(): List<Pair<Int, Int>> {
    return buildList {
        forEachIndexed { reference: Point, referenceValue: Int ->
            if (pointsInGridAdjacentTo(reference)
                    .map { point -> get(point) }
                    .all { referenceValue < it }
            ) {
                add(reference)
            }
        }
    }
}


fun Grid<Int>.calculateBasinFromLowPoint(lowPoint: Pair<Int, Int>): Set<Pair<Int, Int>> {
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
    return input.findLowPoints().sumOf { (rowIndex, columnIndex) ->
        input[rowIndex, columnIndex] + 1
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