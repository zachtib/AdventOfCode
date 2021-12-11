package util

import twentytwentyone.pointsInGridAdjacentTo

inline fun <T, reified R> Grid<T>.map(transform: (T) -> R): Grid<R> {
    return buildGrid(rows, columns) { row, column ->
        transform(this[row, column])
    }
}

fun <T> Grid<T>.forEach(func: (item: T) -> Unit) {
    for (item in this) {
        func(item)
    }
}

fun <T> Grid<T>.forEachIndexed(
    func: (index: Point, item: T) -> Unit,
) = indices.forEach { index ->
    func(index, this[index])
}

fun <T> Grid<T>.filter(predicate: (T) -> Boolean) = iterator {
    for (item in this@filter) {
        if (predicate(item)) {
            yield(item)
        }
    }
}

fun <T> Grid<T>.pointsInGridAdjacentTo(reference: Point): List<Point> {
    val (rowIndex, columnIndex) = reference

    return listOf(
        rowIndex - 1 to columnIndex,
        rowIndex + 1 to columnIndex,
        rowIndex to columnIndex - 1,
        rowIndex to columnIndex + 1
    ).filter { (rowIndex, columnIndex) ->
        rowIndex in rowIndices && columnIndex in columnIndices
    }
}

fun <T> Grid<T>.pointsInGridAdjacentOrDiagonalTo(reference: Point): List<Point> {
    val (rowIndex, columnIndex) = reference
    return listOf(
        rowIndex - 1 to columnIndex - 1,
        rowIndex - 1 to columnIndex,
        rowIndex - 1 to columnIndex + 1,
        rowIndex to columnIndex - 1,
        rowIndex to columnIndex + 1,
        rowIndex + 1 to columnIndex - 1,
        rowIndex + 1 to columnIndex,
        rowIndex + 1 to columnIndex + 1,
    ).filter { (rowIndex, columnIndex) ->
        rowIndex in rowIndices && columnIndex in columnIndices
    }
}