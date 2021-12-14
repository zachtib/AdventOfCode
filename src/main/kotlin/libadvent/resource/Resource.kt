package libadvent.resource

interface Resource {
    val body: String
    val items: List<String>
}

private class LoadedResource(
    override val body: String,
    private val delimiters: Array<out String>,
) : Resource {
    override val items: List<String>
        get() = this.body.split(*delimiters)
            .filter { it.isNotBlank() }
}

fun load(filename: String, vararg delimiters: String = arrayOf("\n")): Resource {
    val url = ResourceLoader.getUrlForFile(filename)
    return LoadedResource(url.readText(), delimiters)
}

fun resourceOf(body: String, vararg delimiters: String = arrayOf("\n")): Resource {
    return LoadedResource(body, delimiters)
}