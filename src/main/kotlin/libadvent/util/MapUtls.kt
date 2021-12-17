package libadvent.util

fun  <K, V> Map<out K, V?>.filterValuesNotNull(): Map<K, V> {
    val result = LinkedHashMap<K, V>()
    for (entry in this) {
        entry.value?.let { value ->
            result.put(entry.key, value)
        }
    }
    return result
}