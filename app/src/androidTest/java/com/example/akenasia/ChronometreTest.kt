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
        val scenario = launchFragment<CoupsLimites>()
        scenario.onFragment { fragment ->
            fragment.nouvelEssai()
            //Test que les X est bien affichée au premier click
            //assertThat(fragment.ChXCurTv.text.toString(), equalTo())
        }
    }
    @Test fun YIsVisible() {
        val scenario = launchFragment<CoupsLimites>()
        scenario.onFragment { fragment ->
            fragment.nouvelEssai()
            //Test que les Y est bien affichée au premier click
            //assertThat(fragment.ChYCurTv.text.toString(),not(equalTo("")))
        }
    }
    @Test fun MinusEssai() {
        val scenario = launchFragment<CoupsLimites>()
        scenario.onFragment { fragment ->
            fragment.nouvelEssai()
            //Test que le premier essai a bien été pris en compte
           assertThat(fragment.getEssais(), not(equalTo(10)))
        }
    }

    override fun perform(fragment: Chronometre) {
        TODO("Not yet implemented")
    }


}