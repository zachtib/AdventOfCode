package twentytwenty

import res.asStrings
import res.load
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException


data class Slope(val right: Int, val down: Int)

class TreeField(private val rows: List<String>) {

    /**
     *  Checks the square at position ([row], [column])
     *  @return `true` if there is a tree at that position
     */
    fun checkPosition(row: Int, column: Int): Boolean {
        if (row >= rows.size) throw IllegalArgumentException("[row] cannot be greater than the input size")
        val selectedRow = rows[row]
        val modColumn = column % selectedRow.length
        return when (selectedRow[modColumn]) {
            '#' -> true
            '.' -> false
            else -> throw IllegalStateException("Only # and . characters are valid input")
        }
    }

    /**
     *  Solution for part 1, traverses the field by [slope] and counts the trees
     *  @return the number of trees
     */
    fun traverse(slope: Slope): Int {
        var row = 0
        var column = 0
        var trees = 0

        while (row < rows.size) {
            if (checkPosition(row, column)) {
                trees++
            }
            row += slope.down
            column += slope.right
        }
        return trees
    }

    /**
     *  runs [traverse] for each aoc2020.Slope in [slopes] and multiplies the results
     *  @return the aoc2020.product of all traversals
     */
    fun multiTraverse(slopes: List<Slope>): Int {
        return slopes.map { slope -> traverse(slope) }.reduce { acc, i -> acc * i }
    }
}


fun main() {
    val field = TreeField(load("2020/day3.txt").asStrings())
    println("Part 1: ${field.traverse(Slope(3, 1))}")

    val part2result = field.multiTraverse(listOf(
            Slope(right = 1, down = 1),
            Slope(right = 3, down = 1),
            Slope(right = 5, down = 1),
            Slope(right = 7, down = 1),
            Slope(right = 1, down = 2),
    ))
    println("Part 2: $part2result")
}