package util

val Int.isEven: Boolean get() = (this % 2) == 0
val Int.isOdd: Boolean get() = (this % 2) == 1