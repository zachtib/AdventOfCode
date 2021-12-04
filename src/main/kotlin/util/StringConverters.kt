package util


fun String.toListOfInts(separator: String = ","): List<Int> {
    return split(separator).map { it.toInt() }
}