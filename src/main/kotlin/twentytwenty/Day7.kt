package twentytwenty

import libadvent.resource.asType
import libadvent.resource.load

data class BagColor(val color: String)

data class BagAmount(val amount: Int, val color: BagColor)

data class BagRule(val color: BagColor, val contents: List<BagAmount>)

fun parseBagRule(input: String): BagRule {
    val (bag, contents) = input.split(" contain ", limit = 2)
    val bagColor = BagColor(bag.removeSuffix(" bags"))
    val contentList = if (contents == "no other bags.") {
        listOf()
    } else {
        contents.removeSuffix(".").split(", ").map {
            val (amtString, color) = it.removeSuffix("s").removeSuffix(" bag").split(" ", limit = 2)
            BagAmount(amtString.toInt(), BagColor(color))
        }
    }
    return BagRule(bagColor, contentList)
}

fun whatCanHold(rules: List<BagRule>, color: BagColor): Set<BagColor> {
    val applicableRules = rules.filter { rule ->
        rule.contents.any {
            bagAmount -> bagAmount.color == color
        }
    }
    val currentResults = applicableRules.map { it.color }.toSet()
    return currentResults + currentResults.flatMap { whatCanHold(rules, it) }
}

fun numberOfBagsInside(rules: List<BagRule>, color: BagColor): Int {
    val rule = rules.firstOrNull { it.color == color } ?: return 0
    if (rule.contents.isEmpty()) return 0
    var acc = 0
    for (item in rule.contents) {
        val subContentsPerBag = numberOfBagsInside(rules, item.color)
        val bagIncluded = subContentsPerBag + 1
        val scaled = bagIncluded * item.amount
        acc += scaled
    }
    return acc
}

fun collectColors(rules: List<BagRule>): Set<BagColor> {
    return rules.map { it.color }.toSet()
}

fun main() {
    val myBag = BagColor("shiny gold")
    val rules = load("2020/day7.txt").asType { parseBagRule(it) }
    val part1Solution = whatCanHold(rules, myBag)
    println("Part 1 solution: ${part1Solution.size}")
    val part2Solution = numberOfBagsInside(rules, myBag)
    println("Part 2 solution: $part2Solution")
}