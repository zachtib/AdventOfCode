package twentytwentyone

import libadvent.resource.asType
import libadvent.resource.resourceOf
import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Tests {

    private val sampleInput = resourceOf("""
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end
    """.trimIndent()).asType { it.toCaveNodePair() }

    private val secondExample = resourceOf("""
        dc-end
        HN-start
        start-kj
        dc-start
        dc-HN
        LN-dc
        HN-end
        kj-sa
        kj-HN
        kj-dc
    """.trimIndent()).asType { it.toCaveNodePair() }

    private val thirdExample = resourceOf("""
        fs-end
        he-DX
        fs-he
        start-DX
        pj-DX
        end-zg
        zg-sl
        zg-pj
        pj-he
        RW-he
        fs-DX
        pj-RW
        zg-RW
        start-pj
        he-WI
        zg-he
        pj-fs
        start-RW
    """.trimIndent()).asType { it.toCaveNodePair() }

    @Test
    fun `test first part 1 given`() {
        val cave = Cave(sampleInput)
        val paths = cave.findAllPaths()
        assertEquals(10, paths.size)
    }

    @Test
    fun `test second part 1 given`() {
        val cave = Cave(secondExample)
        val paths = cave.findAllPaths()
        assertEquals(19, paths.size)
    }

    @Test
    fun `test third part 1 given`() {
        val cave = Cave(thirdExample)
        val paths = cave.findAllPaths()
        assertEquals(226, paths.size)
    }
    @Test
    fun `test first part 2 given`() {
        val cave = Cave(sampleInput)
        val paths = cave.findAllPaths(true)
        assertEquals(36, paths.size)
    }

    @Test
    fun `test second part 2 given`() {
        val cave = Cave(secondExample)
        val paths = cave.findAllPaths(true)
        assertEquals(103, paths.size)
    }

    @Test
    fun `test third part 2 given`() {
        val cave = Cave(thirdExample)
        val paths = cave.findAllPaths(true)
        assertEquals(3509, paths.size)
    }
}