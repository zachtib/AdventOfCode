package util

import java.io.FileNotFoundException
import java.net.URL


class ResourceNotFound(filename: String) : FileNotFoundException("Requested resource, $filename, was not present")

private object ResourceLoader {
    fun load(name: String): URL = javaClass.classLoader.getResource(name) ?: throw ResourceNotFound(name)
}

class Resource(val body: String, val delimiters: Array<out String> = arrayOf("\n")) {
    constructor(url: URL, delimiters: Array<out String> = arrayOf("\n")) : this(url.readText(), delimiters)
}

fun Resource.asStrings(): List<String> {
    return this.body.split(*delimiters)
        .filter { it.isNotBlank() }
}

fun <T> Resource.asType(transform: (String) -> T): List<T> {
    return asStrings().map(transform)
}

fun Resource.asInts(): List<Int> = this.asType { it.toInt() }

fun resource(name: String, vararg delimiters: String = arrayOf("\n")): Resource {
    val resourceUrl = ResourceLoader.load(name)
    return Resource(resourceUrl, delimiters)
}

fun resource(vararg delimiters: String = arrayOf("\n"), getString: () -> String): Resource {
    return Resource(getString(), delimiters)
}

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