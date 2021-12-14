package twentytwenty

import libadvent.resource.load
import libadvent.part1Result
import libadvent.part2Result

fun tokenize(expression: String): List<Char> {
    val result = mutableListOf<Char>()
    for (c in expression) {
        if (!c.isWhitespace()) {
            result.add(c)
        }
    }
    return result
}

fun applyOperation(oper: Char, a: Long, b: Long): Long {
    return when (oper) {
        '+' -> a + b
        '*' -> a * b
        else -> throw IllegalArgumentException("Valid operations are + and *")
    }
}

// Some extensions to make a list behave like a stack
fun <T> MutableList<T>.push(t: T) = add(t)

fun <T> MutableList<T>.pop(): T = removeLast()

fun <T> MutableList<T>.peek(): T = last()

fun Char.compareTo(other: Char, advanced: Boolean): Boolean {
    return if (advanced) {
        if (other == '(') {
            false
        } else {
            if (this == '*') {
                true
            } else {
                other == '+'
            }
        }
    } else {
        other != '('
    }
}

fun evaluate(expression: String, advanced: Boolean = false): Long {
    val values = mutableListOf<Long>()
    val operators = mutableListOf<Char>()
    for (token in tokenize(expression)) {
        when {
            token.isDigit() -> values.push(token.toString().toLong())
            token == '(' -> operators.push(token)
            token == ')' -> {
                while (operators.peek() != '(') {
                    val op = operators.pop()
                    val v1 = values.pop()
                    val v2 = values.pop()
                    values.push(applyOperation(op, v1, v2))
                }
                operators.pop()
            }
            else -> {
                while (operators.isNotEmpty() && token.compareTo(operators.peek(), advanced)) {
                    val op = operators.pop()
                    val v1 = values.pop()
                    val v2 = values.pop()
                    values.push(applyOperation(op, v1, v2))
                }
                operators.push(token)
            }
        }
    }
    while (operators.isNotEmpty()) {
        val op = operators.pop()
        val v1 = values.pop()
        val v2 = values.pop()
        values.push(applyOperation(op, v1, v2))
    }
    assert(values.size == 1)
    return values.first()
}

fun main() {
    val input = load("2020/day18.txt").items

    input.sumOf { evaluate(it) }.part1Result()
    input.sumOf { evaluate(it, true) }.part2Result()
}