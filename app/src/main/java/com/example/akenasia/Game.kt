package com.example.akenasia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.akenasia.databinding.ActivityMainBinding
import com.example.akenasia.databinding.CoupsLimitesBinding
import com.example.akenasia.databinding.ChronometreBinding
import kotlinx.android.synthetic.main.coups_limites.*
import kotlinx.android.synthetic.main.chronometre.*
import kotlin.math.atan2
import kotlin.math.sqrt


class Game : AppCompatActivity() {



    private lateinit var pos: Position
    private lateinit var binding: ChronometreBinding
    private lateinit var _binding: CoupsLimitesBinding
    private lateinit var dbHandler : DatabaseHandler
    private lateinit var place: Place
    private var essais=10
    private var lastDistance=0.0

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        dbHandler = DatabaseHandler(this)
        pos = Position(this)
        place= dbHandler.getPlace(intent.getIntExtra("id",0))

        if(intent.getStringExtra("mode").toString()=="chronometre"){
            binding = ChronometreBinding.inflate(layoutInflater)
            setContentView(binding.root)
            Chgoal_X.text=place.getPlaceLat().toString()
            Chgoal_Y.text=place.getPlaceLong().toString()
        }
        else if (intent.getStringExtra("mode").toString()=="coups") {
            _binding = CoupsLimitesBinding.inflate(layoutInflater)
            setContentView(_binding.root)
            Cfgoal_X.text = place.getPlaceLat().toString()
            Cfgoal_Y.text = place.getPlaceLong().toString()

            lastDistance = pos.calcul_distance(
                pos.getLatitude(),
                pos.getLongitude(),
                Cfgoal_Y.text.toString().toDouble(),
                Cfgoal_X.text.toString().toDouble()
            )

            CfQuitGameBT.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)
            }


            CfRefreshBT.setOnClickListener {
                nouvelEssai()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    //maison de l'étudiant 48.902656120835665, 2.2134736447569447
    fun readLocation(){
        pos.refreshLocation()//appel de la méthode qui récupère les coordonnées GPS de l'appareil
        //current_X.text =pos.getLatitude().toString()
       // current_Y.text = pos.getLongitude().toString()
        val distance : Double = pos.calcul_distance(48.902656120835665, 2.2134736447569447, 48.90432845480199, 2.216647218942868)
        Toast.makeText(this,"$distance", Toast.LENGTH_SHORT).show()
    }

    fun nouvelEssai(){
        pos.refreshLocation()
        Cfcurrent_X.text = pos.getLatitude().toString()
        Cfcurrent_Y.text = pos.getLongitude().toString()
        /*
        //tests on simule des positions voir si on obtient les résulats attendus
        if (essais == 9) {
            pos.setLatitude(1.0)
            pos.setLongitude(1.0)

        }
        if (essais == 8) {
            pos.setLatitude(0.5)
            pos.setLongitude(2.0)
        }
        if (essais == 7) {
            pos.setLatitude(0.0)
            pos.setLongitude(1.0)
        }
        if (essais == 6) {
            pos.setLatitude(30.0)
            pos.setLongitude(2.0)
        }
        if (essais == 5) {
            pos.setLatitude(31.0)
            pos.setLongitude(2.0)
        }
        if (essais == 4) {
            pos.setLatitude(32.0)
            pos.setLongitude(2.0)
        }
        if (essais == 3) {
            pos.setLatitude(31.0)
            pos.setLongitude(2.0)
        }
        if (essais == 2) {
            pos.setLatitude(31.0)
            pos.setLongitude(2.0)
        }
        if (essais == 1) {
            pos.setLatitude(48.90527388944)
            pos.setLongitude(2.21568703652)
        }

        Cfcurrent_X.text = pos.getLatitude().toString()
        Cfcurrent_Y.text = pos.getLongitude().toString() */

        val distance : Double =pos.calcul_distance(pos.getLatitude(),
            pos.getLongitude(),
            Cfgoal_X.text.toString().toDouble(),
            Cfgoal_Y.text.toString().toDouble())


        if(essais==1 && distance >= 1500){
            _binding.resultTV.text="Dommage ! vous avez perdu ;_;"
            CfRefreshBT.setVisibility(View.GONE);
            CfQuitGameBT.setVisibility(View.VISIBLE)
        }
        //else{
            //pos.refreshLocation()
            //_binding.CfcurrentX.text=pos.getLatitude().toString()
            //_binding.CfcurrentY.text = pos.getLongitude().toString()
            //val distance : Double =pos.calcul_distance(pos.getLatitude(),
              //  pos.getLongitude(),
               // Cfgoal_X.text.toString().toDouble(),
               // Cfgoal_Y.text.toString().toDouble())
        if(distance<1500){
            //Toast.makeText(this, "Vous avez gagné!",Toast.LENGTH_SHORT).show()
                _binding.resultTV.text="Bravo ! vous avez gagné"
            CfRefreshBT.setVisibility(View.GONE);
            CfQuitGameBT.setVisibility(View.VISIBLE)
            }
        else{
            if (essais in 2..9) {


                if (distance < lastDistance) {
                    _binding.resultTV.text="Vous chauffez !"
                }
                if (distance == lastDistance) {
                    _binding.resultTV.text="AFK ?"
                }
                if (distance > lastDistance) {
                    _binding.resultTV.text="Vous refroidissez"
                }
            }
        }
        lastDistance = distance
        essais--
        _binding.CfessaisTV.text="Il vous reste "+essais+" essais"
        Toast.makeText(this, distance.toString(),Toast.LENGTH_SHORT).show()
    }
}
