package libadvent.util

fun String.toBinaryInt(): Int = toInt(2)

fun String.toBinaryLong(): Long = toLong(2)

fun Int.toBinaryString(length: Int? = null): String {
    val binaryString = toString(2)
    return if (length != null) {
        binaryString.padStart(length, padChar = '0')
    } else {
        binaryString
    }
}

fun Char.hexDigitToInt() = digitToInt(16)

fun Char.hexDigitToBinaryString(length: Int? = null): String {
    return hexDigitToInt().toBinaryString(length)
}

fun String.toHexInt() = toInt(16)

fun String.hexStringToBinary(trimLeadingZeros: Boolean = true): String {
    return toCharArray()
        .mapIndexed { index, c ->
            if (index == 0 && trimLeadingZeros) {
                c.hexDigitToBinaryString()
            } else {
                c.hexDigitToBinaryString(4)
            }
        }
        .joinToString("")
}