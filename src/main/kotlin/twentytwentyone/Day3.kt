package twentytwentyone

import libadvent.EmptyCollectionException
import libadvent.part1Result
import libadvent.part2Result
import libadvent.util.toBinaryInt
import libadvent.resource.asStrings
import libadvent.resource.load


fun <T> List<T>.getElementCounts(): Map<T, Int> = buildMap {
    for (element in this@getElementCounts) {
        val elementCount = this.getOrDefault(element, 0) + 1
        this[element] = elementCount
    }
}

fun <T> List<T>.mostCommonElement(defaultInCaseOfTie: T? = null): T {
    val counts = getElementCounts()
    val selectedMaximum = counts.maxByOrNull { it.value } ?: throw EmptyCollectionException()
    if (defaultInCaseOfTie != null && counts.filter { it.value == selectedMaximum.value }.count() > 1) {
        return defaultInCaseOfTie
    }
    return selectedMaximum.key
}

fun <T> List<T>.leastCommonElement(defaultInCaseOfTie: T? = null): T {
    val counts = getElementCounts()
    val selectedMinimum = counts.minByOrNull { it.value } ?: throw EmptyCollectionException()
    if (defaultInCaseOfTie != null && counts.filter { it.value == selectedMinimum.value }.count() > 1) {
        return defaultInCaseOfTie
    }
    return selectedMinimum.key
}

fun day3Part1(input: List<String>): Int {
    val element = input.firstOrNull() ?: throw EmptyCollectionException()

    val gammaCharacters = mutableListOf<Char>()
    val epsilonCharacters = mutableListOf<Char>()

    for (index in element.indices) {
        val characters = input.map { string -> string[index] }
        val mostCommon = characters.mostCommonElement()
        val leastCommon = characters.leastCommonElement()

        gammaCharacters.add(mostCommon)
        epsilonCharacters.add(leastCommon)
    }

    val gammaString = gammaCharacters.joinToString("")
    val epsilonString = epsilonCharacters.joinToString("")

    return gammaString.toBinaryInt() * epsilonString.toBinaryInt()
}

fun processStringsByCriteria(values: List<String>, bitIndex: Int = 0, selector: (List<Char>) -> Char): String {
    val consideredBits = values.map { it[bitIndex] }
    val selectedBit = selector(consideredBits)
    val filteredValues = values.filter { it[bitIndex] == selectedBit }
    if (filteredValues.count() == 1) {
        return filteredValues.first()
    }
    return processStringsByCriteria(filteredValues, bitIndex + 1, selector)
}

fun day3Part2(input: List<String>): Int {
    val oxygenRating = processStringsByCriteria(input) { characters ->
        characters.mostCommonElement(defaultInCaseOfTie = '1')
    }.toBinaryInt()

    val co2Rating = processStringsByCriteria(input) { characters ->
        characters.leastCommonElement(defaultInCaseOfTie = '0')
    }.toBinaryInt()

    return oxygenRating * co2Rating
}

fun main() {
    val input = load("2021/day3.txt").asStrings()
    day3Part1(input).part1Result()
    day3Part2(input).part2Result()
}
