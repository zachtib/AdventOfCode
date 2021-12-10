package util

class Stack<T> private constructor(private val values: MutableList<T>): Collection<T> {

    constructor(initialItems: Collection<T> = emptyList()) : this(initialItems.toMutableList())

    fun push(item: T) = values.add(item)

    fun pop(): T? = values.removeLastOrNull()

    fun peek(): T? = values.lastOrNull()

    override fun iterator(): Iterator<T> = iterator {
        var next = pop()
        while(next != null) {
            yield(next)
            next = pop()
        }
    }

    override val size: Int get() = values.size

    override fun contains(element: T): Boolean = values.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = values.containsAll(elements)

    override fun isEmpty(): Boolean = values.isEmpty()
}

fun <T> emptyStack(): Stack<T> = Stack()