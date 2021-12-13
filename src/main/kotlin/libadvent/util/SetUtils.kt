package libadvent.util

infix fun <T> Set<T>.isSubsetOf(other: Set<T>): Boolean {
    // Every element in *this* is present in *other*
    return other.containsAll(this)
}

infix fun <T> Set<T>.isSupersetOf(other: Set<T>): Boolean {
    // Every element in *other* is present in *this*
    return this.containsAll(other)
}
infix fun <T> Set<T>.isNotSubsetOf(other: Set<T>): Boolean {
    // Not every element in *this* is present in *other*
    return !other.containsAll(this)
}

infix fun <T> Set<T>.isNotSupersetOf(other: Set<T>): Boolean {
    // Not every element in *other* is present in *this*
    return !this.containsAll(other)
}