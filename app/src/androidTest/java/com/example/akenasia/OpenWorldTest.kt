package com.example.akenasia

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.Handler.PlaceHandler
import com.example.akenasia.Handler.PositionHandler
import com.example.akenasia.database.Item
import com.example.akenasia.database.ListItems
import com.example.akenasia.database.Place
import com.example.akenasia.database.Position
import com.example.akenasia.game.CoupsLimites
import com.example.akenasia.openworld.OpenWorld
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OpenWorldTest: TestCase(){

    private lateinit var place : Place
    private lateinit var pos : Position
    private lateinit var placeHandler : PlaceHandler
    private lateinit var positionHandler : PositionHandler
    private lateinit var scenario: FragmentScenario<CoupsLimites>
    private lateinit var itemHandler: ItemHandler


    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        pos = Position(context)
        place = Place(0, "RU", 0.0, 0.0)
        placeHandler = PlaceHandler(context)
        positionHandler = PositionHandler(context)
    }

    @Test
    fun randomColorTest() {
        Handler(Looper.getMainLooper()).post {
            val op = OpenWorld()
            assertEquals(BitmapDescriptorFactory.HUE_CYAN, op.randomColor(8))
            assertEquals(BitmapDescriptorFactory.HUE_YELLOW, op.randomColor(9))
            assertEquals(BitmapDescriptorFactory.HUE_GREEN, op.randomColor(10))
            assertEquals(BitmapDescriptorFactory.HUE_VIOLET, op.randomColor(11))
        }
    }

    @Test
    fun DropItemTest() {
        Handler(Looper.getMainLooper()).post {
            val op = OpenWorld()
            val context = ApplicationProvider.getApplicationContext<Context>()
            itemHandler = ItemHandler(context)
            val item = Item(0, "BOUCLIER","Bouclier simple","Parfait pour les débutants",1.0,2.0, 0)
            op.DropItem(0)
            assertEquals("BOUCLIER", item.ItemType)
        }
    }
}
