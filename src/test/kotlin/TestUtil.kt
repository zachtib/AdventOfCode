fun String.toInts(): List<Int> {
    return trimIndent()
        .lines()
        .filter { it.isNotBlank() }
        .map { it.toInt() }
}