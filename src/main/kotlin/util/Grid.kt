package util

interface Grid<T> {
    val rowCount: Int
    val columnCount: Int
    val rows: List<List<T>>
    val columns: List<List<T>>

    operator fun get(row: Int, column: Int): T
}

interface MutableGrid<T> : Grid<T> {
    operator fun set(row: Int, column: Int, value: T)
}

class ListGrid<T>(
    override val rowCount: Int,
    override val columnCount: Int,
    private val data: MutableList<T>,
) : Grid<T> {

    override val rows: List<List<T>>
        get() = data.windowed(columnCount)

    override val columns: List<List<T>>
        get() = (0 until columnCount).map { index -> rows.map { it[index] } }

    val items: List<T>
        get() = data.toList()

    override operator fun get(row: Int, column: Int): T {
        if (row !in 0 until rowCount || column !in 0 until columnCount) {
            throw ArrayIndexOutOfBoundsException()
        }
        return data[row * columnCount + column]
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
