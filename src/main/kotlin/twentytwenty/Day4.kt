package twentytwenty

import res.asType
import res.load

data class Passport(
    var byr: String? = null,
    var iyr: String? = null,
    var eyr: String? = null,
    var hgt: String? = null,
    var hcl: String? = null,
    var ecl: String? = null,
    var pid: String? = null,
    var cid: String? = null,
)

val Passport.isValid: Boolean
    get() = when {
        byr == null -> false
        iyr == null -> false
        eyr == null -> false
        hgt == null -> false
        hcl == null -> false
        ecl == null -> false
        pid == null -> false
//        cid == null -> false
        else -> true
    }

val hclRegex = Regex("^#[a-f,0-9]{6}\$")

val rules: List<(Passport.() -> Boolean?)> = listOf(
        { isValid },
        { byr?.let { it.length == 4 && it.toInt() in 1920..2002 } },
        { iyr?.let { it.length == 4 && it.toInt() in 2010..2020 } },
        { eyr?.let { it.length == 4 && it.toInt() in 2020..2030 } },
        {
            hgt?.let { height ->
                when {
                    height.endsWith("in") -> height.removeSuffix("in").toInt() in 59..76
                    height.endsWith("cm") -> height.removeSuffix("cm").toInt() in 150..193
                    else -> false
                }
            }
        },
        { hcl?.let { hclRegex.matches(it) } },
        { ecl in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") },
        { pid?.length == 9 && pid?.toIntOrNull() != null },
        // TODO: Write cid validator
)

fun Passport.validateFields(): Boolean {
    return !rules.map { rule -> this.rule() ?: false }.contains(false)
}

fun parsePassport(string: String) = Passport().apply {
    for (record in string.split(' ', '\n')) {
        val (key, value) = record.split(':', limit = 2)
        when (key) {
            "byr" -> byr = value
            "iyr" -> iyr = value
            "eyr" -> eyr = value
            "hgt" -> hgt = value
            "hcl" -> hcl = value
            "ecl" -> ecl = value
            "pid" -> pid = value
            "cid" -> cid = value
        }
    }
}

fun loadPassports(data: String): List<Passport> {
    return data.split("\n\n").map(::parsePassport)
}

fun main() {
    val passports = load("2020/day4.txt", "\n\n").asType { parsePassport(it) }

    val validCount = passports.count { it.isValid }
    println("Part 1 contains $validCount valid passports")

    val fullValidCount = passports.count { it.validateFields() }
    println("Part 1 contains $fullValidCount valid passports")
}