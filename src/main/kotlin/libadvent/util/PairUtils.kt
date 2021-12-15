package libadvent.util

fun <T> List<T>.toPair(): Pair<T, T> {
    if (size != 2) {
        throw RuntimeException("List.toPair() is only valid for Lists of size 2")
    }
    return this[0] to this[1]
}

fun CharSequence.toPair(): Pair<Char, Char> {
    if (length != 2) {
        throw RuntimeException("CharSequence.toPair() is only valid for CharSequence of length 2")
    }
    return this[0] to this[1]
}

fun Pair<Char, Char>.joined(): String {
    return "$first$second"
}

fun <T> Collection<Pair<T, T>>.itemsPairedWith(item: T): Set<T> {
    return buildSet {
        for ((first, second) in this@itemsPairedWith) {
            if (item == first) {
                add(second)
            } else if (item == second) {
                add(first)
            }
        }
    }
}