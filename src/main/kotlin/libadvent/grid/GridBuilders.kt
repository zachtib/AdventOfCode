package libadvent.grid

fun <T : Any> gridOf(vararg arrays: Array<T>): Grid<T> {
    return ArrayGrid(arrays as Array<Array<T>>)
}