package twentytwentyone

import libadvent.geometry.*
import libadvent.grid.*
import libadvent.part1
import libadvent.part2
import libadvent.resource.asGrid
import libadvent.resource.load


@JvmInline
value class RiskLevel(val level: Int)

operator fun RiskLevel.plus(other: RiskLevel): RiskLevel {
    return RiskLevel(this.level + other.level)
}

operator fun RiskLevel.minus(other: RiskLevel): RiskLevel {
    return RiskLevel(this.level - other.level)
}

operator fun RiskLevel.compareTo(other: RiskLevel): Int {
    return level.compareTo(other.level)
}

data class PathNode(
    var isVisited: Boolean,
    var tentativeDistance: Int
)

class ScaledGrid(
    private val subGrid: Grid<RiskLevel>,
    private val scale: Int
) : Grid<RiskLevel> by subGrid {

    override val rows: Int get() = subGrid.rows * scale
    override val columns: Int get() = subGrid.rows * scale

    override fun get(row: Int, column: Int): RiskLevel {

        val rowOffset = row / subGrid.rows
        val translatedRow = row % subGrid.rows

        val columnOffset = column / subGrid.columns
        val translatedColumn = column % subGrid.columns

        val originalValue = subGrid[translatedRow, translatedColumn]

        var newValue = originalValue.level + rowOffset + columnOffset

        while (newValue > 9) {
            newValue -= 9
        }

        return RiskLevel(newValue)
    }
}

fun findOptimalPath(
    distances: Grid<RiskLevel>,
    startingPosition: Coordinate = distances.upperLeft,
    endingPosition: Coordinate = distances.lowerRight,
): Int {
    val nodes: Grid<PathNode> = distances.map {
        PathNode(
            isVisited = false,
            tentativeDistance = Int.MAX_VALUE
        )
    }

    val unvisitedCoordinates = nodes.coordinateIterator()
        .asSequence()
        .toMutableSet()

    println("Total nodes: ${unvisitedCoordinates.size}")
    val percent = unvisitedCoordinates.size / 100

    var currentPosition = startingPosition
    val destinationNode = nodes[endingPosition]

    nodes[startingPosition].tentativeDistance = 0

    while (unvisitedCoordinates.isNotEmpty()) {
        if (unvisitedCoordinates.size % percent == 0) {
            val p = 100 - (unvisitedCoordinates.size / percent)
            println("$p%")
        }
        val currentNode = nodes[currentPosition]

        val unvisitedNeighbors = nodes.pointsInGridAdjacentTo(currentPosition)
            .filter { !nodes[it].isVisited }

        unvisitedNeighbors.forEach { coordinate ->
            val node = nodes[coordinate]
            val distance = distances[coordinate].level
            val tentativeDistance = currentNode.tentativeDistance + distance

            if (tentativeDistance < node.tentativeDistance) {
                node.tentativeDistance = tentativeDistance
            }
        }

        currentNode.isVisited = true
        if (destinationNode.isVisited) {
            break
        }
        unvisitedCoordinates.remove(currentPosition)

        currentPosition = unvisitedCoordinates.minByOrNull {
            nodes[it].tentativeDistance
        } ?: break
    }
    return destinationNode.tentativeDistance
}

fun day15Part1(riskLevels: Grid<RiskLevel>): Int {
    return findOptimalPath(riskLevels)
}

fun day15Part2(riskLevels: Grid<RiskLevel>): Int {
    return findOptimalPath(ScaledGrid(riskLevels, 5))
}

fun main() {
    val riskLevels = load("2021/day15.txt")
        .asGrid { char -> RiskLevel(char.digitToInt()) }

    part1 { day15Part1(riskLevels) }
    part2 { day15Part2(riskLevels) }
}