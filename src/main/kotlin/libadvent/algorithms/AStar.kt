package libadvent.algorithms

import libadvent.NoPathFoundException
import libadvent.geometry.*
import libadvent.grid.Grid

fun solveAStarForGrid(
    distances: Grid<Int>,
    start: Coordinate = distances.upperLeft,
    destination: Coordinate = distances.lowerRight,
    heuristic: (Coordinate) -> Int = { it manhattanDistance destination },
): Int {
    val openSet = mutableSetOf(start)
    val gScore = mutableMapOf(start to 0)
    val fScore = mutableMapOf(start to heuristic(start))

    while (openSet.isNotEmpty()) {
        val current = openSet.minByOrNull { fScore[it] ?: Int.MAX_VALUE } ?: break
        val currentScore = gScore[current] ?: break
        if (current == destination) {
            return currentScore
        }
        openSet.remove(current)
        for (neighbor in distances.pointsInGridAdjacentTo(current)) {
            val tentativeScore = currentScore + distances[neighbor]
            if (tentativeScore < (gScore[neighbor] ?: Int.MAX_VALUE)) {
                gScore[neighbor] = tentativeScore
                fScore[neighbor] = tentativeScore + heuristic(neighbor)
                if (neighbor !in openSet) {
                    openSet.add(neighbor)
                }
            }
        }
    }
    throw NoPathFoundException()
}
