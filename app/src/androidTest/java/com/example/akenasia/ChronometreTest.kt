package com.example.akenasia

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import com.example.akenasia.game.Chronometre
import com.example.akenasia.game.CoupsLimites
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Test

class ChronometreTest: FragmentScenario.FragmentAction<Chronometre>{
    @Test fun XIsVisible() {
        val scenario = launchFragment<Chronometre>()
        scenario.onFragment { fragment ->
            fragment.nouvelEssai()
            //Test que les X est bien affichée au premier click
            //assertThat(fragment.ChXCurTv.text.toString(), equalTo())
        }
    }
    @Test fun YIsVisible() {
        val scenario = launchFragment<Chronometre>()
        scenario.onFragment { fragment ->
            fragment.nouvelEssai()
            //Test que les Y est bien affichée au premier click
            //assertThat(fragment.ChYCurTv.text.toString(),not(equalTo("")))
        }
    }


    override fun perform(fragment: Chronometre) {
        TODO("Not yet implemented")
    }


}