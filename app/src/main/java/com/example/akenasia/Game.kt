package com.example.akenasia

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock

import android.view.View
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.coups_limites.*
import kotlinx.android.synthetic.main.chronometre.*
import kotlinx.android.synthetic.main.content_game.*
import kotlinx.android.synthetic.main.historique.*
import com.example.akenasia.databinding.*
import kotlinx.android.synthetic.main.regles_generales.*


class Game : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var pos: Position
    private lateinit var Reglesbinding: ReglesGeneralesBinding
    private lateinit var binding: ActivityGameBinding
    private lateinit var Chronobinding: ChronometreBinding
    private lateinit var CLbinding: CoupsLimitesBinding
    private lateinit var dbHandler : DatabaseHandler
    private lateinit var place: Place
    private lateinit var chronometre: Chronometer
    var isPlay = false
    private var essais=10
    private var lastDistance=0.0
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHandler = DatabaseHandler(this)
        pos = Position(this)
        place= dbHandler.getPlace(intent.getIntExtra("id",0))

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.include2) as NavHostFragment?
        val navController = navHostFragment?.navController
        if (navController != null) {
            appBarConfiguration = AppBarConfiguration(navController.graph)
        }
        if (navController != null) {
            setupActionBarWithNavController(navController, appBarConfiguration)
        }

    }

    //maison de l'étudiant 48.902656120835665, 2.2134736447569447
    fun readLocation(){
        pos.refreshLocation()//appel de la méthode qui récupère les coordonnées GPS de l'appareil
        //current_X.text =pos.getLatitude().toString()
        // current_Y.text = pos.getLongitude().toString()
        val distance : Double = pos.calcul_distance(48.902656120835665, 2.2134736447569447, 48.90432845480199, 2.216647218942868)
        Toast.makeText(this,"$distance", Toast.LENGTH_SHORT).show()
    }
}