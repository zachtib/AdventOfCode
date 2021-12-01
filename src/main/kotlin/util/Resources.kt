package util


object Resources {
    fun loadFile(name: String): String = javaClass.classLoader.getResource(name)?.readText() ?: ""
}

fun <T> String.load(transform: (String) -> T): List<T> {
    return Resources.loadFile(this)
        .lines()
        .map(transform)
}

fun String.loadInts(): List<Int> {
    return load { it.toInt() }
}