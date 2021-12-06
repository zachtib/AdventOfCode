package twentytwenty

import kotlin.math.abs

enum class Direction {
    N,
    S,
    E,
    W,
    L,
    R,
    F,
}

enum class Facing {
    NORTH,
    SOUTH,
    EAST,
    WEST,
}

data class Movement(val direction: Direction, val amount: Int)

fun String.toMovement(): Movement {
    val directionString = substring(0, 1)
    val amountString = substring(1)
    val direction = Direction.valueOf(directionString)
    val amount = amountString.toInt()
    return Movement(direction, amount)
}

abstract class Ship {
    var east: Int = 0
        protected set
    var north: Int = 0
        protected set

    fun move(movement: Movement) {
        when (movement.direction) {
            Direction.N -> moveNorth(movement.amount)
            Direction.S -> moveSouth(movement.amount)
            Direction.E -> moveEast(movement.amount)
            Direction.W -> moveWest(movement.amount)
            Direction.L -> rotateLeft(movement.amount)
            Direction.R -> rotateRight(movement.amount)
            Direction.F -> moveForward(movement.amount)
        }
    }

    fun manhattanDistance(): Int {
        return abs(east) + abs(north)
    }

    protected abstract fun moveNorth(amount: Int)

    protected abstract fun moveSouth(amount: Int)

    protected abstract fun moveEast(amount: Int)

    protected abstract fun moveWest(amount: Int)

    protected abstract fun rotateLeft(degrees: Int)

    protected abstract fun rotateRight(degrees: Int)

    protected abstract fun moveForward(amount: Int)
}

class SimpleShip : Ship() {
    private var facing: Facing = Facing.EAST

    override fun moveNorth(amount: Int) {
        north += amount
    }

    override fun moveSouth(amount: Int) {
        north -= amount
    }

    override fun moveEast(amount: Int) {
        east += amount
    }

    override fun moveWest(amount: Int) {
        east -= amount
    }

    /**
     * Turns right by 90 degrees
     */
    private fun turn() {
        facing = when (facing) {
            Facing.NORTH -> Facing.EAST
            Facing.SOUTH -> Facing.WEST
            Facing.EAST -> Facing.SOUTH
            Facing.WEST -> Facing.NORTH
        }
    }

    override fun rotateRight(degrees: Int) {
        require(degrees in listOf(90, 180, 270)) { "Only right angle turns are supported" }
        for (i in 1..(degrees / 90)) {
            turn()
        }
    }

    override fun rotateLeft(degrees: Int) {
        require(degrees in listOf(90, 180, 270)) { "Only right angle turns are supported" }
        rotateRight(360 - degrees)
    }

    override fun moveForward(amount: Int) {
        when (facing) {
            Facing.NORTH -> moveNorth(amount)
            Facing.SOUTH -> moveSouth(amount)
            Facing.EAST -> moveEast(amount)
            Facing.WEST -> moveWest(amount)
        }
    }
}

class WaypointShip : Ship() {
    var waypointNorth: Int = 1
        private set
    var waypointEast: Int = 10
        private set

    override fun moveNorth(amount: Int) {
        waypointNorth += amount
    }

    override fun moveSouth(amount: Int) {
        waypointNorth -= amount
    }

    override fun moveEast(amount: Int) {
        waypointEast += amount
    }

    override fun moveWest(amount: Int) {
        waypointEast -= amount
    }

    override fun rotateLeft(degrees: Int) {
        rotateRight(360 - degrees)
    }

    override fun rotateRight(degrees: Int) {
        require(degrees in listOf(90, 180, 270)) { "Only right angle turns are supported" }
        for (i in 1..(degrees / 90)) {
            val newEast = waypointNorth
            waypointNorth = -waypointEast
            waypointEast = newEast
        }
    }

    override fun moveForward(amount: Int) {
        north += (waypointNorth * amount)
        east += (waypointEast * amount)
    }
}

fun main() {
    val movement = Resources.load("day12.txt") { it.toMovement() }
    val ship = SimpleShip()
    for (move in movement) {
        ship.move(move)
    }
    println("Part 1 Solution: ${ship.manhattanDistance()}")

    val waypointShip = WaypointShip()
    for (move in movement) {
        waypointShip.move(move)
    }
    println("Part 2 Solution: ${waypointShip.manhattanDistance()}")
}