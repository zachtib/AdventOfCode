package libadvent.algorithms

import libadvent.geometry.*
import libadvent.grid.Grid
import libadvent.grid.map


data class DijkstraGridNode(
    var tentativeDistance: Int,
    val stepDistance: Int,
)


fun solveDijkstraForGrid(
    distances: Grid<Int>,
    startingPosition: Coordinate = distances.upperLeft,
    destinationPosition: Coordinate = distances.lowerRight,
): Int {
    val nodes = distances.map { distance ->
        DijkstraGridNode(
            tentativeDistance = Int.MAX_VALUE,
            stepDistance = distance
        )
    }
    nodes[startingPosition].tentativeDistance = 0

    val unvisitedCoordinates = nodes.coordinateIterator()
        .asSequence()
        .toMutableSet()

    var currentPosition = startingPosition

    while (destinationPosition in unvisitedCoordinates) {
        val currentNode = nodes[currentPosition]

        val unvisitedNeighbors = nodes.pointsInGridAdjacentTo(currentPosition)
            .filter { it in unvisitedCoordinates }

        unvisitedNeighbors.forEach { coordinate ->
            val node = nodes[coordinate]
            val distance = distances[coordinate]
            val tentativeDistance = currentNode.tentativeDistance + distance

            if (tentativeDistance < node.tentativeDistance) {
                node.tentativeDistance = tentativeDistance
            }
        }

        unvisitedCoordinates.remove(currentPosition)
        currentPosition = unvisitedCoordinates.minByOrNull { nodes[it].tentativeDistance } ?: break
    }
    return nodes[destinationPosition].tentativeDistance
}


fun <T : Any> solveDijkstraForGrid(
    items: Grid<T>,
    getDistance: (T) -> Int,
    startingPosition: Coordinate = items.upperLeft,
    destinationPosition: Coordinate = items.lowerRight,
): Int {
    return solveDijkstraForGrid(items.map(getDistance), startingPosition, destinationPosition)
}