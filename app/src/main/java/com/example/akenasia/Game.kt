package com.example.akenasia

import android.app.Activity
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
import com.example.akenasia.databinding.ChaudFroidBinding
import com.example.akenasia.databinding.ChronometreBinding
import kotlinx.android.synthetic.main.chaud_froid.*
import kotlinx.android.synthetic.main.chronometre.*


class Game : AppCompatActivity() {



    private lateinit var pos: Position
    private lateinit var binding: ChronometreBinding
    private lateinit var _binding: ChaudFroidBinding
    private lateinit var dbHandler : DatabaseHandler
    private lateinit var place: Place

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        dbHandler = DatabaseHandler(this)
        place= dbHandler.getPlace(intent.getIntExtra("id",0))

        if(intent.getStringExtra("mode").toString()=="chronometre"){
            binding = ChronometreBinding.inflate(layoutInflater)
            setContentView(binding.root)
            Chgoal_X.text=place.getPlaceLong().toString()
            Chgoal_Y.text=place.getPlaceLat().toString()
        }
        else if (intent.getStringExtra("mode").toString()=="c/f"){
            _binding = ChaudFroidBinding.inflate(layoutInflater)
            setContentView(_binding.root)
            Cfgoal_X.text=place.getPlaceLong().toString()
            Cfgoal_Y.text=place.getPlaceLat().toString()
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

        if (distance<1000){
            //Toast.makeText(activity,"<1000 bravo", Toast.LENGTH_SHORT).show()
        }
    }
}