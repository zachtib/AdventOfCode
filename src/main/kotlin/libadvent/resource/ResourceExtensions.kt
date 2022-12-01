package libadvent.resource

import libadvent.grid.ArrayGrid
import libadvent.grid.Grid

fun Resource.asStrings(): List<String> = items

fun <T> Resource.asType(transform: (String) -> T) = items.map(transform)

fun Resource.asInts(): List<Int> = items.map { it.toInt() }

fun Resource.asLongs(): List<Long> = items.map { it.toLong() }

fun <T> Resource.asLists(transform: (String) -> T): List<List<T>> {
    return body.split("\n\n")
        .map { group ->
            group.split("\n")
                .map(transform)
        }
}

fun Resource.asListsOfInts() = asLists { it.toInt() }

inline fun <reified T> Resource.as2dArray(transform: (Char) -> T): Array<Array<T>> {
    val rows = items
    return Array(rows.size) { rowIndex: Int ->
        val row = rows[rowIndex]
        Array(row.length) { columnIndex: Int ->
            transform(row[columnIndex])
        }
    }
}

inline fun <reified T> Resource.asGrid(
    transform: (Char) -> T
): Grid<T> {
    return ArrayGrid(as2dArray(transform))
}