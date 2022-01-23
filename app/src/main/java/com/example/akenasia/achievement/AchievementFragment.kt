package com.example.akenasia.achievement

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.akenasia.Handler.AchievementHandler
import com.example.akenasia.Handler.ItemHandler
import com.example.akenasia.Handler.PersonnageHandler
import com.example.akenasia.Handler.PlaceHandler
import com.example.akenasia.adapter.AchievementAdapter
import com.example.akenasia.adapter.MyListAdapter
import com.example.akenasia.database.Place
import com.example.akenasia.databinding.AchievementsBinding
import com.example.akenasia.databinding.DatabaseBinding
import com.example.akenasia.databinding.PersonnageBinding
import kotlinx.android.synthetic.main.database.*

class AchievementFragment : AppCompatActivity() {

    private lateinit var binding: AchievementsBinding
    private lateinit var achievementHandler: AchievementHandler

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = AchievementsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        achievementHandler = AchievementHandler(this)
    }


    private fun viewRecord(){
        //calling the viewPlace method of DatabaseHandler class to read the records
        val emp: HashMap<String,Int> = achievementHandler.view()
        val empArrayDesc = Array<String>(emp.size){"0"}
        val empArrayUnlocked = Array<Int>(emp.size){0}

        var index = 0
        for(e in emp){
            empArrayDesc[index] = e.key
            empArrayUnlocked[index] = e.value
            index++
        }
        //creating custom ArrayAdapter
        val achievementAdapter = AchievementAdapter(this,empArrayDesc,empArrayUnlocked)
    }
}