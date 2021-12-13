package libadvent.util


fun String.toListOfInts(separator: String = ","): List<Int> {
    return split(separator)
        .filter { it.isNotBlank() }
        .map { it.toInt() }
}