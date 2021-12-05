import util.*

data class BingoBoardsWithInputs(
    val inputs: List<Int>,
    val boards: List<BingoBoard>,
)

data class BingoSquare(val value: Int, var isMarked: Boolean = false)

class BingoBoard(boardString: String) {
    private val boardState: List<List<BingoSquare>>

    init {
        val lines = boardString.lines()
        boardState = buildList {
            for (line in lines) {
                val tokens = line.split(" ").filter { it.isNotBlank() }
                add(tokens.map { BingoSquare(it.toInt()) })
            }
        }
    }

    private val rowCount: Int
        get() = boardState.size

    private val columnCount: Int
        get() = boardState.firstOrNull()?.size ?: 0

    private val rows: List<List<BingoSquare>>
        get() = boardState

    private val columns: List<List<BingoSquare>>
        get() = (0 until columnCount).map { columnIndex -> boardState.map { it[columnIndex] } }

    private fun squareAtPosition(row: Int, column: Int): BingoSquare {
        return boardState[row][column]
    }

    fun markSquareContaining(value: Int) {
        for (row in 0 until rowCount) {
            for (col in 0 until columnCount) {
                val square = squareAtPosition(row, col)
                if (square.value == value) {
                    square.isMarked = true
                }
            }
        }
    }

    val isComplete: Boolean
        get() {
            return listOf(rows, columns).any { collections ->
                collections.any { squares ->
                    squares.all { it.isMarked }
                }
            }
        }

    fun sumOfUnmarkedValues(): Int {
        return boardState.flatten()
            .filter { !it.isMarked }
            .map { it.value }
            .sum()
    }
}

fun String.toBingoBoard(): BingoBoard = BingoBoard(this)

fun day4Part1(input: BingoBoardsWithInputs): Int {
    for (number in input.inputs) {
        input.boards.forEach { board -> board.markSquareContaining(number) }
        val winningBoards = input.boards.filter { it.isComplete }
        if (winningBoards.any()) {
            val board = winningBoards.first()
            return board.sumOfUnmarkedValues() * number
        }
    }
    return -1
}

fun day4Part2(input: Any): Int {
    return -1
}

fun main() {
    val input = resource("day4.txt").asComplexType {
        BingoBoardsWithInputs(
            inputs = takeOne { it.toListOfInts() },
            boards = takeRemaining { it.toBingoBoard() },
        )
    }
    day4Part1(input.copy()).part1Result()
    day4Part2(input.copy()).part2Result()
}