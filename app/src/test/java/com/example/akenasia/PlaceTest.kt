package com.example.akenasia

import com.example.akenasia.database.Place
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class PlaceTest {
    private val p = Place(0, "Test", 11.1111, 99.99)

    @Test
    fun getPlaceId() {
       assertEquals(0, p.getPlaceId())
    }

    @Test
    fun getPlaceName() {
        assertEquals("Test", p.getPlaceName())
    }

    @Test
    fun getPlaceLat() {
        assertEquals(11.1111, p.getPlaceLat())
    }

    @Test
    fun getPlaceLong() {
        assertEquals(99.99, p.getPlaceLong())
    }
}