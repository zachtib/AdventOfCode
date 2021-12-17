package twentytwentyone

import libadvent.asDigitInRange
import libadvent.geometry.Bounds
import libadvent.geometry.Coordinate
import libadvent.grid.BoundedGrid
import libadvent.grid.joinToString
import libadvent.part1
import libadvent.part2
import libadvent.resource.load
import libadvent.util.filterValuesNotNull
import libadvent.util.maximum
import libadvent.util.minimum
import libadvent.util.toPair

data class ProbeVelocity(val x: Int, val y: Int)

fun ProbeVelocity.applyingDragAndGravity(): ProbeVelocity {
    val dx = when {
        x > 0 -> -1
        x < 0 -> 1
        else -> 0
    }
    val dy = -1
    return copy(x = x + dx, y = y + dy)
}

data class ProbePosition(val x: Int, val y: Int)


fun ProbePosition.applyingVelocity(velocity: ProbeVelocity): ProbePosition {
    return ProbePosition(x = x + velocity.x, y = y + velocity.y)
}

data class TargetArea(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int)

operator fun TargetArea.contains(position: ProbePosition): Boolean {
    return position.x in minX..maxX && position.y in minY..maxY
}

fun String.toTargetArea(): TargetArea {
    val (xvalues, yvalues) = removePrefix("target area: ")
        .split(", ", limit = 2).map { substring ->
            val pair = substring.substring(2)
                .split("..", limit = 2)
                .map { it.toInt() }
                .toPair()
            pair.first..pair.second
        }
    return TargetArea(
        minX = xvalues.first,
        maxX = xvalues.last,
        minY = yvalues.first,
        maxY = yvalues.last,
    )
}

class Probe(
    velocity: ProbeVelocity,
    position: ProbePosition = ProbePosition(0, 0),
) {
    var velocity: ProbeVelocity = velocity
        private set

    var position: ProbePosition = position
        private set

    fun step() {
        position = position.applyingVelocity(velocity)
        velocity = velocity.applyingDragAndGravity()
    }

    override fun toString(): String {
        return "Probe at (${position.x}, ${position.y}) traveling at (dx=${velocity.x}, dy=${velocity.y})"
    }
}

fun runProbeSimulation(velocity: ProbeVelocity, targetArea: TargetArea): Int? {
    val probe = Probe(velocity = velocity)

    var maximumObservedY = Int.MIN_VALUE
    var missedTargetArea = false

    while (probe.position !in targetArea && !missedTargetArea) {
        val positionBeforeStepping = probe.position
        probe.step()
        if (positionBeforeStepping.x < targetArea.minX && probe.position.x > targetArea.maxX) {
            // We overshot by x
            missedTargetArea = true
        } else if (probe.position.y < targetArea.minY && probe.velocity.y < 0) {
            // fallen past the target
            missedTargetArea = true
        }
        if (probe.position.y > maximumObservedY) {
            maximumObservedY = probe.position.y
        }
    }
    return if (missedTargetArea) {
        null
    } else {
        maximumObservedY
    }
}

private fun searchVelocityBoundsToHitTargetArea(
    velocityBounds: Bounds,
    targetArea: TargetArea,
): Map<ProbeVelocity, Int> {
    val velocityIterator = iterator {
        for (x in velocityBounds.minX..velocityBounds.maxX) {
            for (y in velocityBounds.minY..velocityBounds.maxY) {
                yield(ProbeVelocity(x, y))
            }
        }
    }

    return velocityIterator
        .asSequence()
        .associateWith { v -> runProbeSimulation(v, targetArea) }
        .filterValuesNotNull()
}

fun day17Part1(
    targetArea: TargetArea,
    minX: Int = 0,
    minY: Int = -100,
    maxX: Int = 100,
    maxY: Int = 100,
): Int {
    val searchBounds = Bounds(minX, minY, maxX, maxY)

    val results = searchVelocityBoundsToHitTargetArea(
        velocityBounds = searchBounds,
        targetArea = targetArea,
    )

    val resultBounds = Bounds(
        minX = results.keys.minOf { it.x },
        minY = results.keys.minOf { it.y },
        maxX = results.keys.maxOf { it.x },
        maxY = results.keys.maxOf { it.y },
    )
    println("All values found were within $resultBounds")

    if (searchBounds.minX == resultBounds.minX ||
        searchBounds.minY == resultBounds.minY ||
        searchBounds.maxX == resultBounds.maxX ||
        searchBounds.maxY == resultBounds.maxY) {
        println("\n\nResult reaches edge of search area: $searchBounds\n\n")
    }

    val valuesRange = results.values.minimum()..results.values.maximum()

    val grid = BoundedGrid(resultBounds) { c: Coordinate ->
        results[ProbeVelocity(c.y, c.x)]
    }

    println(grid.joinToString { y -> y.asDigitInRange(valuesRange)})


    val max = results.maxOf { it.value }

    val points = results.filterValues { it == max }.keys.joinToString()
    println("Maximum values were at velocities: $points")
    return max
}

fun day17Part2(
    targetArea: TargetArea,
    minX: Int = 0,
    minY: Int = -100,
    maxX: Int = 100,
    maxY: Int = 100,
): Int {

    val results = searchVelocityBoundsToHitTargetArea(
        velocityBounds = Bounds(minX, minY, maxX, maxY),
        targetArea = targetArea,
    )

    return results.count()
}

fun main() {
    val input = load("2021/day17.txt")
    val targetArea = input.body.toTargetArea()

    part1 { day17Part1(targetArea, minY = 90, maxY = 300) }
    part2 { day17Part2(targetArea, minX = 0, maxX = 2000, minY = -1000, maxY = 1000) }
}