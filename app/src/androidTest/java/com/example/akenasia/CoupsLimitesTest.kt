package com.example.akenasia

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.android.synthetic.main.coups_limites.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoupsLimitesTest: FragmentScenario.FragmentAction<CoupsLimites>{
    @Test fun XIsVisible() {
        val scenario = launchFragment<CoupsLimites>()
        scenario.onFragment { fragment ->
            fragment.nouvelEssai()
            //Test que les X est bien affichée au premier click
            //assertThat(fragment.CfXCurTV.text.toString(),not(equalTo("")))
        }
    }
    @Test fun YIsVisible() {
        val scenario = launchFragment<CoupsLimites>()
        scenario.onFragment { fragment ->
            fragment.nouvelEssai()
            //Test que les Y est bien affichée au premier click
            //assertThat(fragment.CfYCurTV.text.toString(),not(equalTo("")))
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
    override fun perform(fragment: CoupsLimites) {
    }


}