package com.example.akenasia

import android.content.Context
import com.example.akenasia.database.Position
import org.junit.Test

import org.junit.Before


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    lateinit var pos : Position
    private lateinit var instrumentationContext: Context

    @Before public fun initialize(){
        // pos= Position(instrumentationContext)
    }

    @Test
    fun position_Is_Correct() {
        /*assertEquals(pos.getLatitude(), 0.0)
        assertEquals(pos.getLongitude(),0.0)*/
    }
}