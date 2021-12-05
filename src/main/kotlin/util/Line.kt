package util

data class Line(val from: Point, val to: Point)

val Line.x1: Int
    get() = from.x

val Line.x2: Int
    get() = to.x

val Line.y1: Int
    get() = from.y

val Line.y2: Int
    get() = to.y

val Line.isHorizontal: Boolean
    get() = y1 == y2

val Line.isVertical: Boolean
    get() = x1 == x2

infix fun Point.lineTo(other: Point): Line {
    return Line(this, other)
}

fun String.toLine(): Line {
    val (p1String, p2String) = this.split(" -> ", limit = 2)
    return Line(from = p1String.toPoint(), to = p2String.toPoint())
}