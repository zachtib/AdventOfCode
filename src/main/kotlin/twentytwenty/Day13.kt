package twentytwenty

import res.load

sealed class Bus {
    data class InService(val id: Int) : Bus()
    object OutOfService : Bus()
}

fun parseInputAssumingOutOfServiceBuses(input: String): Pair<Int, List<Bus>> {
    val (line1, line2) = input.split('\n', limit = 2)
    val timeStamp = line1.toInt()
    val buses = parseBuses(line2)
    return timeStamp to buses
}

fun parseBuses(input: String) = input.split(',').map {
    when (val id = it.toIntOrNull()) {
        null -> Bus.OutOfService
        else -> Bus.InService(id)
    }
}

fun findEarliestBus(timestamp: Int, buses: List<Bus>): Bus.InService {
    return buses.filterIsInstance<Bus.InService>()
            .minByOrNull { it.id - timestamp % it.id }
            ?: throw IllegalArgumentException("No valid buses")
}

fun findEarliestBusTimeWait(timestamp: Int, buses: List<Bus>): Int {
    val bus = findEarliestBus(timestamp, buses)
    return bus.id * (bus.id - timestamp % bus.id)
}

data class IndexedBus(val index: Int, val busId: Int)

fun indexInServiceBuses(buses: List<Bus>): List<IndexedBus> {
    val result = mutableListOf<IndexedBus>()
    for ((index, bus) in buses.withIndex()) {
        if (bus is Bus.InService) {
            result.add(IndexedBus(index, bus.id))
        }
    }
    return result
}

fun euclid(a: Long, b: Long): Pair<Long, Long> {
    var previousRemainder = a
    var remainder = b
    var prevS = 1L
    var s = 0L
    var prevT = 0L
    var t = 1L

    while (remainder != 0L) {
        val quotient = previousRemainder / remainder

        val newRemainder = previousRemainder - quotient * remainder
        previousRemainder = remainder
        remainder = newRemainder

        val newS = prevS - quotient * s
        prevS = s
        s = newS

        val newT = prevT - quotient * t
        prevT = t
        t = newT
    }
    return prevS to prevT
}

fun findTimeStampForBuses(buses: List<Bus>): Long {
    val indexedBuses = indexInServiceBuses(buses)
    val product = indexedBuses.fold(1L) { acc, i -> acc * i.busId }

    val ys = indexedBuses.map { product / it.busId }
    val idents = ys.mapIndexed { i, y -> euclid(indexedBuses[i].busId.toLong(), y) }
    val toSum = idents.mapIndexed { i, pair -> pair.second * ys[i] * -indexedBuses[i].index }
    var result = toSum.sum()
    while (result < 0) {
        result += product
    }
    while (result > product) {
        result -= product
    }
    return result
}

fun main() {
    val input = load("2020/day13.txt").body
    val (timestamp, buses) = parseInputAssumingOutOfServiceBuses(input)

    println("Part 1 Result: ${findEarliestBusTimeWait(timestamp, buses)}")
    println("Part 2 Result: ${findTimeStampForBuses(buses)}")
}