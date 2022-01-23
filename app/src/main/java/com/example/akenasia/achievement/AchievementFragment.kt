package com.example.akenasia.achievement

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.akenasia.Handler.AchievementHandler
import com.example.akenasia.R
import com.example.akenasia.adapter.AchievementAdapter
import com.example.akenasia.databinding.AchievementsBinding
import com.example.akenasia.home.MainActivity
import com.example.akenasia.openworld.Bag
import com.example.akenasia.openworld.OpenWorld
import com.example.akenasia.openworld.Personnage
import kotlinx.android.synthetic.main.achievements.*

class AchievementFragment : AppCompatActivity() {

    private lateinit var binding: AchievementsBinding
    private lateinit var achievementHandler: AchievementHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AchievementsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        achievementHandler = AchievementHandler(applicationContext)

        viewRecord()

        binding.NavigationView.selectedItemId = R.id.PersonnageClick

        //Implémentation des différents choix du menu
        binding.NavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.QuitClick -> {
                    val intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)
                }
                R.id.MapClick -> {
                    val intent = Intent(this, OpenWorld::class.java)
                    this.startActivity(intent)
                }
                R.id.BagClick -> {
                    val intent = Intent(this, Bag::class.java)
                    this.startActivity(intent)
                }
                else -> {
                    val intent = Intent(this, Personnage::class.java)
                    this.startActivity(intent)
                }
            }
            true
        }
    }



    private fun viewRecord(){
        //calling the viewPlace method of DatabaseHandler class to read the records
        val emp: HashMap<String,Int> = achievementHandler.view()
        val empArrayid = Array<String>(emp.size){"0"}
        val empArrayDesc = Array<String>(emp.size){"null"}
        val empArrayUnlocked = Array<String>(emp.size){"null"}

        var index = 0
        for(e in emp){
            empArrayid[index]= (index+1).toString()
            empArrayDesc[index] = e.key
            empArrayUnlocked[index] = e.value.toString()
            index++
        }
        //creating custom ArrayAdapter
        val achievementAdapter = AchievementAdapter(this,empArrayid,empArrayDesc,empArrayUnlocked)
        ListViewAchievements?.adapter= achievementAdapter
    }
}