package util

import java.io.FileNotFoundException
import java.net.URL


object Resources {
    fun load(name: String): URL? = javaClass.classLoader.getResource(name)
}

class Resource(body: String) {

    private val body: String
    private val lines: List<String>

    init {
        this.body = body
        this.lines = this.body.lines()
            .filter { it.isNotBlank() }
    }

    constructor(url: URL) : this(url.readText())

    private fun readLines(): List<String> {
        return lines.filter { it.isNotBlank() }
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

fun resource(getString: () -> String): Resource {
    return Resource(getString())
}