package res

fun Resource.asStrings(): List<String> = items

fun <T> Resource.asType(transform: (String) -> T) = items.map(transform)

fun Resource.asInts(): List<Int> = items.map { it.toInt() }

fun Resource.asLongs(): List<Long> = items.map { it.toLong() }

inline fun <reified T> Resource.as2dArray(transform: (Char) -> T): Array<Array<T>> {
    val rows = items
    return Array(rows.size) { rowIndex: Int ->
        val row = rows[rowIndex]
        Array(row.length) { columnIndex: Int ->
            transform(row[columnIndex])
        }
    }
}