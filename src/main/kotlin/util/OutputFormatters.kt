package util

private fun printError(message: Any) {
    System.err.println(message)
}

private fun <T : Any?> displayResult(num: Int, result: T): T {
    println("Part $num Result: $result")
    return result
}

private fun <T : Any> displayError(num: Int, e: Throwable): T? {
    val errorMessage = "Encountered an exception during part $num:\n\n${e.stackTraceToString()}"
    printError(errorMessage)
    return null
}

fun <T : Any?> T.part1Result(): T = displayResult(1, this)

fun <T : Any?> T.part2Result(): T = displayResult(2, this)


private fun <T> performPart(partNumber: Int, calculate: () -> T): T? {
    return try {
        displayResult(partNumber, calculate())
    } catch (e: Throwable) {
        displayError(partNumber, e)
    }
}

fun <T> part1(calculate: () -> T): T? = performPart(1, calculate)

fun <T> part2(calculate: () -> T): T? = performPart(2, calculate)