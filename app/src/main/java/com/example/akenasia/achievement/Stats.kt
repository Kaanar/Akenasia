package com.example.akenasia.achievement

import android.content.Context
import com.example.akenasia.Handler.AchievementHandler
import com.example.akenasia.Handler.StatsHandler
import com.example.akenasia.database.Marqueur

class Stats(context:Context, idJoueur: Int) {
        var stathandler= StatsHandler(context)
        var id= idJoueur
        var achievementhandler= AchievementHandler(context)

    fun upMarqueurs(){
        stathandler.upTotalMarqueur(id)

        when(stathandler.getTotalMarqueur(id)){
            10-> achievementhandler.unlock(1)
            20-> achievementhandler.unlock(2)
            30-> achievementhandler.unlock(3)
            40-> achievementhandler.unlock(4)
            50-> achievementhandler.unlock(5)
            75-> achievementhandler.unlock(6)
            100-> achievementhandler.unlock(7)
            150-> achievementhandler.unlock(8)
            200-> achievementhandler.unlock(9)

        }
    }

    fun upMonstres(){
        stathandler.upTotalMonstre(id)

        when(stathandler.getTotalMonstre(id)){
            1-> achievementhandler.unlock(10)
            5-> achievementhandler.unlock(11)
            10-> achievementhandler.unlock(12)
            15-> achievementhandler.unlock(13)
            20-> achievementhandler.unlock(14)
            35-> achievementhandler.unlock(15)
            50-> achievementhandler.unlock(16)
            70-> achievementhandler.unlock(17)
            100-> achievementhandler.unlock(18)
        }
    }

    fun upItems(){
        stathandler.upTotalItem(id)

        when(stathandler.getTotalItem(id)){
            1-> achievementhandler.unlock(19)
            5-> achievementhandler.unlock(20)
            10-> achievementhandler.unlock(21)
            15-> achievementhandler.unlock(22)
            20-> achievementhandler.unlock(23)
            35-> achievementhandler.unlock(24)
            50-> achievementhandler.unlock(25)
            70-> achievementhandler.unlock(26)
            100-> achievementhandler.unlock(27)
        }

    }


}