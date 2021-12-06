package twentytwenty

import util.part1Result
import util.part2Result

data class Rule(
        val name: String,
        val firstRange: IntRange,
        val secondRange: IntRange,
) {

    fun isValueValid(value: Int): Boolean {
        return value in firstRange || value in secondRange
    }

    fun allValuesValid(values: List<Int>): Boolean {
        return values.map { isValueValid(it) }.reduce { a, b -> a && b }
    }

    companion object {
        private val regex = Regex("^(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$")

        fun fromString(text: String): Rule {
            return regex.matchEntire(text)?.let { match ->
                val (name, firstLower, firstUpper, secondLower, secondUpper) = match.destructured
                val firstRange = firstLower.toInt()..firstUpper.toInt()
                val secondRange = secondLower.toInt()..secondUpper.toInt()
                Rule(name, firstRange, secondRange)
            } ?: throw IllegalArgumentException("Input string did not match the Regex")
        }
    }
}

data class ParsedInput(
    val rules: List<Rule>,
    val yourTicket: List<Int>,
    val otherTickets: List<List<Int>>
)

fun parseInput(input: String): ParsedInput {
    val (rules, yourTicket, otherTickets) = input.split(
            "\n\nyour ticket:\n",
            "\n\nnearby tickets:\n",
            limit = 3)
    val formattedRules = rules.split("\n").map { Rule.fromString(it) }
    val yourTicketList = yourTicket.split(",").map { it.toInt() }
    val otherTicketsList = otherTickets.split("\n").map { ticket -> ticket.split(",").map { it.toInt() } }
    return ParsedInput(formattedRules, yourTicketList, otherTicketsList)
}

fun calculateScanningErrorRate(rules: List<Rule>, tickets: List<List<Int>>): Int {
    var acc = 0
    for (field in tickets.flatten()) {
        val anyRuleIsValid = rules.map { rule -> rule.isValueValid(field) }.reduce { a, b -> a || b }
        if (!anyRuleIsValid) {
            acc += field
            continue
        }
    }

    return acc
}

fun isTicketValid(ticket: List<Int>, rules: List<Rule>): Boolean {
    for (field in ticket) {
        val anyRuleIsValid = rules.map { rule -> rule.isValueValid(field) }.reduce { a, b -> a || b }
        if (!anyRuleIsValid) {
            return false
        }
    }
    return true
}

fun solveMyTicket(rules: List<Rule>, myTicket: List<Int>, validTickets: List<List<Int>>): MutableMap<String, Int> {
    val allTickets: List<List<Int>> = validTickets + listOf(myTicket)
    val validities = mutableListOf<MutableList<Rule>>()
    for (fieldIndex in myTicket.indices) {
        val allValues = allTickets.map { it[fieldIndex] }
        val validRules = rules.filter { rule -> rule.allValuesValid(allValues) }
        validities.add(validRules.toMutableList())
    }
    while (validities.any { it.size > 1 }) {
        for (item in validities.filter { it.size == 1 }) {
            validities.filter { it != item }.forEach { it.remove(item.first()) }
        }
    }
    val result = mutableMapOf<String, Int>()
    for ((fieldIndex, validRules) in validities.withIndex()) {
        println("Valid rules for field $fieldIndex are $validRules")
        val rule = validRules.first()
        result[rule.name] = myTicket[fieldIndex]
    }
    return result
}

fun main() {
    val input = parseInput(Resources.getText("day16.txt") ?: "")

    calculateScanningErrorRate(input.rules, input.otherTickets).part1Result()

    val validTickets = input.otherTickets.filter { isTicketValid(it, input.rules) }

    val myTicket = solveMyTicket(input.rules, input.yourTicket, validTickets)

    val filtered = myTicket.filterKeys { it.startsWith("departure") }

    filtered.values.map { it.toLong() }.fold(1L) { a, b -> a * b }.part2Result()
}