package libadvent.grid


fun <A, B> areGridsContentsEqual(first: Grid<A>, second: Grid<B>): Boolean {
    if (first.rowIndices != second.rowIndices || first.columnIndices != second.columnIndices) {
        return false
    }
    for (row in first.rowIndices) {
        for (column in first.columnIndices) {
            if (first[row, column] != second[row, column]) {
                return false
            }
        }
    }
    return true
}