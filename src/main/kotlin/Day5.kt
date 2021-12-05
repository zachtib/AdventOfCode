import util.*


fun rangeIgnoringOrder(i1: Int, i2: Int): IntRange {
    return if (i1 > i2) {
        i2..i1
    } else {
        i1..i2
    }
}

fun Line.pointsAlong(): List<Point> {
    return if (isVertical) {
        rangeIgnoringOrder(y1, y2).map { Point(x1, it) }
    } else if (isHorizontal) {
        rangeIgnoringOrder(x1, x2).map { Point(it, y1) }
    } else {
        listOf(from, to)
    }
}

fun day5Part1(lines: List<Line>): Int {
    val bounds = lines.bounds()
    val grid = mutableGridOf(bounds.maxX + 1, bounds.maxY + 1, 0)

    val verticalOrHorizontalLines = lines.filter { it.isHorizontal || it.isVertical }

    for (line in verticalOrHorizontalLines) {
        val pointsOnLine = line.pointsAlong()
        for (point in pointsOnLine) {
            val (x, y) = point
            grid[x, y] = grid[x, y] + 1
        }
    }

    for (col in grid.columns) {
        println(col.joinToString("") { if (it == 0) "." else it.toString() })
    }

    return grid.items.count { it >= 2 }
}

fun day5Part2(): Int {
    return -1
}

fun main() {
    val input = resource("day5.txt").asType { it.toLine() }

    day5Part1(input).part1Result()
    day5Part2().part2Result()
}
