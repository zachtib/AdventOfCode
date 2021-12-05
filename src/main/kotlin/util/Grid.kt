package util

class Grid<T> private constructor(
    val rowCount: Int,
    val columnCount: Int,
    private val data: MutableList<T>,
) {
//) : Map<Pair<Int, Int>, T> {

    companion object {
        fun <T> build(rows: Int, columns: Int, builder: (row: Int, column: Int) -> T): Grid<T> {
            val data = mutableListOf<T>()
            for (row in 0 until rows) {
                for (column in 0 until columns) {
                    data.add(builder(row, column))
                }
            }
            return Grid(rows, columns, data)
        }
    }

    val rows: List<List<T>>
        get() = data.windowed(columnCount)

    val columns: List<List<T>>
        get() = (0 until columnCount).map { index -> rows.map { it[index] } }

    val items: List<T>
        get() = data.toList()

    operator fun get(key: Pair<Int, Int>): T {
        val (row, column) = key
        if (row !in 0 until rowCount || column !in 0 until columnCount) {
            throw ArrayIndexOutOfBoundsException()
        }
        return data[row * columnCount + column]
    }
}
