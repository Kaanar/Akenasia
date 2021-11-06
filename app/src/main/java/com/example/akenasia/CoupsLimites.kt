package com.example.akenasia


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.akenasia.databinding.CoupsLimitesBinding
import kotlinx.android.synthetic.main.coups_limites.*
import com.example.akenasia.Game.*
import com.example.akenasia.databinding.ChronometreBinding
import kotlinx.android.synthetic.main.chronometre.*

class CoupsLimites: Fragment() {
    private var _binding: CoupsLimitesBinding? = null
    private lateinit var pos: Position
    private val binding get() = _binding!!
    private var thiscontext: Context? = null
    private lateinit var dbHandler : DatabaseHandler
    private lateinit var place: Place
    private var essais=10
    private var lastDistance=0.0

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
        _binding = CoupsLimitesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Cfgoal_X.text=place.getPlaceLat().toString()
        Cfgoal_Y.text=place.getPlaceLong().toString()

        //Rafraîchit la position de l'utilisateur
        CfRefreshBT.setOnClickListener {
            nouvelEssai()
        }
        //Quitte la partie
        CfQuitGameBT.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            this.startActivity(intent)
        }
        //Envoie vers le fragment d'affichage des positions raffraîchies
        CfPositionBT.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id",1)
            bundle.putString("mode","CL")
            findNavController().navigate(R.id.action_Coups_limites_to_Historique,bundle)
        }

        binding.btnMap3.setOnClickListener {
            findNavController().navigate(R.id.action_CoupsLimitesHisto_to_map_fragment)
        }
    }

    fun nouvelEssai() {
        pos.refreshLocation()

        Cfcurrent_X.text = pos.getLatitude().toString()
        Cfcurrent_Y.text = pos.getLongitude().toString()

        //tests on simule des positions voir si on obtient les résulats attendus
       /* if (essais == 9) {
            //pos.setLatitude(10.0)
            //pos.setLongitude(10.0)

        }
        if (essais == 8) {
            //pos.setLatitude(-10.5)
            //pos.setLongitude(-2.0)
        }
        if (essais == 7) {
            //pos.setLatitude(0.0)
            //pos.setLongitude(1.0)
        }
        if (essais == 6) {
            //pos.setLatitude(30.0)
            //pos.setLongitude(2.0)
        }
        if (essais == 5) {
            //pos.setLatitude(50.0)
            //pos.setLongitude(2.0)
        }
        if (essais == 4) {
            //pos.setLatitude(32.0)
            //pos.setLongitude(-2.0)
        }
        if (essais == 3) {
            //pos.setLatitude(-31.0)
            //pos.setLongitude(2.0)
        }
        if (essais == 2) {
            //pos.setLatitude(31.0)
            //pos.setLongitude(2.0)
        }
        if (essais == 1) {
            //pos.setLatitude(48.90527388944)
            //pos.setLongitude(2.21568703652)
        } */

        //Ajoute la position récupérée dans la base de données
        dbHandler.addPosition(
            PositionTable(
                10 - essais,
                pos.getLatitude(),
                pos.getLongitude(),
                1
            )
        )
        Cfcurrent_X.text = pos.getLatitude().toString()
        Cfcurrent_Y.text = pos.getLongitude().toString()

        val distance: Double = pos.calcul_distance(
            pos.getLatitude(),
            pos.getLongitude(),
            Cfgoal_X.text.toString().toDouble(),
            Cfgoal_Y.text.toString().toDouble()
        )


        if (essais == 1 && distance >= 1500) {
            CfresultTV.text = "Dommage ! vous avez perdu ;_;"
            CfRefreshBT.setVisibility(View.GONE);
            CfQuitGameBT.setVisibility(View.VISIBLE)
            CfPositionBT.setVisibility(View.VISIBLE)
        }
        if (distance < 1500) {
            //Toast.makeText(this, "Vous avez gagné!",Toast.LENGTH_SHORT).show()
            CfresultTV.text = "Bravo ! Vous avez gagné"
            CfRefreshBT.setVisibility(View.GONE);
            CfQuitGameBT.setVisibility(View.VISIBLE)
            CfPositionBT.setVisibility(View.VISIBLE)
        } else {
            if (essais in 2..9) {
                if (distance < lastDistance) {
                    CfresultTV.text = "Vous chauffez !"
                }
                if (distance == lastDistance) {
                    CfresultTV.text = "AFK ?"
                }
                if (distance > lastDistance) {
                    CfresultTV.text = "Vous refroidissez"
                }
            }
        }
        if(essais==0){
            CfRefreshBT.setVisibility(View.GONE);
            CfQuitGameBT.setVisibility(View.VISIBLE)
            CfPositionBT.setVisibility(View.VISIBLE)
            CfessaisTV.text = "Il vous reste " + essais + " essais"
        }
        else{
            lastDistance = distance
            essais--
            CfessaisTV.text = "Il vous reste " + essais + " essais"
            Toast.makeText(context,distance.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}