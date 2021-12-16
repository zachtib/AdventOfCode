package twentytwentyone

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class Day16Tests {

    private val decoder = BitsDecoder()

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

        assertNotNull(decoded)
        assertIs<DecodedBitsPacket.Operator>(decoded)
        assertEquals(1, decoded.version)
        assertEquals(6, decoded.typeId)

        val first = decoded.subpackets[0]
        assertIs<DecodedBitsPacket.Literal>(first)
        assertEquals(10, first.value)

        val second = decoded.subpackets[1]
        assertIs<DecodedBitsPacket.Literal>(second)
        assertEquals(20, second.value)
    }

    @Test
    fun `test decoding second operator packet`() {
        val packet = BitsPacket("EE00D40C823060")
        val decoded = decoder.decodePacket(packet)

        assertNotNull(decoded)
        assertIs<DecodedBitsPacket.Operator>(decoded)
        assertEquals(7, decoded.version)
        assertEquals(3, decoded.typeId)

        val first = decoded.subpackets[0]
        assertIs<DecodedBitsPacket.Literal>(first)
        assertEquals(1, first.value)

        val second = decoded.subpackets[1]
        assertIs<DecodedBitsPacket.Literal>(second)
        assertEquals(2, second.value)

        val third = decoded.subpackets[2]
        assertIs<DecodedBitsPacket.Literal>(third)
        assertEquals(3, third.value)
    }


    @Test
    fun `test part 1 example 1`() {
        val actual = day16Part1(BitsPacket("8A004A801A8002F478"))
        assertEquals(16, actual)
    }

    @Test
    fun `test part 1 example 2`() {
        val actual = day16Part1(BitsPacket("620080001611562C8802118E34"))
        assertEquals(12, actual)
    }

    @Test
    fun `test part 1 example 3`() {
        val actual = day16Part1(BitsPacket("C0015000016115A2E0802F182340"))
        assertEquals(23, actual)
    }

    @Test
    fun `test part 1 example 4`() {
        val actual = day16Part1(BitsPacket("A0016C880162017C3686B18A3D4780"))
        assertEquals(31, actual)
    }

    @Test
    fun `test part 2 example`() {
        val actual = day16Part2()
        assertEquals(0, actual)
    }
}