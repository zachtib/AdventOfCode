package twentytwenty

import libadvent.resource.asType
import libadvent.resource.load
import libadvent.part1Result
import libadvent.part2Result
import kotlin.math.pow

sealed class BitmaskInstruction {
    data class UpdateBitmask(val bitmask: String) : BitmaskInstruction()
    data class WriteValue(val address: Long, val value: Long) : BitmaskInstruction()

    companion object {
        fun fromString(input: String): BitmaskInstruction {
            return when {
                input.startsWith("mask") -> {
                    UpdateBitmask(input.removePrefix("mask = "))
                }
                input.startsWith("mem") -> {
                    val (memoryInstruction, value) = input.split(" = ", limit = 2)
                    val memoryAddress = memoryInstruction.removeSurrounding("mem[", "]")
                    WriteValue(memoryAddress.toLong(), value.toLong())
                }
                else -> throw IllegalArgumentException("Badly formatted input: \"$input\"")
            }
        }
    }
}

class BitEncoder {
    fun toByteString(value: Long): String {
        require(value >= 0) { "Only unsigned values accepted" }
        return value.toString(2).padStart(36, '0')
    }

    fun toLong(value: String): Long {
        return value.toLong(2)
    }
}

class BitMasker(initialMask: String = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX") {

    init {
        require(initialMask.length == 36) {
            "Bitmask length must be 36 characters"
        }
    }

    private val encoder: BitEncoder = BitEncoder()
    var bitmask: String = initialMask
        set(value) {
            require(value.length == 36) {
                "Bitmask length must be 36 characters"
            }
            field = value
        }

    fun mask(value: Long): Long {
        val array = encoder.toByteString(value).toCharArray()
        for (i in bitmask.indices) {
            when (bitmask[i]) {
                '0', '1' -> array[i] = bitmask[i]
            }
        }
        return encoder.toLong(array.joinToString(""))
    }

    fun maskAddress(value: Long): String {
        val array = encoder.toByteString(value).toCharArray()
        for (i in bitmask.indices) {
            when (bitmask[i]) {
                '1', 'X' -> array[i] = bitmask[i]
            }
        }
        return array.joinToString("")
    }
}


class BitmaskComputer {
    private val masker = BitMasker()
    private val memory = mutableMapOf<Long, Long>()

    fun execute(instruction: BitmaskInstruction) = when (instruction) {
        is BitmaskInstruction.UpdateBitmask -> {
            masker.bitmask = instruction.bitmask
        }
        is BitmaskInstruction.WriteValue -> {
            memory[instruction.address] = masker.mask(instruction.value)
        }
    }

    fun execute(program: List<BitmaskInstruction>) {
        for (instruction in program) {
            execute(instruction)
        }
    }

    fun sumMemoryValues(): Long {
        return memory.values.sum()
    }
}

class Biterator {

    fun biterateLongs(input: String): Iterable<Long> {
        val floatingIndices = mutableListOf<Int>()
        for (i in input.indices) {
            if (input[i] == 'X') {
                floatingIndices.add(i)
            }
        }
        val ceil = 2.toDouble().pow(floatingIndices.size).toInt()
        val results = mutableListOf<Long>()
        for (c in 0 until ceil) {
            val bString = c.toString(2).padStart(length = floatingIndices.size, '0')
            val cArray = input.toCharArray()
            for (i in bString.indices) {
                cArray[floatingIndices[i]] = bString[i]
            }
            results.add(cArray.joinToString("").toLong(2))
        }
        return results
    }
}

class FloatingMemory {
    private val biterator = Biterator()
    private val internalMemory = mutableMapOf<Long, Long>()

    operator fun set(floatingAddress: String, value: Long) {
        for (address in biterator.biterateLongs(floatingAddress)) {
            internalMemory[address] = value
        }
    }

    operator fun get(address: Long): Long? = internalMemory[address]

    fun values(): List<Long> = internalMemory.values.toList()
}

class BitmaskComputerV2 {
    private val masker = BitMasker()
    private val memory = FloatingMemory()

    fun execute(instruction: BitmaskInstruction) = when (instruction) {
        is BitmaskInstruction.UpdateBitmask -> {
            masker.bitmask = instruction.bitmask
        }
        is BitmaskInstruction.WriteValue -> {
            memory[masker.maskAddress(instruction.address)] = instruction.value
        }
    }

    fun execute(program: List<BitmaskInstruction>) {
        for (instruction in program) {
            execute(instruction)
        }
    }

    fun sumMemoryValues(): Long {
        return memory.values().sum()
    }

}
fun main() {
    val program = load("2020/day14.txt").asType { BitmaskInstruction.fromString(it) }
    val computer = BitmaskComputer()
    computer.execute(program)

    computer.sumMemoryValues().part1Result()

    val v2computer = BitmaskComputerV2()
    v2computer.execute(program)

    v2computer.sumMemoryValues().part2Result()
}
