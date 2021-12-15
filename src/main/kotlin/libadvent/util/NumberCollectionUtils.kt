package libadvent.util

import libadvent.EmptyCollectionException

fun Collection<Int>.minimum(): Int {
    return this.minOrNull() ?: throw EmptyCollectionException()
}

fun Collection<Int>.maximum(): Int {
    return this.maxOrNull() ?: throw EmptyCollectionException()
}