package twentytwentyone

import libadvent.grid.Grid
import libadvent.part1
import libadvent.part2
import libadvent.resource.asGrid
import libadvent.resource.load


@JvmInline
value class RiskLevel(val level: Int)

fun day15Part1(riskLevels: Grid<RiskLevel>): Int {
    throw NotImplementedError()
}

fun day15Part2(riskLevels: Grid<RiskLevel>): Int {
    throw NotImplementedError()
}

fun main() {
    val riskLevels = load("2021/day15.txt")
        .asGrid { char -> RiskLevel(char.digitToInt()) }

    part1 { day15Part1(riskLevels) }
    part2 { day15Part2(riskLevels) }
}