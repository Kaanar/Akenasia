package com.example.akenasia

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock

import android.view.View
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.coups_limites.*
import kotlinx.android.synthetic.main.chronometre.*
import kotlinx.android.synthetic.main.content_game.*
import kotlinx.android.synthetic.main.historique.*
import android.app.PendingIntent.getActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.akenasia.databinding.*
import kotlinx.android.synthetic.main.regles_generales.*


class Game : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var pos: Position
    private lateinit var binding: ReglesGeneralesBinding
    private lateinit var Chronobinding: ChronometreBinding
    private lateinit var CLbinding: CoupsLimitesBinding
    private lateinit var dbHandler : DatabaseHandler
    private lateinit var place: Place
    private lateinit var chronometre: Chronometer
    var isPlay = false
    private var essais=10
    private var lastDistance=0.0
    //private var lat = ArrayList<Double>()
    //private var long = ArrayList<Double>()
    private var i = 0

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ReglesGeneralesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHandler = DatabaseHandler(this)
        pos = Position(this)
        place= dbHandler.getPlace(intent.getIntExtra("id",0))

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.content_game) as NavHostFragment?
        val navController = navHostFragment?.navController
        if (navController != null) {
            appBarConfiguration = AppBarConfiguration(navController.graph)
        }
        if (navController != null) {
            setupActionBarWithNavController(navController, appBarConfiguration)
        }

        begin_game.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", place.getPlaceId().toInt())
            if (intent.getStringExtra("mode").toString() == "chronometre") {
                binding.beginGame.findNavController().navigate(R.id.Chronometre, bundle)
            } else {
                binding.beginGame.findNavController().navigate(R.id.CoupsLimites, bundle)
            }
        }
        /*if(intent.getStringExtra("mode").toString()=="chronometre"){

            Chronobinding = ChronometreBinding.inflate(layoutInflater)
            setContentView(Chronobinding.root)
            chronometre = findViewById(R.id.chronoMterPlay)
            Chgoal_X.text=place.getPlaceLat().toString()
            Chgoal_Y.text=place.getPlaceLong().toString()
            if (!isPlay) {
                chronometre.base = SystemClock.elapsedRealtime()
                chronometre.start()
                isPlay = true
            }
            //Rafraîchit la position de l'utilisateur
            ChRefreshBT.setOnClickListener {
                nouvelEssai()
            }
            //Quitte la partie
            ChQuitGameBT.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)
            }
            //Envoie vers le fragment d'affichage des positions raffraîchies
            ChPositionBT.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id",1)
                val fragment: Fragment = Chronometre()
                val fragmentManager: FragmentManager = this.supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.Historique, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()            }
        }*/
        if (intent.getStringExtra("mode").toString()=="coups") {

            CLbinding = CoupsLimitesBinding.inflate(layoutInflater)
            setContentView(CLbinding.root)
            Cfgoal_X.text = place.getPlaceLat().toString()
            Cfgoal_Y.text = place.getPlaceLong().toString()

            lastDistance = pos.calcul_distance(
                pos.getLatitude(),
                pos.getLongitude(),
                Cfgoal_Y.text.toString().toDouble(),
                Cfgoal_X.text.toString().toDouble()
            )

            //Quitte la partie
            CfQuitGameBT.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                this.startActivity(intent)
            }

            //Envoie vers le fragment d'affichage des positions raffraîchies
            CfPositionBT.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id",1)
                //CLbinding.CfPositionBT.findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle)
            }

            //Rafraîchit la position de l'utilisateur
            CfRefreshBT.setOnClickListener {
                nouvelEssai()
            }
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

    fun nouvelEssai(){
        pos.refreshLocation()
        //Cfcurrent_X.text = pos.getLatitude().toString()
        //Cfcurrent_Y.text = pos.getLongitude().toString()

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

       /* var a = pos.getLatitude()
        var b = pos.getLongitude()
        lat.add(a)
        long.add(b)*/

        //Ajoute la position récupérée dans la base de données
        dbHandler.addPosition(PositionTable(
            10-essais,
            pos.getLatitude(),
            pos.getLongitude(),
            1
        )
        )

        if (intent.getStringExtra("mode").toString()=="chronometre") {
            Chcurrent_X.text = pos.getLatitude().toString()
            Chcurrent_Y.text = pos.getLongitude().toString()

            val distance : Double =pos.calcul_distance(pos.getLatitude(),
                pos.getLongitude(),
                Chgoal_X.text.toString().toDouble(),
                Chgoal_Y.text.toString().toDouble())


            if(essais==1 && distance >= 1500){
                isPlay = false
                chronometre.stop()
                Chronobinding.ChresultTV.text="Dommage ! vous avez perdu ;_;"
                ChRefreshBT.setVisibility(View.GONE);
                ChQuitGameBT.setVisibility(View.VISIBLE)

            }
            if(distance<1500){
                //Toast.makeText(this, "Vous avez gagné!",Toast.LENGTH_SHORT).show()
                isPlay = false
                chronometre.stop()
                Chronobinding.ChresultTV.text= chronometre.getText().toString()
                ChRefreshBT.setVisibility(View.GONE);
                ChQuitGameBT.setVisibility(View.VISIBLE)
                ChPositionBT.setVisibility(View.VISIBLE)
            }
            else{
                if (essais in 2..9) {


                    if (distance < lastDistance) {
                        Chronobinding.ChresultTV.text="Vous chauffez !"
                    }
                    if (distance == lastDistance) {
                        Chronobinding.ChresultTV.text="AFK ?"
                    }
                    if (distance > lastDistance) {
                        Chronobinding.ChresultTV.text="Vous refroidissez"
                    }
                }
            }
            lastDistance = distance
            essais--
            Chronobinding.ChessaisTV.text="Il vous reste "+essais+" essais"
            Toast.makeText(this, distance.toString(),Toast.LENGTH_SHORT).show()
        }

        if (intent.getStringExtra("mode").toString()=="coups") {
            Cfcurrent_X.text = pos.getLatitude().toString()
            Cfcurrent_Y.text = pos.getLongitude().toString()

            val distance : Double =pos.calcul_distance(pos.getLatitude(),
                pos.getLongitude(),
                Cfgoal_X.text.toString().toDouble(),
                Cfgoal_Y.text.toString().toDouble())


            if(essais==1 && distance >= 1500){
                CLbinding.CfresultTV.text="Dommage ! vous avez perdu ;_;"
                CfRefreshBT.setVisibility(View.GONE);
                CfQuitGameBT.setVisibility(View.VISIBLE)
                CfPositionBT.setVisibility(View.VISIBLE)
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
                CLbinding.CfresultTV.text="Bravo ! vous avez gagné"
                CfRefreshBT.setVisibility(View.GONE);
                CfQuitGameBT.setVisibility(View.VISIBLE)
                CfPositionBT.setVisibility(View.VISIBLE)
            }
            else{
                if (essais in 2..9) {
                    if (distance < lastDistance) {
                        CLbinding.CfresultTV.text="Vous chauffez !"
                    }
                    if (distance == lastDistance) {
                        CLbinding.CfresultTV.text="AFK ?"
                    }
                    if (distance > lastDistance) {
                        CLbinding.CfresultTV.text="Vous refroidissez"
                    }
                }
            }
            lastDistance = distance
            essais--
            CLbinding.CfessaisTV.text="Il vous reste "+essais+" essais"
            Toast.makeText(this, distance.toString(),Toast.LENGTH_SHORT).show()
        }
    }
    /*fun liste(lat : ArrayList<Double>, long : ArrayList<Double>, i : Int) {
        _binding.CfessaisTV.text ="Position " + (i+1) + " | X : " + lat[i].toString()+ " Y : " + long[i].toString()
    }*/

}