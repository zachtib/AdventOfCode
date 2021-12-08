package res

interface ComplexTypeBuilderScope {
    fun <T> takeOne(transform: (String) -> T): T
    fun <T> take(count: Int, transform: (String) -> T): List<T>
    fun <T> takeRemaining(transform: (String) -> T): List<T>
}

private class ComplexTypeBuilder(
    items: List<String>,
) : ComplexTypeBuilderScope {

    private var lines = items.filter { it.isNotEmpty() }.toMutableList()

    override fun <T> takeOne(transform: (String) -> T): T {
        return transform(lines.removeFirst().trim())
    }

    override fun <T> take(count: Int, transform: (String) -> T): List<T> {
        return buildList {
            for (i in 0..count) {
                add(takeOne(transform))
            }
        }
    }

    override fun <T> takeRemaining(transform: (String) -> T): List<T> {
        val result = lines.map(transform)
        lines.clear()
        return result
    }
}

fun <R> Resource.asComplexType(
    separator: String = "\n\n",
    block: ComplexTypeBuilderScope.() -> R,
): R {
    val lines = body.split(separator).filter { it.isNotBlank() }
    val builder = ComplexTypeBuilder(lines)

    return builder.block()
}