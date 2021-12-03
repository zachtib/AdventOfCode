package util

import java.io.FileNotFoundException
import java.net.URL


interface Resource {
    val lines: List<String>
}

private object ResourceLoader {
    fun load(name: String): URL? = javaClass.classLoader.getResource(name)
}

private class UrlResource(private val url: URL) : Resource {
    override val lines: List<String>
        get() = url.readText()
            .lines()
            .filter { it.isNotBlank() }
}

private class StringResource(private val body: String) : Resource {
    override val lines: List<String>
        get() = body.lines()
            .filter { it.isNotBlank() }
}

fun <T> Resource.asType(transform: (String) -> T): List<T> = lines.map(transform)

fun Resource.asStrings(): List<String> = lines

fun Resource.asInts(): List<Int> = this.asType { it.toInt() }

fun resource(name: String): Resource {
    val url = ResourceLoader.load(name) ?: throw FileNotFoundException("Requested file, $name, not present in resources")
    return UrlResource(url)
}

fun resource(getString: () -> String): Resource {
    return StringResource(getString())
}
