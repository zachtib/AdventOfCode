package libadvent.grid

import libadvent.geometry.Bounds
import libadvent.geometry.Coordinate
import libadvent.geometry.gridCoordinate

interface BoundedGrid<T> : Grid<T> {
    val bounds: Bounds
}

class MutableBoundedGrid<T>(
    override val bounds: Bounds,
    private val array: Array<Array<T>>,
) : BoundedGrid<T>, MutableGrid<T> {
    override val rows: Int get() = array.size
    override val columns: Int get() = array.firstOrNull()?.size ?: 0

    private val boundRowIndices: IntRange get() = bounds.minX..bounds.maxX
    private val boundColumnIndices: IntRange get() = bounds.minY..bounds.maxY

    override fun isEmpty(): Boolean = rows == 0 || columns == 0
    override fun contains(element: T): Boolean = array.any { it.contains(element) }
    override fun containsAll(elements: Collection<T>): Boolean = elements.all { contains(it) }

    override operator fun iterator(): Iterator<T> = iterator {
        for (row in boundRowIndices) {
            for (column in boundColumnIndices) {
                yield(get(row, column))
            }
        }
    }

    private fun getOffsetRow(row: Int) = row - bounds.minX
    private fun getOffsetColumn(column: Int) = column - bounds.minY

    override fun get(row: Int, column: Int): T {
        return array[getOffsetRow(row)][getOffsetColumn(column)]
    }

    override fun set(row: Int, column: Int, element: T): T {
        val offsetRow = getOffsetRow(row)
        val offsetColumn = getOffsetColumn(column)

        val result = array[offsetRow][offsetColumn]
        array[offsetRow][offsetColumn] = element
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Grid<*>) {
            return false
        }
        return hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        var result = array.contentDeepHashCode()
        result = 31 * result + boundRowIndices.hashCode()
        result = 31 * result + boundColumnIndices.hashCode()
        return result
    }
}

@Suppress("FunctionName")
inline fun <reified T> MutableBoundedGrid(bounds: Bounds, init: (row: Int, column: Int) -> T): MutableBoundedGrid<T> {
    val rows = bounds.maxX - bounds.minX + 1
    val columns = bounds.maxY - bounds.minY + 1

    val array = Array(rows) { row ->
        Array(columns) { column ->
            init(row + bounds.minX, column + bounds.minY)
        }
    }
    return MutableBoundedGrid(bounds, array)
}

@Suppress("FunctionName")
inline fun <reified T> MutableBoundedGrid(bounds: Bounds, init: (coordinate: Coordinate) -> T): MutableBoundedGrid<T> {
    val rows = bounds.maxX - bounds.minX + 1
    val columns = bounds.maxY - bounds.minY + 1

    val array = Array(rows) { row ->
        Array(columns) { column ->
            init(gridCoordinate(row + bounds.minX, column + bounds.minY))
        }
    }
    return MutableBoundedGrid(bounds, array)
}

@Suppress("FunctionName")
inline fun <reified T> BoundedGrid(bounds: Bounds, elementBuilder: (row: Int, column: Int) -> T): BoundedGrid<T> {
    return MutableBoundedGrid(bounds, elementBuilder)
}

@Suppress("FunctionName")
inline fun <reified T> BoundedGrid(bounds: Bounds, elementBuilder: (coordinate: Coordinate) -> T): BoundedGrid<T> {
    return MutableBoundedGrid(bounds, elementBuilder)
}