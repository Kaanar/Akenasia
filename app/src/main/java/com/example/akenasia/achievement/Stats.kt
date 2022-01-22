package com.example.akenasia.achievement

import android.content.Context
import android.widget.Toast
import com.example.akenasia.Handler.AchievementHandler
import com.example.akenasia.Handler.StatsHandler
import com.example.akenasia.database.Marqueur

class Stats(context:Context, idJoueur: Int) {
        var thiscontext=context
        var stathandler= StatsHandler(context)
        var id= idJoueur
        var achievementhandler= AchievementHandler(context)

    fun upMarqueurs(){
        stathandler.upTotalMarqueur(id)

        when(stathandler.getTotalMarqueur(id)){
            10-> {achievementhandler.unlock(1)
            Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            20-> {achievementhandler.unlock(2)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            30-> {achievementhandler.unlock(3)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            40-> {achievementhandler.unlock(4)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            50-> {achievementhandler.unlock(5)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            75-> {achievementhandler.unlock(6)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            100-> {achievementhandler.unlock(7)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            150-> {achievementhandler.unlock(8)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            200-> {achievementhandler.unlock(9)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
        }
    }

    fun upMonstres(){
        stathandler.upTotalMonstre(id)

        when(stathandler.getTotalMonstre(id)){
            1-> {achievementhandler.unlock(10)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            5-> {achievementhandler.unlock(11)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            10-> {achievementhandler.unlock(12)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            15-> {achievementhandler.unlock(13)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            20-> {achievementhandler.unlock(14)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            35-> {achievementhandler.unlock(15)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            50-> {achievementhandler.unlock(16)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            70-> {achievementhandler.unlock(17)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            100-> {achievementhandler.unlock(18)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
        }
    }

    fun upItems(){
        stathandler.upTotalItem(id)

        when(stathandler.getTotalItem(id)){
            1-> {achievementhandler.unlock(19)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            5-> {achievementhandler.unlock(20)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            10-> {achievementhandler.unlock(21)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            15-> {achievementhandler.unlock(22)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            20-> {achievementhandler.unlock(23)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            35-> {achievementhandler.unlock(24)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            50-> {achievementhandler.unlock(25)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            70-> {achievementhandler.unlock(26)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
            100-> {achievementhandler.unlock(27)
                Toast.makeText(thiscontext,"Nouveau succès dévérouillé!",Toast.LENGTH_LONG).show()}
        }

    }


}