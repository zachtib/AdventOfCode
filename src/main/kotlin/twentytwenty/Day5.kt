package twentytwenty

import res.asType
import res.load

fun IntRange.takeFront(): IntRange {
    val pivot: Int = (last - first) / 2 + first
    return first..pivot
}

fun IntRange.takeBack(): IntRange {
    val pivot: Int = (last - first) / 2 + 1 + first
    return pivot..last
}

fun partitionRange(initialRange: IntRange, input: List<Boolean>): Int {
    var range = initialRange
    for (instruction in input) {
        range = if (instruction) {
            range.takeFront()
        } else {
            range.takeBack()
        }
    }
    if (range.first != range.last) {
        throw IllegalStateException("Input sizes did not match")
    }
    return range.first
}

fun calculateRow(range: IntRange, input: CharSequence): Int {
    return partitionRange(range, input.map { it == 'F' })
}

fun calculateColumn(range: IntRange, input: CharSequence): Int {
    return partitionRange(range, input.map { it == 'L' })
}

fun calculateSeat(ticket: CharSequence): Int {
    val row = calculateRow(0..127, ticket.subSequence(0..6))
    val column = calculateColumn(0..7, ticket.subSequence(7..9))
    return row * 8 + column
}


fun main() {
    val seats = load("2020/day5.txt").asType { calculateSeat(it) }
    val maxSeatId = seats.maxOrNull() ?: throw IllegalStateException("List was empty!")
    println("Max seat id is $maxSeatId")

    val allSeats = seats.sorted()
    for (window in allSeats.windowed(size = 2, step = 1)) {
        if (window[1] == window[0] + 2) {
            println("There is a missing seat between ${window[0]} and ${window[1]}")
        }
    }
}
