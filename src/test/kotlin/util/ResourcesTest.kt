package util

import kotlin.test.Test
import kotlin.test.assertEquals

class ResourcesTest {

    @Test
    fun testLoadingResourcesAsInts() {
        val actual = resource("sampleInts.txt").asInts()
        assertEquals(actual, listOf(123, 42, 24, 101, 99))
    }
}