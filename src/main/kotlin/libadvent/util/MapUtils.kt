package libadvent.util


fun <K, V : Any> MutableMap<K, V>.update(key: K, default: V, transform: (V) -> V): V? {
    val previousValue = getOrDefault(key, default)
    val updatedValue = transform(previousValue)

    return this.put(key, updatedValue)
}

fun <K, V : Any> MutableMap<K, V>.update(key: K, transform: (V) -> V): V? {
    val previousValue = this[key] ?: return null
    val updatedValue = transform(previousValue)

    return this.put(key, updatedValue)
}

fun <K> MutableMap<K, Int>.increment(key: K, amount: Int = 1) {
    val previousValue = getOrDefault(key, 0)
    put(key, previousValue + amount)
}

fun <K> MutableMap<K, Long>.increment(key: K, amount: Long = 1) {
    val previousValue = getOrDefault(key, 0L)
    put(key, previousValue + amount)
}
