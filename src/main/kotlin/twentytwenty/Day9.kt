package twentytwenty

class SlidingList<T>(
        private val maxSize: Int,
        private val list: MutableList<T> = mutableListOf()
) : List<T> by list {

    fun add(element: T) {
        list.add(element)
        while (list.size > maxSize) {
            list.removeFirst()
        }
    }

    fun addAll(elements: Collection<T>) {
        list.addAll(elements)
        while (list.size > maxSize) {
            list.removeFirst()
        }
    }
}

fun <T> Iterator<T>.take(size: Int): List<T> {
    var index = 0
    val result = mutableListOf<T>()
    while (index < size && hasNext()) {
        result.add(next())
        index++
    }
    return result
}

fun <T> List<T>.anyTwo(condition: (T, T) -> Boolean): Boolean {
    for (i in indices) {
        for (j in i + 1 until size) {
            if (condition(this[i], this[j])) {
                return true
            }
        }
    }
    return false
}

fun <T> List<T>.iterateRangesConditionally(
        skip: (List<T>) -> Boolean
): Sequence<List<T>> = sequence {
    for (i in indices) {
        var offset = 0
        var subList = subList(i, i + offset)
        while (!skip(subList)) {
            yield(subList)
            offset++
            subList = subList(i, i + offset)
        }
    }
}

class XmasDecoder(private val preambleLength: Int, private val input: List<Long>) {

    fun findFirstInvalidElement(): Long? {
        val iterator = input.iterator()
        val preamble = SlidingList<Long>(preambleLength)
        preamble.addAll(iterator.take(preambleLength))

        for (element in iterator) {
            if (!preamble.anyTwo { a, b -> a + b == element }) {
                return element
            }
            preamble.add(element)
        }
        return null
    }

    fun findRangeForInvalidElement(): Long? {
        val element = findFirstInvalidElement() ?: return null
        for (range in input.iterateRangesConditionally { it.sum() > element }) {
            if (range.sum() == element) {
                val min = range.minOrNull() ?: return null
                val max = range.maxOrNull() ?: return null
                return min + max
            }
        }
        return null
    }
}

fun main() {
    val input = Resources.getLongs("day9.txt")
    val decoder = XmasDecoder(25, input)

    val part1Result = decoder.findFirstInvalidElement()
    println("Part 1 result: $part1Result")
    val part2Result = decoder.findRangeForInvalidElement()
    println("Part 2 result: $part2Result")
}