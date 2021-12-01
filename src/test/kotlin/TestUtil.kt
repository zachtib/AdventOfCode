fun String.toInts(): List<Int> {
    return trimIndent()
        .lines()
        .map { it.toInt() }
}