package twentytwentyone

import libadvent.part1
import libadvent.part2
import libadvent.resource.load
import libadvent.util.toBinaryInt
import libadvent.util.toBinaryLong


data class BitsPacket(val content: String)

val BitsPacket.binary: String
    get() = content.toCharArray().joinToString(separator = "") { char ->
        char.digitToInt(16)
            .toString(2)
            .padStart(4, padChar = '0')
    }

fun BitsPacket.biterator() = Biterator(this.binary)

class Biterator(private val binaryString: String) {

    private var bitsTaken = 0

    private fun bitsRemaining(): Int {
        return binaryString.length - bitsTaken
    }

    private fun isEmpty(): Boolean {
        return bitsRemaining() > 0
    }

    private fun takeCharacters(length: Int): String {
        val result = binaryString.substring(bitsTaken, bitsTaken + length)
        bitsTaken += result.length
        return result
    }

    fun takeBitsAsInt(n: Int): Int {
        val string = takeCharacters(n)
        return string.toBinaryInt()
    }

    fun takeBitsAsString(n: Int): String {
        return takeCharacters(n)
    }

    fun takeBitsAsIntOrNull(n: Int): Int? {
        return if (bitsRemaining() < n) {
            null
        } else {
            takeBitsAsInt(n)
        }
    }

    fun takeBitsAsStringOrNull(n: Int): String? {
        return if (bitsRemaining() < n) {
            null
        } else {
            takeBitsAsString(n)
        }
    }
}

sealed class DecodedBitsPacket {
    abstract val version: Int
    abstract val typeId: Int

    data class Literal(
        override val version: Int,
        override val typeId: Int,
        val value: Long,
    ) : DecodedBitsPacket()

    data class Operator(
        override val version: Int,
        override val typeId: Int,
        val subpackets: List<DecodedBitsPacket>,
    ) : DecodedBitsPacket()
}

class BitsDecoder {

    companion object {
        private const val TYPE_ID_LITERAL = 4
    }

    private fun decodeLiteralValue(biterator: Biterator): Long {
        var bitChunks = ""
        var chunk = biterator.takeBitsAsStringOrNull(5)
        while (chunk != null) {
            val initial = chunk.first().digitToInt(2)
            val remainder = chunk.substring(1)
            bitChunks += remainder
            println("prefix: $initial, value: $remainder")

            chunk = if (initial == 0) {
                null
            } else {
                biterator.takeBitsAsStringOrNull(5)
            }
        }

        return bitChunks.toBinaryLong()
    }

    private fun decodePacket(biterator: Biterator): DecodedBitsPacket? {
        val packetVersion = biterator.takeBitsAsIntOrNull(3) ?: return null
        val typeId = biterator.takeBitsAsIntOrNull(3) ?: return null

        println("version: $packetVersion, type: $typeId")

        if (typeId == TYPE_ID_LITERAL) {
            val decodedValue = decodeLiteralValue(biterator)
            println("decoded value: $decodedValue")
            return DecodedBitsPacket.Literal(
                version = packetVersion,
                typeId = typeId,
                value = decodedValue,
            )
        }
        val lengthTypeId = biterator.takeBitsAsInt(1)

        if (lengthTypeId == 0) {
            val totalLengthInBits = biterator.takeBitsAsInt(15)
            println("length: $totalLengthInBits")
            val bitString = biterator.takeBitsAsString(totalLengthInBits)
            val subiterator = Biterator(bitString)

            val subpackets = buildList {
                var nextPacket = decodePacket(subiterator)
                while (nextPacket != null) {
                    add(nextPacket)
                    nextPacket = decodePacket(subiterator)
                }
            }
            return DecodedBitsPacket.Operator(
                version = packetVersion,
                typeId = typeId,
                subpackets = subpackets,
            )
        } else if (lengthTypeId == 1) {
            val numberOfSubPackets = biterator.takeBitsAsInt(11)
            println("packets: $numberOfSubPackets")
            val subpackets = buildList {
                repeat(numberOfSubPackets) {
                    add(decodePacket(biterator)!!)
                }
            }
            return DecodedBitsPacket.Operator(
                version = packetVersion,
                typeId = typeId,
                subpackets = subpackets,
            )
        }
        return null
    }

    fun decodePacket(packet: BitsPacket): DecodedBitsPacket? {
        val biterator = packet.biterator()
        return decodePacket(biterator)
    }
}


private fun sumAllVersionNumbers(packets: List<DecodedBitsPacket>): Int {
    return packets.sumOf { sumAllVersionNumbers(it) }
}

fun sumAllVersionNumbers(decodedPacket: DecodedBitsPacket?): Int {
    val packet = decodedPacket ?: return 0
    return packet.version + when (decodedPacket) {
        is DecodedBitsPacket.Literal -> 0
        is DecodedBitsPacket.Operator -> sumAllVersionNumbers(decodedPacket.subpackets)
    }
}

fun day16Part1(packet: BitsPacket): Int {
    val decoder = BitsDecoder()
    val decodedPacket = decoder.decodePacket(packet)

    return sumAllVersionNumbers(decodedPacket)
}

fun day16Part2(): Int {
    throw NotImplementedError()
}

fun main() {
    val input = load("2021/day16.txt")
    val packet = BitsPacket(input.body)

    part1 { day16Part1(packet) }
    part2 { day16Part2() }
}