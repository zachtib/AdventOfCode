package libadvent

fun Int?.asDigitInRange(range: IntRange): String {
    if (this == null || this !in range) {
        return "."
    }
    if (this == range.last) {
        return "#"
    }

    val rangeStep = (range.last - range.first) / 10
    val delta = this - range.first
    val m = delta / rangeStep

    return try {
        m.digitToChar().toString()
    } catch (e: Exception) {
        println("Error placing $this in $range")
        "E"
    }
}