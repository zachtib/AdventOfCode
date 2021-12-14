package util

import libadvent.resource.asInts
import libadvent.resource.load
import kotlin.test.Test
import kotlin.test.assertEquals

class ResourcesTest {

    @Test
    fun testLoadingResourcesAsInts() {
        val actual = load("sampleInts.txt").asInts()
        assertEquals(actual, listOf(123, 42, 24, 101, 99))
    }
}