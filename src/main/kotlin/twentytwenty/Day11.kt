package twentytwenty

import res.load
import libadvent.util.as2dArray

sealed class Tile {
    data class Seat(var occupied: Boolean) : Tile()
    object Floor : Tile()
}

data class Vector(val row: Int, val column: Int) {
    fun scale(magnitude: Int): Vector {
        return copy(row = row * magnitude, column = column * magnitude)
    }

    companion object {
        val allUnits = listOf(
            Vector(row = -1, column = -1),
            Vector(row = -1, column = 0),
            Vector(row = -1, column = 1),
            Vector(row = 0, column = -1),
            Vector(row = 0, column = 1),
            Vector(row = 1, column = -1),
            Vector(row = 1, column = 0),
            Vector(row = 1, column = 1),
        )
    }
}

fun Collection<Tile>.countOccupied(): Int {
    var sum = 0
    for (item in this) {
        if (item is Tile.Seat && item.occupied) {
            sum++
        }
    }
    return sum
}

fun Array<Array<Tile>>.deepCopy(): Array<Array<Tile>> {
    return Array(size) { row ->
        Array(this[row].size) { column ->
            when (val tile = this[row][column]) {
                is Tile.Seat -> tile.copy()
                Tile.Floor -> tile
            }
        }
    }
}

class Layout(private val array: Array<Array<Tile>>) {

    constructor(fromString: String) : this(fromString.as2dArray {
        when (it) {
            '.' -> Tile.Floor
            'L' -> Tile.Seat(occupied = false)
            '#' -> Tile.Seat(occupied = true)
            else -> throw IllegalArgumentException("fromString must only contain ., L, and # characters")
        }
    })

    fun tileAt(row: Int, column: Int) = array[row][column]

    fun adjacentTiles(row: Int, column: Int): List<Tile> {
        val result = mutableListOf<Tile>()

        if (row > 0) {
            if (column > 0) {
                result.add(array[row - 1][column - 1])
            }
            result.add(array[row - 1][column])
            if (column < array[0].size - 1) {
                result.add(array[row - 1][column + 1])
            }
        }
        if (column > 0) {
            result.add(array[row][column - 1])
        }
        if (column < array[0].size - 1) {
            result.add(array[row][column + 1])
        }
        if (row < array.size - 1) {
            if (column > 0) {
                result.add(array[row + 1][column - 1])
            }
            result.add(array[row + 1][column])
            if (column < array[0].size - 1) {
                result.add(array[row + 1][column + 1])
            }
        }

        return result
    }

    fun occupiedSeatsAdjacentTo(row: Int, column: Int): Int {
        return adjacentTiles(row, column).countOccupied()
    }

    fun runRound() {
        val toFlip = mutableListOf<Tile.Seat>()
        for (row in array.indices) {
            for (column in array[row].indices) {
                val tile = tileAt(row, column)
                if (tile is Tile.Seat) {
                    val adjacent = occupiedSeatsAdjacentTo(row, column)
                    if ((!tile.occupied && adjacent == 0) || (tile.occupied && adjacent >= 4)) {
                        toFlip.add(tile)
                    }
                }
            }
        }
        for (seat in toFlip) {
            seat.occupied = !seat.occupied
        }
    }

    fun runUntilStable() {
        var shouldContinue = true
        while (shouldContinue) {
            val snapshot = Layout(array.deepCopy())
            runRound()
            shouldContinue = (this != snapshot)
        }
    }

    fun totalOccupiedSeats(): Int {
        var count = 0
        for (row in array) {
            for (tile in row) {
                if (tile is Tile.Seat && tile.occupied) {
                    count++
                }
            }
        }
        return count
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Layout) return false
        return hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return array.contentDeepHashCode()
    }

    override fun toString(): String {
        return array.joinToString("\n") { row ->
            row.joinToString("") { tile ->
                when (tile) {
                    is Tile.Seat -> if (tile.occupied) "#" else "L"
                    Tile.Floor -> "."
                }
            }
        }
    }

    fun seatsVisibleFrom(row: Int, column: Int): List<Tile.Seat> {
        val result = mutableListOf<Tile.Seat>()
        for (unitVector in Vector.allUnits) {
            var magnitude = 1
            var scaledVector = unitVector.scale(magnitude)
            var offsetRow = row + scaledVector.row
            var offsetColumn = column + scaledVector.column

            while (offsetRow in array.indices && offsetColumn in array[offsetRow].indices) {
                val tile = tileAt(offsetRow, offsetColumn)
                if (tile is Tile.Seat) {
                    result.add(tile)
                    break
                }
                magnitude++
                scaledVector = unitVector.scale(magnitude)
                offsetRow = row + scaledVector.row
                offsetColumn = column + scaledVector.column
            }
        }
        return result
    }

    fun runRound2() {
        val toFlip = mutableListOf<Tile.Seat>()
        for (row in array.indices) {
            for (column in array[row].indices) {
                val tile = tileAt(row, column)
                if (tile is Tile.Seat) {
                    val occupied = seatsVisibleFrom(row, column).countOccupied()
                    if ((!tile.occupied && occupied == 0) || (tile.occupied && occupied >= 5)) {
                        toFlip.add(tile)
                    }
                }
            }
        }
        for (seat in toFlip) {
            seat.occupied = !seat.occupied
        }
    }

    fun runUntilStable2() {
        var shouldContinue = true
        while (shouldContinue) {
            val snapshot = Layout(array.deepCopy())
            runRound2()
            shouldContinue = (this != snapshot)
        }
    }
}


fun main() {
    val input = load("day11.txt").body
    val waitingRoom = Layout(input)

    // Part 1
    waitingRoom.runUntilStable()
    val part1Result = waitingRoom.totalOccupiedSeats()
    println("Part 1 Result: $part1Result")

    // Part 2
    val waitingRoom2 = Layout(input)
    waitingRoom2.runUntilStable2()
    val part2Result = waitingRoom2.totalOccupiedSeats()
    println("Part 2 Result: $part2Result")
}
