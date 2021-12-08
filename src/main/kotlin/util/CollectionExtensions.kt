package util

fun <T : Any> Collection<T>.requireOnlyOne(): T = if (size == 1) {
    first()
} else {
    throw IllegalStateException("Collect had more than one element: $this")
}

fun <T, C : Collection<T>> C.requireSize(requiredSize: Int): C {
    if (size != requiredSize) {
        throw IllegalStateException("This must contain $requiredSize elements but it actually contains $size")
    }
    return this
}