package twentytwentyone

import res.as2dArray
import res.load
import util.part1Result
import util.part2Result


val <T> Array<Array<T>>.gridIndices: Sequence<Pair<Int, Int>>
    get() = sequence {
        for (rowIndex in indices) {
            for (columnIndex in get(rowIndex).indices) {
                yield(rowIndex to columnIndex)
            }
        }
    }

fun <T> Array<Array<T>>.pointsInGridAdjacentTo(reference: Pair<Int, Int>): Sequence<Pair<Int, Int>> {
    val (rowIndex, columnIndex) = reference

    return listOf(
        rowIndex - 1 to columnIndex,
        rowIndex + 1 to columnIndex,
        rowIndex to columnIndex - 1,
        rowIndex to columnIndex + 1
    ).filter { (rowIndex, columnIndex) ->
        rowIndex in indices && columnIndex in this[rowIndex].indices
    }.asSequence()
}

fun Array<Array<Int>>.findLowPoints(): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    for (reference in gridIndices) {
        val (rowIndex, columnIndex) = reference
        val referenceValue = this[rowIndex][columnIndex]

        val isLowPoint = pointsInGridAdjacentTo(reference)
            .map { (row, column) -> this[row][column] }
            .all { referenceValue < it }

        if (isLowPoint) {
            result.add(reference)
        }
    }
    return result
}


fun Array<Array<Int>>.calculateBasinFromLowPoint(lowPoint: Pair<Int, Int>): Set<Pair<Int, Int>> {

    val basin = mutableSetOf(lowPoint)
    val pointQueue = mutableListOf(lowPoint)

    while (pointQueue.isNotEmpty()) {
        val element = pointQueue.removeFirst()
        val heightAtElement = this[element.first][element.second]

        val elementsToAdd = pointsInGridAdjacentTo(element)
            .filter {
                val value = this[it.first][it.second]
                it !in basin && value != 9 && value > heightAtElement
            }
            .toList()

        basin.addAll(elementsToAdd)
        pointQueue.addAll(elementsToAdd)
    }

    return basin
}

fun day9Part1(input: Array<Array<Int>>): Int {
    return input.findLowPoints().sumOf { (rowIndex, columnIndex) ->
        input[rowIndex][columnIndex] + 1
    }
}

fun day9Part2(input: Array<Array<Int>>): Int {
    return input.findLowPoints()
        .map { lowPoint ->
            input.calculateBasinFromLowPoint(lowPoint).size
        }
        .sortedDescending()
        .take(3)
        .fold(1) { acc, i -> acc * i }
}

fun main() {
    val input = load("2021/day9.txt").as2dArray { it.digitToInt() }

    day9Part1(input).part1Result()
    day9Part2(input).part2Result()
}