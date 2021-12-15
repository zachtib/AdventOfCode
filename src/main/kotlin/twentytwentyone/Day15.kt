package twentytwentyone

import libadvent.geometry.*
import libadvent.grid.*
import libadvent.part1
import libadvent.part2
import libadvent.resource.asGrid
import libadvent.resource.load
import kotlin.math.min


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

typealias GridPath = List<Coordinate>

fun findAllAvailablePaths(
    grid: Grid<Any>,
    currentPosition: Coordinate,
    pathSoFar: GridPath = emptyList(),
): List<GridPath> {
    val updatingPath = pathSoFar.toMutableList()
    updatingPath.add(currentPosition)
    val path = updatingPath.toList()

    if (currentPosition.row == grid.rowIndices.last && currentPosition.column == grid.columnIndices.last) {
        return listOf(path)
    }
    // Find the next available steps
    val adjacentCoordinates: List<Coordinate> = grid.pointsInGridAdjacentTo(currentPosition)
    val coordinatesNotYetVisited = adjacentCoordinates.filterNot { it in pathSoFar }

    return if (coordinatesNotYetVisited.isEmpty()) {
        emptyList()
    } else {
        coordinatesNotYetVisited.flatMap { nextCoordinate ->
            findAllAvailablePaths(grid, nextCoordinate, path)
        }
    }
}

private fun findPathsRecursivelyWithBaseline(
    grid: Grid<RiskLevel>,
    currentPosition: Coordinate,
    endingPosition: Coordinate,
    currentPath: GridPath,
    currentRiskLevel: RiskLevel,
    baselineRiskLevel: RiskLevel,
): List<Pair<GridPath, RiskLevel>> {
    if (currentPosition == endingPosition) {
        // ta-da, we are at the destination already
        return listOf(currentPath to currentRiskLevel)
    }
    val adjacentCoordinates: List<Coordinate> = grid.pointsInGridAdjacentTo(currentPosition)

    if (endingPosition in adjacentCoordinates) {
        // Go directly to the destination
        val updatedPath = currentPath + endingPosition
        val updatedRiskLevel = currentRiskLevel + grid[endingPosition]
        return listOf(updatedPath to updatedRiskLevel)
    }

    val acceptableRisk = baselineRiskLevel - currentRiskLevel
    val acceptableCoordinates = adjacentCoordinates.filter { coor ->
        grid[coor].level <= acceptableRisk.level && coor !in currentPath
    }
    return acceptableCoordinates.flatMap { nextPosition ->
        findPathsRecursivelyWithBaseline(
            grid,
            nextPosition,
            endingPosition,
            currentPath + nextPosition,
            currentRiskLevel + grid[nextPosition],
            baselineRiskLevel
        )
    }
}

fun findPathsRecursivelyWithBaseline(
    grid: Grid<RiskLevel>,
    startingPosition: Coordinate,
    endingPosition: Coordinate,
    baselineRiskLevel: RiskLevel,
): List<Pair<GridPath, RiskLevel>> {
    return findPathsRecursivelyWithBaseline(
        grid,
        startingPosition,
        endingPosition,
        listOf(startingPosition),
        RiskLevel(0),
        baselineRiskLevel
    )
}

fun Grid<RiskLevel>.determineNaivePath(
    startingPosition: Coordinate = gridCoordinate(0, 0),
    destination: Coordinate = gridCoordinate(rowIndices.last, columnIndices.last)
): Pair<GridPath, RiskLevel> {
    val path = mutableListOf(startingPosition)

    var position = startingPosition
    var risk = 0
    while (position != destination) {
        val right = gridCoordinate(position.row, position.column + 1)
        val down = gridCoordinate(position.row + 1, position.column)

        position = if (right == destination || down == destination) {
            destination
        } else if (right.column > destination.column) {
            down
        } else if (down.row > destination.row) {
            right
        } else {
            val rightRisk = this[right].level
            val downRisk = this[down].level

            if (rightRisk < downRisk) {
                right
            } else {
                down
            }
        }
        path.add(position)
        risk += this[position].level
    }
    return path to RiskLevel(risk)
}

fun Grid<RiskLevel>.calculateTotalRisk(path: GridPath): Int {
    return path.map { coordinate ->
        this[coordinate]
    }.sumOf { it.level }
}

data class PathNode(
    var isVisited: Boolean,
    var tentativeDistance: Int
)

fun findOptimalPath(
    distances: Grid<RiskLevel>,
    startingPosition: Coordinate = distances.upperLeft,
    endingPosition: Coordinate = distances.lowerRight,
): Int {
    val nodes: Grid<PathNode> = distances.map { risk ->
        PathNode(
            isVisited = false,
            tentativeDistance = Int.MAX_VALUE
        )
    }

    val unvisitedCoordinates = nodes.coordinateIterator()
        .asSequence()
        .toMutableSet()

    var currentPosition = startingPosition
    val destinationNode = nodes[endingPosition]

    nodes[startingPosition].tentativeDistance = 0

    while (unvisitedCoordinates.isNotEmpty()) {
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
    println(nodes.joinToString(columnSeparator = ", ") { node ->
        node.tentativeDistance.toString().padStart(3)
    })
    return destinationNode.tentativeDistance
}

fun day15Part1(riskLevels: Grid<RiskLevel>): Int {
    return findOptimalPath(riskLevels)
}

fun day15Part2(riskLevels: Grid<RiskLevel>): Int {
    throw NotImplementedError()
}

fun main() {
    val riskLevels = load("2021/day15.txt")
        .asGrid { char -> RiskLevel(char.digitToInt()) }

    part1 { day15Part1(riskLevels) }
    part2 { day15Part2(riskLevels) }
}