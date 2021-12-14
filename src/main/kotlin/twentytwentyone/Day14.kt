package twentytwentyone

import libadvent.util.toPair
import res.Resource
import res.asComplexType
import res.load
import util.EmptyCollectionException
import util.part1
import util.part2


@JvmInline
value class PolymerTemplate(val template: String)

data class PairInsertion(val pair: Pair<Char, Char>, val insertion: Char) {
    companion object {
        fun fromString(inputString: String): PairInsertion {
            val (inputs, output) = inputString.split(" -> ", limit = 2)
            return PairInsertion(
                pair = inputs.toList().toPair(),
                insertion = output.first()
            )
        }
    }
}

fun Resource.asPolymerInputs() = asComplexType {
    takeOne {
        PolymerTemplate(it)
    } to takeLines {
        PairInsertion.fromString(it)
    }
}

fun PolymerTemplate.applyPairInsertions(insertions: List<PairInsertion>): PolymerTemplate {
    val elements = template.toMutableList()
    var indexOffset = 1

    for ((originalPairIndex, window) in elements.windowed(2).withIndex()) {
        val pair = window.toPair()

        for (rule in insertions.filter { it.pair == pair }) {
            val newIndex = originalPairIndex + indexOffset
            elements.add(newIndex, rule.insertion)
            indexOffset++
        }
    }
    return PolymerTemplate(elements.joinToString(""))
}

fun PolymerTemplate.getElementCounts(): Map<Char, Int> {
    val uniqueElements = this.template.toSet()
    return uniqueElements.associateWith { element ->  template.count { it == element  } }
}

fun day14Part1(template: PolymerTemplate, insertions: List<PairInsertion>): Int {
    var polymer = template
    repeat(10) {
        polymer = polymer.applyPairInsertions(insertions)
    }

    val elementCounts = polymer.getElementCounts()
    val minimum = elementCounts.minOfOrNull { entry -> entry.value } ?: throw EmptyCollectionException()
    val maximum = elementCounts.maxOfOrNull { entry -> entry.value } ?: throw EmptyCollectionException()

    return maximum - minimum
}

fun day14Part2(): Int {
    throw NotImplementedError()
}

fun main() {
    val (template, insertions) = load("2021/day14.txt").asPolymerInputs()
    part1 { day14Part1(template, insertions) }
    part2 { day14Part2() }
}