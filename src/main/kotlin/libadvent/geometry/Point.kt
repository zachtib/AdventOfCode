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