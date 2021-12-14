package twentytwentyone

import libadvent.resource.asType
import libadvent.resource.load
import libadvent.part1Result
import libadvent.part2Result

data class LanternFish(var daysRemaining: Int = DAYS_FOR_INITIAL_CYCLE) {
    companion object {
        const val DAYS_FOR_INITIAL_CYCLE = 8
        const val DAYS_WHEN_CYCLE_RESETS = 6
    }

    fun onDayPass(): LanternFish? {
        return if (daysRemaining > 0) {
            daysRemaining--
            null
        } else {
            daysRemaining = DAYS_WHEN_CYCLE_RESETS
            LanternFish()
        }
    }
}

interface School {
    fun onDayPass()

    fun runForDays(days: Int, printStatus: Boolean = false) {
        if (printStatus) {
            println("Initial state: $this")
        }

        val padLength = days.toString().length

        for (day in 1..days) {
            onDayPass()

            if (printStatus) {
                val dayString = "$day".padStart(padLength)
                println("After $dayString days: $this")
            }
        }
    }
}

class LanternFishSchool(initialFish: List<LanternFish>) : School {
    private var fish = initialFish.map { it.copy() }.toMutableList()

    override fun onDayPass() {
        val newFish: List<LanternFish> = fish.mapNotNull { fish -> fish.onDayPass() }
        fish.addAll(newFish)
    }

    fun count(): Int = fish.size

    override fun toString(): String {
        return fish.joinToString(",") { "${it.daysRemaining}" }
    }
}


class LargeLanternFishSchool(initialFish: List<LanternFish>) : School {

    private fun LongArray.shiftLeftAndReturn(): Long {
        val ret: Long = this.first()
        for ((i1, i2) in indices.windowed(2)) {
            this[i1] = this[i2]
        }
        this[indices.last] = 0L
        return ret
    }

    private val fishCounts = LongArray(9) { i -> initialFish.count { it.daysRemaining == i }.toLong() }

    private var previousCount = 0L

    override fun onDayPass() {
        val fishFinishingCycle = fishCounts.shiftLeftAndReturn()
        fishCounts[LanternFish.DAYS_WHEN_CYCLE_RESETS] += fishFinishingCycle
        fishCounts[LanternFish.DAYS_FOR_INITIAL_CYCLE] += fishFinishingCycle

        val count = this.count()
        if (count < previousCount) {
            throw RuntimeException("Count decreased, this shouldn't happen: $count, $previousCount")
        }
        previousCount = count
    }

    fun count(): Long {
        return fishCounts.sum()
    }

    override fun toString(): String {
        val padSize = 14

        val paddedCount =  count().toString().padStart(padSize)

        return fishCounts
            .mapIndexed { index, count -> "$index: ${count.toString().padStart(padSize)}" }
            .joinToString(", ") + " count: $paddedCount"
    }
}

fun day6Part1(input: List<LanternFish>, runForDays: Int = 80, printStatus: Boolean = false): Int {
    val school = LanternFishSchool(input)
    school.runForDays(runForDays, printStatus)
    return school.count()
}

fun day6Part2(input: List<LanternFish>): Long {
    val school = LargeLanternFishSchool(input)
    school.runForDays(256)
    return school.count()
}

fun main() {
    val input = load("2021/day6.txt", ",").asType { LanternFish(it.toInt()) }

    day6Part1(input).part1Result()
    day6Part2(input).part2Result()
}