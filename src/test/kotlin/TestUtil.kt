fun String.asTestData(): List<String> {
    return trimIndent()
        .lines()
        .filter { it.isNotBlank() }
}

fun <T> String.asTestData(transform: (String) -> T): List<T> {
    return asTestData().map(transform)
}

fun String.toInts(): List<Int> {
    return trimIndent()
        .lines()
        .filter { it.isNotBlank() }
        .map { it.toInt() }
}