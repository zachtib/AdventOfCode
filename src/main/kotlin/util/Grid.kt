package util

interface Grid<T> {
    val rows: Int
    val columns: Int
    val items: Collection<T>

    val size: Int
    fun isEmpty(): Boolean
    fun contains(element: T): Boolean
    fun containsAll(elements: Collection<T>): Boolean
    fun iterator(): Iterator<T>

    val indices: Iterator<Point>

    operator fun get(row: Int, column: Int): T
}

interface MutableGrid<T> : Grid<T> {
    operator fun set(row: Int, column: Int, value: T)
}

internal object EmptyIterator : ListIterator<Nothing> {
    override fun hasNext(): Boolean = false
    override fun hasPrevious(): Boolean = false
    override fun nextIndex(): Int = 0
    override fun previousIndex(): Int = -1
    override fun next(): Nothing = throw NoSuchElementException()
    override fun previous(): Nothing = throw NoSuchElementException()
}

class EmptyGrid<T> : Grid<T> {
    override val size: Int get() = 0
    override fun isEmpty(): Boolean = true
    override fun contains(element: T): Boolean = false
    override fun containsAll(elements: Collection<T>): Boolean = elements.isEmpty()

    override fun get(row: Int, column: Int): Nothing {
        throw IndexOutOfBoundsException("Empty list doesn't contain element at index ($row, $column).")
    }

    override fun iterator(): Iterator<T> = EmptyIterator

    override val indices: Iterator<Point> get() = EmptyIterator

    override val rows: Int get() = 0
    override val columns: Int get() = 0
    override val items: Collection<Nothing> get() = emptyList()
}

class ArrayGrid<T>(private val array: Array<Array<T>>) : Grid<T>, MutableGrid<T> {

    override val rows: Int = array.size

    override val columns: Int = array.firstOrNull()?.size ?: 0

    override val items: Collection<T>
        get() = array.flatten()

    override fun get(row: Int, column: Int): T {
        return array[row][column]
    }

    override fun set(row: Int, column: Int, value: T) {
        array[row][column] = value
    }

    override val size: Int get() = rows * columns

    override fun contains(element: T): Boolean {
        return array.any { it.contains(element) }
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return elements.all { contains(it) }
    }

    override fun isEmpty(): Boolean = rows != 0 && columns != 0

    override fun iterator(): Iterator<T> = iterator {
        for ((row, column) in indices) {
            yield(get(row, column))
        }
    }

    override val indices: Iterator<Point> get() = iterator {
        for (row in array.indices) {
            for (column in array[row].indices) {
                yield(row to column)
            }
        }
    }
}

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

inline fun <reified T> buildGrid(rows: Int, columns: Int, elementBuilder: (row: Int, column: Int) -> T): Grid<T> {
    if (rows == 0 || columns == 0) {
        return EmptyGrid()
    }
    return MutableGrid(rows, columns, elementBuilder)
}

inline fun <reified T> Grid<T>.toMutableGrid(): MutableGrid<T> {
    return MutableGrid(rows, columns) { row, column ->
        this[row, column]
    }
}

operator fun <T> Grid<T>.get(point: Point) = get(point.first, point.second)
operator fun <T> MutableGrid<T>.set(point: Point, value: T) = set(point.first, point.second, value)
