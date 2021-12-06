package twentytwenty

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class Day11Tests {
    private val sampleLayout = """
        L.LL.LL.LL
        LLLLLLL.LL
        L.L.L..L..
        LLLL.LL.LL
        L.LL.LL.LL
        L.LLLLL.LL
        ..L.L.....
        LLLLLLLLLL
        L.LLLLLL.L
        L.LLLLL.LL
    """.trimIndent()

    private val sampleAfter1Round = """
        #.##.##.##
        #######.##
        #.#.#..#..
        ####.##.##
        #.##.##.##
        #.#####.##
        ..#.#.....
        ##########
        #.######.#
        #.#####.##
    """.trimIndent()

    private val sampleAfter2Rounds = """
        #.##.##.##
        #######.##
        #.#.#..#..
        ####.##.##
        #.##.##.##
        #.#####.##
        ..#.#.....
        ##########
        #.######.#
        #.#####.##
    """.trimIndent()

    private val stableSample = """
        #.#L.L#.##
        #LLL#LL.L#
        L.#.L..#..
        #L##.##.L#
        #.#L.LL.LL
        #.#L#L#.##
        ..L.L.....
        #L#L##L#L#
        #.LLLLLL.L
        #.#L#L#.##
    """.trimIndent()

    private val secondLayout = """
        L.LL.LL.LL
        LL#L#LL.LL
        L.L.L..L..
        LL#L.LL.LL
        L.LL.LL.LL
        L.LLLLL.LL
        ..L.L.....
        LLLLLL#LLL
        L.LLLLLL.L
        L.LLLLL.LL
    """.trimIndent()

    @Test
    fun `test layout loading`() {
        val layout = Layout(sampleLayout)

        assertEquals(Tile.Seat(occupied = false), layout.tileAt(row = 0, column = 0))
        assertEquals(Tile.Floor, layout.tileAt(row = 0, column = 1))
    }

    @Test
    fun `test adjacent tile detection in corner`() {
        val layout = Layout(sampleLayout)
        val expected = listOf(
                Tile.Floor,
                Tile.Seat(occupied = false),
                Tile.Seat(occupied = false),
        )
        val actual = layout.adjacentTiles(0, 0)
        assertEquals(expected, actual)
    }

    @Test
    fun `test adjacent tile detection`() {
        val layout = Layout(sampleLayout)
        val expected = listOf(
                Tile.Seat(occupied = false),
                Tile.Floor,
                Tile.Seat(occupied = false),

                Tile.Seat(occupied = false),
                Tile.Seat(occupied = false),

                Tile.Seat(occupied = false),
                Tile.Floor,
                Tile.Seat(occupied = false),
        )
        val actual = layout.adjacentTiles(1, 1)
        assertEquals(expected, actual)
    }

    @Test
    fun `test countOccupied`() {
        val tiles = listOf(
                Tile.Seat(occupied = false),
                Tile.Floor,
                Tile.Seat(occupied = true),

                Tile.Seat(occupied = true),
                Tile.Seat(occupied = false),

                Tile.Seat(occupied = true),
                Tile.Floor,
                Tile.Seat(occupied = false),
        )
        val actual = tiles.countOccupied()
        assertEquals(3, actual)
    }

    @Test
    fun `test Layout equals`() {
        val layout = Layout(sampleLayout)
        val other = Layout(sampleLayout)
        assertTrue(layout == other)
    }

    @Test
    fun `test Layout notEqual`() {
        val layout = Layout(sampleLayout)
        val other = Layout(secondLayout)
        assertTrue(layout != other)
    }

    @Test
    fun `test run until stable`() {
        val layout = Layout(sampleLayout)
        layout.runUntilStable()
        val expected = Layout(stableSample)
        assertEquals(expected, layout)
    }

    private val part2step2 = """
        #.##.##.##
        #######.##
        #.#.#..#..
        ####.##.##
        #.##.##.##
        #.#####.##
        ..#.#.....
        ##########
        #.######.#
        #.#####.##
    """.trimIndent()

    private val part2stable = """
        #.L#.L#.L#
        #LLLLLL.LL
        L.L.L..#..
        ##L#.#L.L#
        L.L#.LL.L#
        #.LLLL#.LL
        ..#.L.....
        LLL###LLL#
        #.LLLLL#.L
        #.L#LL#.L#
    """.trimIndent()

    @Test
    fun `test run pt 2 once`() {
        val layout = Layout(sampleLayout)
        layout.runRound2()
        val expected = Layout(part2step2)
        assertEquals(expected, layout)
    }

    @Test
    fun `test run pt 2 until stable`() {
        val layout = Layout(sampleLayout)
        layout.runUntilStable2()
        val expected = Layout(part2stable)
        assertEquals(expected, layout)
    }
}