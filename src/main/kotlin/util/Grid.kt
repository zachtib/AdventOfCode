package util

interface Grid<T> {
    val rowCount: Int
    val columnCount: Int
    val rows: List<List<T>>
    val columns: List<List<T>>
    val items: Collection<T>

    operator fun get(row: Int, column: Int): T
}

interface MutableGrid<T> : Grid<T> {
    operator fun set(row: Int, column: Int, value: T)
}

class ListGrid<T>(
    override val rowCount: Int,
    override val columnCount: Int,
    private val data: MutableList<T>,
) : MutableGrid<T> {

    override val rows: List<List<T>>
        get() = data.windowed(columnCount, step = columnCount)

    override val columns: List<List<T>>
        get() = (0 until columnCount).map { index -> rows.map { it[index] } }

    override val items: Collection<T>
        get() = data.toList()

    override operator fun get(row: Int, column: Int): T {
        if (row !in 0 until rowCount || column !in 0 until columnCount) {
            throw IndexOutOfBoundsException()
        }
        return data[row * columnCount + column]
    }

    override operator fun set(row: Int, column: Int, value: T) {
        if (row !in 0 until rowCount || column !in 0 until columnCount) {
            throw IndexOutOfBoundsException()
        }
        data[row * columnCount + column] = value
    }
}

fun <T> gridBuilder(rows: Int, columns: Int, builder: (row: Int, column: Int) -> T): Grid<T> {
    val data = mutableListOf<T>()
    for (row in 0 until rows) {
        for (column in 0 until columns) {
            data.add(builder(row, column))
        }
    }
    return ListGrid(rows, columns, data)
}

fun <T> mutableGridOf(rows: Int, columns: Int, default: T): MutableGrid<T> {
    return ListGrid(rows, columns, MutableList(rows * columns) { default } )
}
