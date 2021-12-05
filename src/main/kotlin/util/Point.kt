package util

data class Point(val x: Int, val y: Int)

fun String.toPoint(): Point {
    val (xString, yString) = this.split(",", limit = 2)
    return Point(xString.toInt(), yString.toInt())
}