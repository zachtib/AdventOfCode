package twentytwentyone

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class Day16Tests {

    private val decoder = BitsDecoder()
    private val sampleInput = BitsPacket("D2FE28")

    @Test
    fun `test large binary encoding`() {
        val packet = BitsPacket("38006F45291200")
        val expected = "00111000000000000110111101000101001010010001001000000000"
        assertEquals(expected, packet.binary)
    }

    @Test
    fun `test decoding literal packet`() {
        val packet = BitsPacket("D2FE28")
        val decoded = decoder.decodePacket(packet)

        assertIs<DecodedBitsPacket.Literal>(decoded)
        assertEquals(2021, decoded.value)
    }

    @Test
    fun `test decoding operator packet`() {
        val packet = BitsPacket("38006F45291200")
        val decoded = decoder.decodePacket(packet)
        val expected = DecodedBitsPacket.Operator(
            subpackets = listOf(
                DecodedBitsPacket.Literal(10),
                DecodedBitsPacket.Literal(20),
            )
        )
        assertEquals(expected, decoded)
    }

    @Test
    fun `test decoding second operator packet`() {
        val packet = BitsPacket("EE00D40C823060")
        val decoded = decoder.decodePacket(packet)
        val expected = DecodedBitsPacket.Operator(
            subpackets = listOf(
                DecodedBitsPacket.Literal(1),
                DecodedBitsPacket.Literal(2),
                DecodedBitsPacket.Literal(3),
            )
        )
        assertEquals(expected, decoded)
    }


    @Test
    fun `test part 1 example`() {
        val actual = day16Part1(sampleInput)
        assertEquals(0, actual)
    }

    @Test
    fun `test part 2 example`() {
        val actual = day16Part2()
        assertEquals(0, actual)
    }
}