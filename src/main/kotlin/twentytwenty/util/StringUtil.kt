package twentytwenty.util

inline fun <reified T> String.as2dArray(transform: (Char) -> T): Array<Array<T>> {
    val rows = lines()
    return Array(rows.size) { row ->
        Array(rows[row].length) { column ->
            transform(rows[row][column])
        }
    }
}