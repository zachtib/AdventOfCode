package twentytwentyone

import libadvent.algorithms.solveDijkstraForGrid
import libadvent.grid.Grid
import libadvent.part1
import libadvent.part2
import libadvent.resource.asGrid
import libadvent.resource.load

class ScaledGrid(
    private val subGrid: Grid<Int>,
    private val scale: Int
) : Grid<Int> by subGrid {

    override val rows: Int get() = subGrid.rows * scale
    override val columns: Int get() = subGrid.rows * scale

    override fun get(row: Int, column: Int): Int {

        val rowOffset = row / subGrid.rows
        val translatedRow = row % subGrid.rows

        val columnOffset = column / subGrid.columns
        val translatedColumn = column % subGrid.columns

        val originalValue = subGrid[translatedRow, translatedColumn]

        var newValue = originalValue + rowOffset + columnOffset

        while (newValue > 9) {
            newValue -= 9
        }

        return newValue
    }
}

fun day15Part1(riskLevels: Grid<Int>): Int {
    return solveDijkstraForGrid(riskLevels)
}

fun day15Part2(riskLevels: Grid<Int>): Int {
    val scaledGrid = ScaledGrid(riskLevels, 5)
    return solveDijkstraForGrid(scaledGrid)
}

fun main() {
    val riskLevels = load("2021/day15.txt")
        .asGrid { char -> char.digitToInt() }

    part1 { day15Part1(riskLevels) }
    part2 { day15Part2(riskLevels) }
}