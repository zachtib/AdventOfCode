import util.*


fun Line.pointsAlong(): List<Point> {
    return if (isVertical) {
        return (y1..y2).map { Point(x1, it) }
    } else if (isHorizontal) {
        return (x1..x2).map { Point(it, y1) }
    } else {
        listOf(from, to)
    }
}

fun day5Part1(lines: List<Line>): Int {
    val filteredLines = lines.filter { it.isHorizontal || it.isVertical }
    val grid = mutableGridOf(0, 0, 0)

    return -1
}

fun day5Part2(): Int {
    return -1
}

fun main() {
    val input = resource("day5.txt").asType { it.toLine() }

    day5Part1(input).part1Result()
    day5Part2().part2Result()
}
