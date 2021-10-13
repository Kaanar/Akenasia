package com.example.akenasia

import org.junit.jupiter.api.Assertions.*

internal class PlaceTest {
    private val p = Place(0, "Test", 11.1111, 99.99)

    @org.junit.jupiter.api.Test
    fun getPlaceId() {
       assertEquals(0, p.getPlaceId())
    }

    @org.junit.jupiter.api.Test
    fun getPlaceName() {
        assertEquals("Test", p.getPlaceName())
    }

    @org.junit.jupiter.api.Test
    fun getPlaceLat() {
        assertEquals(11.1111, p.getPlaceLat())
    }

    @org.junit.jupiter.api.Test
    fun getPlaceLong() {
        assertEquals(99.99, p.getPlaceLong())
    }
}