import util.*


fun rangeIgnoringOrder(i1: Int, i2: Int): Iterable<Int> {
    val step = if (i1 > i2) -1 else 1
    return IntProgression.fromClosedRange(i1, i2, step)
}

fun Line.pointsAlong(): List<Point> {
    return if (isVertical) {
        rangeIgnoringOrder(y1, y2).map { Point(x1, it) }
    } else if (isHorizontal) {
        rangeIgnoringOrder(x1, x2).map { Point(it, y1) }
    } else if (isDiagonal) {
        val xRange = rangeIgnoringOrder(x1, x2)
        val yRange = rangeIgnoringOrder(y1, y2)

        xRange.zip(yRange).map { (x, y) -> Point(x, y) }
    } else {
        return listOf()
    }
}

private fun printGrid(grid: Grid<Int>) {
    for (col in grid.columns) {
        println(col.joinToString("") { if (it == 0) "." else it.toString() })
    }
}

private fun calculateGridForLines(lines: List<Line>): Int {
    val bounds = lines.bounds()
    val grid = mutableGridOf(bounds.maxX + 1, bounds.maxY + 1, 0)

    for (line in lines) {
        val pointsOnLine = line.pointsAlong()
        for (point in pointsOnLine) {
            val (x, y) = point
            grid[x, y] = grid[x, y] + 1
        }
    }

    return grid.items.count { it >= 2 }
}

fun day5Part1(lines: List<Line>): Int {
    val verticalOrHorizontalLines = lines.filter { it.isHorizontal || it.isVertical }
    return calculateGridForLines(verticalOrHorizontalLines)
}

fun day5Part2(lines: List<Line>): Int {
    val verticalHorizontalOrDiagonalLines = lines.filter { it.isHorizontal || it.isVertical || it.isDiagonal }
    return calculateGridForLines(verticalHorizontalOrDiagonalLines)
}

fun main() {
    val input = resource("day5.txt").asType { it.toLine() }

    day5Part1(input).part1Result()
    day5Part2(input).part2Result()
}
