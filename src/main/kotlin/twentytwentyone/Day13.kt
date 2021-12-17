package twentytwentyone

import libadvent.geometry.Point
import libadvent.geometry.bounds
import libadvent.geometry.toPoint
import libadvent.grid.*
import libadvent.util.map
import libadvent.resource.Resource
import libadvent.resource.asComplexType
import libadvent.resource.load
import libadvent.part1
import libadvent.part2

class InvalidFoldInstruction(string: String) : RuntimeException("\"$string\" was not a valid fold")

sealed class Fold {
    data class Up(val y: Int) : Fold()
    data class Left(val x: Int) : Fold()
}

fun String.asFold(): Fold {
    val (instruction, value) = split("=", limit = 2)
    return when (instruction) {
        "fold along y" -> Fold.Up(value.toInt())
        "fold along x" -> Fold.Left(value.toInt())
        else -> throw InvalidFoldInstruction(this)
    }
}

inline fun <reified T> Grid<T>.foldAlong(fold: Fold, combine: (first: T, second: T) -> T): Grid<T> {
    // Folding left will alter the Grid's columns, up will alter the rows
    return when (fold) {
        is Fold.Up -> {
            Grid(rows, fold.y) { row, column ->
                // Rows will remain unchanged
                val alternateColumn = (fold.y - column) + fold.y
                if (alternateColumn in columnIndices) {
                    combine(this[column, row], this[row, alternateColumn])
                } else {
                    this[row, column]
                }
            }
        }
        is Fold.Left -> {
            Grid(fold.x, columns) { row, column ->
                val alternateRow = (fold.x - row) + fold.x
                if (alternateRow in rowIndices) {
                    combine(this[row, column], this[alternateRow, column])
                } else {
                    this[row, column]
                }
            }
        }
    }
}

private fun getInitialGrid(points: List<Point>): Grid<Boolean> {
    val initialGridBounds = points.bounds()
    return BoundedGrid(initialGridBounds) { row, column ->
        (row to column) in points
    }
}

private fun booleanGridString(grid: Grid<Boolean>): String {
    return grid.joinToString(transpose = true) { it.map(ifTrue = "#", ifFalse = ".") }
}

fun day13Part1(points: List<Point>, folds: List<Fold>, printGridSteps: Boolean = false): Int {
    var grid = getInitialGrid(points)
    if (printGridSteps) {
        println(booleanGridString(grid))
    }
    grid = grid.foldAlong(folds.first()) { first, second -> first || second }
    if (printGridSteps) {
        println("\nFolded:\n${booleanGridString(grid)}")
    }
    return grid.count { it }
}

fun day13Part2(points: List<Point>, folds: List<Fold>, printGridSteps: Boolean = false): String {
    var grid = getInitialGrid(points)
    if (printGridSteps) {
        println(booleanGridString(grid))
    }
    for ((index, fold) in folds.withIndex()) {
        grid = grid.foldAlong(fold) { first, second -> first || second }
        if (printGridSteps) {
            println("\nStep ${index + 1}:")
            println(booleanGridString(grid))
        }
    }
    return booleanGridString(grid)
}

fun Resource.asTransparentOrigami(): Pair<List<Point>, List<Fold>> = asComplexType {
    takeLines {
        it.toPoint()
    } to takeLines {
        it.asFold()
    }
}

fun main() {
    val (points, folds) = load("2021/day13.txt").asTransparentOrigami()

    part1 { day13Part1(points, folds) }
    part2(prependNewline = true) { day13Part2(points, folds) }
}