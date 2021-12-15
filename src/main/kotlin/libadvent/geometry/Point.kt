package libadvent.geometry

typealias Point = Pair<Int, Int>

val Point.x: Int
    get() = first

val Point.y: Int
    get() = second

fun String.toPoint(): Point {
    val (xString, yString) = this.split(",", limit = 2)
    return Point(xString.toInt(), yString.toInt())
}

data class MutablePoint(var x: Int, var y: Int)

fun MutablePoint.toPoint() = x to y
val MutablePoint.row: Int get() = y
val MutablePoint.column: Int get() = x