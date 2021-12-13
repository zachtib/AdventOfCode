package libadvent.geometry


data class Bounds(
    val minX: Int,
    val minY: Int,
    val maxX: Int,
    val maxY: Int,
)

fun Collection<Line>.bounds(): Bounds {
    val allX: List<Int> = flatMap { listOf(it.x1, it.x2) }
    val allY = flatMap { listOf(it.y1, it.y2) }
    return Bounds(
        minX = allX.minOrNull() ?: 0,
        minY = allY.minOrNull() ?: 0,
        maxX = allX.maxOrNull() ?: 0,
        maxY = allY.maxOrNull() ?: 0,
    )
}