package com.example.akenasia

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class PositionTest {
    private val pos = Position.PositionTest()

    @Test
    fun test_calcul_distance() {
        assertEquals(0.0, pos.calcul_distance(0.0, 0.0, 0.0, 0.0))
        //résultat attendu 0.0
    }

    @Test
    fun test_calcul_distance2() {
        assertEquals(217.0, pos.calcul_distance(48.780791, 2.194554, 48.780860991009554, 2.1975147547196374), 217.0*0.01)
        //résultat attendu environ = 217 mètres
        //résultat 216.94 mètres
    }

    @Test
    fun test_calcul_distance3() {
        assertEquals(11250.0, pos.calcul_distance(48.805756910426176, 2.16831734680431, 48.90217564349271, 2.215013203244392), 11250.0*0.01)
        //résultat attendu environ = 11.25 km
        //résultat 11.245 km
    }

    @Test
    fun test_calcul_distance4() {
        assertEquals(9716328.0, pos.calcul_distance(48.90231689667814, 2.214946021579729, 35.68433600366023, 139.75309056001143), 9716328.0*0.01)
        //résultat attendu environ = 9716.328 km
        //résultat 9710.227 km
    }
}