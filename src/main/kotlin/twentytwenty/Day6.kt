package twentytwenty

fun calculateGroupSum(input: String): Int {
    val groups = input.split("\n\n")
    var count = 0
    for (group in groups) {
        val answers = mutableSetOf<Char>()
        val members = group.split('\n')
        for (member in members) {
            for (char in member) {
                answers.add(char)
            }
        }
        count += answers.size
    }
    return count
}

fun calculateGroupSumPart2(input: String): Int {
    val groups = input.split("\n\n")
    var count = 0
    for (group in groups) {
        val members = group.split('\n')
        for (question in 'a'..'z') {
            if (!members.map { question in it }.contains(false)) {
                count++
            }
        }

    }
    return count
}

fun main() {
    val input = Resources.getText("day6.txt") ?: return

    val part1Result = calculateGroupSum(input)
    println("Part 1 result: $part1Result")

    val part2Result = calculateGroupSumPart2(input)
    println("Part 2 result: $part2Result")
}