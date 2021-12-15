package libadvent.geometry

import libadvent.grid.Grid
import libadvent.grid.columnIndices
import libadvent.grid.pointsInGridAdjacentTo
import libadvent.grid.rowIndices
import kotlin.math.abs

// Interfaces

sealed interface Coordinate {
    val x: Int
    val y: Int
}

sealed interface MutableCoordinate : Coordinate {
    override var x: Int
    override var y: Int
}

// Implementations

private data class CoordinateData(override val x: Int, override val y: Int) : Coordinate {
    override fun equals(other: Any?): Boolean {
        return if (other is Coordinate) {
            this.x == other.x && this.y == other.y
        } else {
            super.equals(other)
        }
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

private data class MutableCoordinateData(override var x: Int, override var y: Int) : MutableCoordinate {
    override fun equals(other: Any?): Boolean {
        return if (other is Coordinate) {
            this.x == other.x && this.y == other.y
        } else {
            super.equals(other)
        }
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

// Converters

fun Coordinate.toMutableCoordinate(): MutableCoordinate {
    return MutableCoordinateData(x, y)
}

// Builders

fun Coordinate(x: Int, y: Int): Coordinate = CoordinateData(x, y)

fun MutableCoordinate(x: Int, y: Int): MutableCoordinate = MutableCoordinateData(x, y)

// Extensions for use with Grids

fun gridCoordinate(row: Int, column: Int) = Coordinate(x = column, y = row)
fun mutableGridCoordinate(row: Int, column: Int) = MutableCoordinate(x = column, y = row)

fun <T> Grid<T>.pointsInGridAdjacentTo(reference: Coordinate): List<Coordinate> {
    return pointsInGridAdjacentTo(reference.toPoint()).map { it.toCoordinate() }
}

fun Grid<Any>.coordinateIterator() = iterator {
    for (row in rowIndices) {
        for (column in columnIndices) {
            yield(gridCoordinate(row, column))
        }
    }
}

val Grid<Any>.upperLeft: Coordinate get() = gridCoordinate(rowIndices.first, columnIndices.first)
val Grid<Any>.lowerRight: Coordinate get() = gridCoordinate(rowIndices.last, columnIndices.last)

operator fun <T> Grid<T>.get(index: Coordinate): T {
    return get(index.row, index.column)
}

val Coordinate.row: Int get() = y
val Coordinate.column: Int get() = x
fun Coordinate.toPoint(): Point = x to y
fun Point.toCoordinate() = Coordinate(x = first, y = second)

infix fun Coordinate.manhattanDistance(other: Coordinate) = abs(x - other.x) + abs(y - other.y)
