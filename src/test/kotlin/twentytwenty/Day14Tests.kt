package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class Day14Tests {

    private lateinit var encoder: BitEncoder
    private lateinit var masker: BitMasker
    private lateinit var computer: BitmaskComputer
    private lateinit var biterator: Biterator
    private lateinit var memory: FloatingMemory
    private lateinit var v2computer: BitmaskComputerV2

    @Before
    fun setUp() {
        encoder = BitEncoder()
        masker = BitMasker()
        computer = BitmaskComputer()
        biterator = Biterator()
        v2computer = BitmaskComputerV2()
    }

    @Test
    fun `test encoding long`() {
        val actual = encoder.toByteString(11)
        assertEquals("000000000000000000000000000000001011", actual)
    }

    @Test
    fun `test decoding long`() {
        val actual = encoder.toLong("000000000000000000000000000001001001")
        assertEquals(73L, actual)
    }

    @Test
    fun `test masking`() {
        masker.bitmask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"
        val actual = masker.mask(11L)
        assertEquals(73L, actual)
    }

    @Test
    fun `test v2 masking`() {
        masker.bitmask = "000000000000000000000000000000X1001X"
        val actual = masker.maskAddress(42L)
        assertEquals("000000000000000000000000000000X1101X", actual)
    }

    @Test
    fun `test simple program`() {
        val program: List<BitmaskInstruction> = """
            mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
            mem[8] = 11
            mem[7] = 101
            mem[8] = 0
        """.trimIndent().lines().map { BitmaskInstruction.fromString(it) }
        computer.execute(program)
        val actual = computer.sumMemoryValues()
        assertEquals(165L, actual)
    }

    @Test
    fun `test biteration`() {
        val actual = biterator.biterateLongs("000000000000000000000000000000X1101X")
        val expected = listOf(26L, 27L, 58L, 59L)
        assertEquals(expected, actual)
    }

    @Test
    fun `test simple program v2`() {
        val program: List<BitmaskInstruction> = """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1
        """.trimIndent().lines().map { BitmaskInstruction.fromString(it) }
        v2computer.execute(program)
        val actual = v2computer.sumMemoryValues()
        assertEquals(208L, actual)
    }
}