package com.example.akenasia

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.akenasia.home.HomePage
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
class HomePageTest: TestCase(){
    private lateinit var scenario: FragmentScenario<HomePage>

    @Before
    public override fun setUp() {
        super.setUp()

        scenario = launchFragmentInContainer(themeResId = R.style.Theme_Akenasia)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test fun ClickBouton() {
        onView(withId(R.id.HomepageJouerBT)).perform(click())
    }

    @Test fun TextBouton() {
        assertEquals("Jouer", R.id.HomepageJouerBT)
    }
}
