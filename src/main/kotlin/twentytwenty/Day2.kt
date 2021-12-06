package twentytwenty

data class PasswordPolicy(val range: IntRange, val char: Char, val password: String)

fun String.toPolicy(): PasswordPolicy {
    val (policyString, password) = this.split(": ", limit = 2)
    val (minString, maxString, character) = policyString.split('-', ' ', limit = 3)
    val min = minString.toInt()
    val max = maxString.toInt()
    val char = character.first()
    return PasswordPolicy(min..max, char, password)
}

fun PasswordPolicy.isValid(): Boolean {
    return password.filter { it == char }.count() in range
}

fun countValidPasswords(list: List<PasswordPolicy>): Int {
    return list.filter { it.isValid() }.count()
}

fun PasswordPolicy.isValidPart2(): Boolean {
    val char1 = password[range.first - 1]
    val char2 = password[range.last - 1]
    return (char1 != char2 && (char1 == char || char2 == char))
}

fun countValidPasswordsPart2(list: List<PasswordPolicy>): Int {
    return list.filter { it.isValidPart2() }.count()
}

fun main() {
    val input = Resources.load("day2.txt") { it.toPolicy() }
    println("Number of valid passwords: ${countValidPasswords(input)}")
    println("Number of valid passwords (part 2): ${countValidPasswordsPart2(input)}")
}