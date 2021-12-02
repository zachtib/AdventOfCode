package util

import java.io.FileNotFoundException
import java.net.URL


object Resources {
    fun load(name: String): URL? = javaClass.classLoader.getResource(name)
}

class Resource(private val url: URL) {

    private fun readLines(): List<String> {
        return url
            .readText()
            .lines()
            .filter { it.isNotBlank() }
    }

    fun <T> asType(transform: (String) -> T): List<T> {
        return readLines().map(transform)
    }

    fun asStrings(): List<String> {
        return readLines()
    }

    fun asInts(): List<Int> {
        return asType { it.toInt() }
    }
}

fun resource(name: String): Resource {
    val url = Resources.load(name) ?: throw FileNotFoundException("Requested file, $name, not present in resources")
    return Resource(url)
}