package util

fun <T : Any?> T.part1Result(): T {
    println("Part 1 Result: $this")
    return this
}

fun <T : Any?> T.part2Result(): T {
    println("Part 2 Result: $this")
    return this
}