package util

private fun printError(message: Any) {
    System.err.println(message)
}

private fun <T : Any?> displayResult(num: Int, result: T, prependNewline: Boolean): T {
    if (prependNewline) {
        println("Part $num Result:\n$result")
    } else {
        println("Part $num Result: $result")
    }
    return result
}

private fun <T : Any> displayError(num: Int, e: Throwable): T? {
    val errorMessage = "Encountered an exception during part $num:\n\n${e.stackTraceToString()}"
    printError(errorMessage)
    return null
}

fun <T : Any?> T.part1Result(): T = displayResult(1, this, false)

fun <T : Any?> T.part2Result(): T = displayResult(2, this, false)


private fun <T> performPart(partNumber: Int, calculate: () -> T, prependNewline: Boolean): T? {
    return try {
        displayResult(partNumber, calculate(), prependNewline)
    } catch (e: NotImplementedError) {
        printError("Part $partNumber is not yet implemented")
        null
    } catch (e: Throwable) {
        displayError(partNumber, e)
    }
}

fun <T> part1(prependNewline: Boolean = false, calculate: () -> T): T? = performPart(1, calculate, prependNewline)

fun <T> part2(prependNewline: Boolean = false, calculate: () -> T): T? = performPart(2, calculate, prependNewline)