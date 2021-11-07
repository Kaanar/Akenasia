package com.example.akenasia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.akenasia.databinding.ChronometreBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.chronometre.*
import kotlinx.android.synthetic.main.coups_limites.*


class Chronometre : Fragment(){

    private var _binding: ChronometreBinding? = null
    private lateinit var pos: Position
    private val binding get() = _binding!!
    private var thiscontext: Context? = null
    private lateinit var dbHandler : DatabaseHandler
    private lateinit var place: Place
    private lateinit var chronometre: Chronometer
    var isPlay = false
    private var i=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pos = Position(this.requireActivity())
        if (container != null) {
            val id= this.arguments?.getInt("id")
            thiscontext = container.getContext()
            dbHandler = DatabaseHandler(thiscontext!!)
            place= dbHandler.getPlace(id!!)
        }
        _binding = ChronometreBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chronometre = binding.chronoMterPlay
        Chgoal_X.text=place.getPlaceLat().toString()
        Chgoal_Y.text=place.getPlaceLong().toString()

        pos.refreshLocation()
        //Chcurrent_X.text = pos.getLatitude().toString() servait quand pour les coordonnées en texte
        //Chcurrent_Y.text = pos.getLongitude().toString()

        if (!isPlay) {
            chronometre.base = SystemClock.elapsedRealtime() + 5000
            chronometre.start()
            isPlay = true
        }
        //Rafraîchit la position de l'utilisateur

        //Quitte la partie
        ChQuitGameBT.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            this.startActivity(intent)
        }
        //Envoie vers le fragment d'affichage des positions raffraîchies
        ChPositionBT.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id",1)
            bundle.putString("mode","Chronometre")
            findNavController().navigate(R.id.Histo,bundle)
        }

        //Vérifie chaque tick du chrono
        chronometre.onChronometerTickListener = Chronometer.OnChronometerTickListener {
            nouvelEssai()
            val currentTime: String = chronometre.getText().toString()
            //On arrête la partie lorsque le chrono arrive à 0
            if (currentTime == "00:00") {
                chronometre.stop()
                isPlay = false
                ChresultTV.text = "Temps écoulé, c'est perdu ;_;"
                ChQuitGameBT.setVisibility(View.VISIBLE)
                ChPositionBT.setVisibility(View.VISIBLE)
            }
        }
    }

    fun nouvelEssai() {
        pos.refreshLocation()

        dbHandler.addPosition(
            PositionTable(
                i,
                pos.getLatitude(),
                pos.getLongitude(),
                1
            )
        )
        //Chcurrent_X.text = pos.getLatitude().toString()
        //Chcurrent_Y.text = pos.getLongitude().toString() servait pour les coordonnées en texte

        //calcul de la distance
        val distance: Double = pos.calcul_distance(
            pos.getLatitude(),
            pos.getLongitude(),
            Chgoal_X.text.toString().toDouble(),
            Chgoal_Y.text.toString().toDouble()
        )
        //Condition d'arrêt -> Victoire
        if (distance < 1500) {
            //Toast.makeText(this, "Vous avez gagné!",Toast.LENGTH_SHORT).show()
            isPlay = false
            chronometre.stop()
            ChresultTV.text = "Bravo ! Vous avez gagné"
            ChQuitGameBT.setVisibility(View.VISIBLE)
            ChPositionBT.setVisibility(View.VISIBLE)
        }
        i++
    }
}