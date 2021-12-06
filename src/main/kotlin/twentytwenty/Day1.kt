package twentytwenty

import res.asInts
import res.load
import kotlin.Triple

fun List<Int>.getPairsSummingTo(sum: Int): List<Pair<Int, Int>> {
    val results = mutableListOf<Pair<Int, Int>>()
    for (i in this.indices) {
        for (j in i + 1 until size) {
            if (this[i] + this[j] == sum) {
                results.add(this[i] to this[j])
            }
        }
    }
    return results
}

fun List<Int>.getTriplesSummingTo(sum: Int): List<Triple<Int, Int, Int>> {
    val results = mutableListOf<Triple<Int, Int, Int>>()
    for (i in this.indices) {
        for (j in i + 1 until size) {
            for (k in j + 1 until size) {
                if (this[i] + this[j] + this[k] == sum) {
                    results.add(Triple(this[i], this[j], this[k]))
                }
            }
        }
    }
    return results
}

fun Triple<Int, Int, Int>.product() = first * second * third

fun getPairsProduct(input: List<Int>): Int {
    val pairs = input.getPairsSummingTo(2020)
    // Assuming only one potential result
    val pair = pairs.firstOrNull() ?: return 0
    println("${pair.first} * ${pair.second} = ${pair.first * pair.second}")
    return pair.first * pair.second
}

fun getTriplesProduct(input: List<Int>): Int {
    val triples = input.getTriplesSummingTo(2020)
    // Assuming only one potential result
    val triple = triples.firstOrNull() ?: return 0
    println("${triple.first} * ${triple.second} * ${triple.third} = ${triple.product()}")
    return triple.product()
}

fun main() {
    val input = load("2020/day1.txt").asInts()
    println("Part 1 result: ${getPairsProduct(input)}")
    println("Part 2 result: ${getTriplesProduct(input)}")
}