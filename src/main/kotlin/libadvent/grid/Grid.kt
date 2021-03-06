package libadvent.grid

import libadvent.geometry.Coordinate
import libadvent.geometry.gridCoordinate


internal object EmptyIterator : ListIterator<Nothing> {
    override fun hasNext(): Boolean = false
    override fun hasPrevious(): Boolean = false
    override fun nextIndex(): Int = 0
    override fun previousIndex(): Int = -1
    override fun next(): Nothing = throw NoSuchElementException()
    override fun previous(): Nothing = throw NoSuchElementException()
}

interface Grid<out T> {
    val rows: Int
    val columns: Int

    fun isEmpty(): Boolean
    fun contains(element: @UnsafeVariance T): Boolean
    fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean

    operator fun get(row: Int, column: Int): T

    operator fun iterator(): Iterator<T>
}

interface MutableGrid<T> : Grid<T> {
    operator fun set(row: Int, column: Int, element: T): T
}

object EmptyGrid : Grid<Nothing> {
    override val rows: Int get() = 0
    override val columns: Int get() = 0

    override fun isEmpty(): Boolean = true
    override fun contains(element: Nothing): Boolean = false
    override fun containsAll(elements: Collection<Nothing>): Boolean = elements.isEmpty()

    override fun get(row: Int, column: Int): Nothing {
        throw IndexOutOfBoundsException("Empty list doesn't contain element at index ($row, $column).")
    }

    override operator fun iterator(): Iterator<Nothing> = EmptyIterator

    override fun equals(other: Any?): Boolean {
        return other === EmptyGrid || (other is Grid<*> && other.isEmpty())
    }
}

class ArrayGrid<T>(private val array: Array<Array<T>>) : MutableGrid<T> {
    override val rows: Int get() = array.size
    override val columns: Int get() = array.firstOrNull()?.size ?: 0

    override fun isEmpty(): Boolean = rows == 0 || columns == 0
    override fun contains(element: T): Boolean = array.any { it.contains(element) }
    override fun containsAll(elements: Collection<T>): Boolean = elements.all { contains(it) }

    override fun get(row: Int, column: Int): T {
        return array[row][column]
    }

    override fun set(row: Int, column: Int, element: T): T {
        val result = array[row][column]
        array[row][column] = element
        return result
    }

    override operator fun iterator(): Iterator<T> = iterator {
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                yield(get(row, column))
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Grid<*>) {
            return false
        }
        return hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        var result = array.contentDeepHashCode()
        result = 31 * result + rowIndices.hashCode()
        result = 31 * result + rowIndices.hashCode()
        return result
    }
}

val <T> Grid<T>.indices: List<Coordinate>
    get() = buildList {
        for (row in rowIndices) {
            for (column in columnIndices) {
                add(gridCoordinate(row, column))
            }
        }
    }

val <T> Grid<T>.rowIndices: IntRange
    get() = if (this is BoundedGrid) {
        bounds.minX..bounds.maxX
    } else {
        0 until rows
    }
val <T> Grid<T>.columnIndices: IntRange
    get() = if (this is BoundedGrid) {
        bounds.minY..bounds.maxY
    } else {
        0 until columns
    }
val <T> Grid<T>.size: Int get() = rows * columns
fun <T> Grid<T>.isNotEmpty(): Boolean = !isEmpty()


@Suppress("FunctionName")
inline fun <reified T> MutableGrid(rows: Int, columns: Int, init: (row: Int, column: Int) -> T): MutableGrid<T> {
    val array = Array(rows) { row ->
        Array(columns) { column ->
            init(row, column)
        }
    }
    return ArrayGrid(array)
}

@Suppress("FunctionName")
inline fun <reified T> MutableGrid(rows: Int, columns: Int, default: T): MutableGrid<T> {
    val array = Array(rows) {
        Array(columns) {
            default
        }
    }
    return ArrayGrid(array)
}

@Suppress("FunctionName")
inline fun <reified T> Grid(rows: Int, columns: Int, elementBuilder: (row: Int, column: Int) -> T): Grid<T> {
    if (rows == 0 || columns == 0) {
        return EmptyGrid
    }
    return MutableGrid(rows, columns, elementBuilder)
}

inline fun <reified T> Grid<T>.toMutableGrid(): MutableGrid<T> {
    return MutableGrid(rows, columns) { row, column ->
        this[row, column]
    }
}