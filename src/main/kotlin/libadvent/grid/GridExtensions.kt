package libadvent.grid

import libadvent.geometry.*

inline fun <T, reified R> Grid<T>.map(transform: (T) -> R): Grid<R> {
    return Grid(rows, columns) { row, column ->
        transform(this[row, column])
    }
}

fun <T> Grid<T>.forEach(func: (item: T) -> Unit) {
    for (item in this) {
        func(item)
    }
}

fun <T> Grid<T>.forEachIndexed(
    func: (index: Coordinate, item: T) -> Unit,
) = indices.forEach { index ->
    func(index, get(index))
}

fun <T> Grid<T>.filter(predicate: (T) -> Boolean) = iterator {
    for (item in this@filter) {
        if (predicate(item)) {
            yield(item)
        }
    }
}

fun <T> Grid<T>.pointsInGridAdjacentTo(reference: Coordinate): List<Coordinate> {
    val rowIndex = reference.row
    val columnIndex = reference.column

    return listOf(
        gridCoordinate(rowIndex - 1, columnIndex),
        gridCoordinate(rowIndex + 1, columnIndex),
        gridCoordinate(rowIndex, columnIndex - 1),
        gridCoordinate(rowIndex, columnIndex + 1),
    ).filter { coordinate ->
        coordinate.row in rowIndices && coordinate.column in columnIndices
    }
}

fun <T> Grid<T>.pointsInGridAdjacentOrDiagonalTo(reference: Coordinate): List<Coordinate> {
    val rowIndex = reference.row
    val columnIndex = reference.column

    return listOf(
        gridCoordinate(rowIndex - 1, columnIndex -1),
        gridCoordinate(rowIndex - 1, columnIndex),
        gridCoordinate(rowIndex - 1, columnIndex + 1),
        gridCoordinate(rowIndex, columnIndex - 1),
        gridCoordinate(rowIndex, columnIndex + 1),
        gridCoordinate(rowIndex + 1, columnIndex -1),
        gridCoordinate(rowIndex + 1, columnIndex),
        gridCoordinate(rowIndex + 1, columnIndex + 1),
    ).filter { coordinate ->
        coordinate.row in rowIndices && coordinate.column in columnIndices
    }
}

fun <T> Grid<T>.count(predicate: (T) -> Boolean): Int {
    var count = 0
    for (element in this) {
        if (predicate(element)) count++
    }
    return count
}

fun <T> Grid<T>.joinToString(
    columnSeparator: String = "",
    rowSeparator: String = "\n",
    transpose: Boolean = false,
    transform: (T) -> CharSequence = { it.toString() }
): String {
    return if (transpose) {
         columnIndices.joinToString(separator = rowSeparator) { column ->
            rowIndices.map { row -> this[row, column] }
                .joinToString(separator = columnSeparator) {
                    transform(it)
                }
        }
    } else {
         rowIndices.joinToString(separator = rowSeparator) { row ->
            columnIndices.map { column -> this[row, column] }
                .joinToString(separator = columnSeparator) {
                    transform(it)
                }
        }
    }
}