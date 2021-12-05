package util

import java.io.FileNotFoundException
import java.net.URL


private object ResourceLoader {
    fun load(name: String): URL? = javaClass.classLoader.getResource(name)
}

class Resource(val body: String, private val separator: String = "\n") {

    constructor(url: URL, separator: String = "\n") : this(url.readText(), separator)

    val lines: List<String>
        get() = body.split(separator)
            .filter { it.isNotBlank() }
}

fun <T> Resource.asType(transform: (String) -> T): List<T> = lines.map(transform)

fun Resource.asStrings(): List<String> = lines

fun Resource.asInts(): List<Int> = this.asType { it.toInt() }

fun resource(name: String): Resource {
    val url = ResourceLoader.load(name) ?: throw FileNotFoundException("Requested file, $name, not present in resources")
    return Resource(url)
}

fun resource(getString: () -> String): Resource {
    return Resource(getString())
}

interface ComplexTypeBuilderScope {
    fun <T> takeOne(transform: (String) -> T): T
    fun <T> take(count: Int, transform: (String) -> T): List<T>
    fun <T> takeRemaining(transform: (String) -> T): List<T>
}

private class ComplexTypeBuilder(
    items: List<String>
) : ComplexTypeBuilderScope {

    private var lines = items.filter { it.isNotEmpty() }.toMutableList()

    override fun <T> takeOne(transform: (String) -> T): T {
        return transform(lines.removeFirst())
    }

    override fun <T> take(count: Int, transform: (String) -> T): List<T> {
        return buildList {
            for (i in 0..count) {
                add(transform(lines.removeFirst()))
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