package util

import java.io.FileNotFoundException
import java.net.URL


interface Resource {
    val lines: List<String>
}

private object ResourceLoader {
    fun load(name: String): URL? = javaClass.classLoader.getResource(name)
}

private class StringResource(private val body: String) : Resource {

    constructor(url: URL) : this(url.readText())

    override val lines: List<String>
        get() = body.lines()
            .filter { it.isNotBlank() }
}

fun <T> Resource.asType(transform: (String) -> T): List<T> = lines.map(transform)

fun Resource.asStrings(): List<String> = lines

fun Resource.asInts(): List<Int> = this.asType { it.toInt() }

fun resource(name: String): Resource {
    val url = ResourceLoader.load(name) ?: throw FileNotFoundException("Requested file, $name, not present in resources")
    return StringResource(url)
}

fun resource(getString: () -> String): Resource {
    return StringResource(getString())
}
