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

fun findLowPoints(grid: Array<Array<Int>>): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    for (reference in grid.gridIndices) {
        val (rowIndex, columnIndex) = reference
        val referenceValue = grid[rowIndex][columnIndex]

        val isLowPoint = grid.pointsInGridAdjacentTo(reference)
            .map { (row, column) -> grid[row][column] }
            .all { referenceValue < it }

        if (isLowPoint) {
            result.add(reference)
        }
    }
    return result
}

fun day9Part1(input: Array<Array<Int>>): Int {
    return findLowPoints(input).sumOf { (rowIndex, columnIndex) ->
        input[rowIndex][columnIndex] + 1
    }
}

fun day9Part2(): Int {
    throw NotImplementedError()
}

fun main() {
    val input = load("2021/day9.txt").as2dArray { it.digitToInt() }

    day9Part1(input).part1Result()
    day9Part2().part2Result()
}