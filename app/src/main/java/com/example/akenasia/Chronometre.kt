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
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.akenasia.databinding.ChronometreBinding
import kotlinx.android.synthetic.main.chronometre.*


class Chronometre : Fragment(){
    private var _binding: ChronometreBinding? = null
    private lateinit var pos: Position

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var thiscontext: Context? = null
    private lateinit var dbHandler : DatabaseHandler
    private lateinit var place: Place
    private lateinit var chronometre: Chronometer
    var isPlay = false
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
        _binding = ChronometreBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chronometre = binding.chronoMterPlay
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
            val intent = Intent(context, MainActivity::class.java)
            this.startActivity(intent)
        }
        //Envoie vers le fragment d'affichage des positions raffraîchies
        ChPositionBT.setOnClickListener {
            findNavController().navigate(R.id.ChronoHisto)          }
    }

    fun nouvelEssai() {
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
        dbHandler.addPosition(
            PositionTable(
                10 - essais,
                pos.getLatitude(),
                pos.getLongitude(),
                1
            )
        )
            Chcurrent_X.text = pos.getLatitude().toString()
            Chcurrent_Y.text = pos.getLongitude().toString()

            val distance: Double = pos.calcul_distance(
                pos.getLatitude(),
                pos.getLongitude(),
                Chgoal_X.text.toString().toDouble(),
                Chgoal_Y.text.toString().toDouble()
            )


            if (essais == 1 && distance >= 1500) {
                isPlay = false
                chronometre.stop()
                ChresultTV.text = "Dommage ! vous avez perdu ;_;"
                ChRefreshBT.setVisibility(View.GONE);
                ChQuitGameBT.setVisibility(View.VISIBLE)

            }
            if (distance < 1500) {
                //Toast.makeText(this, "Vous avez gagné!",Toast.LENGTH_SHORT).show()
                isPlay = false
                chronometre.stop()
                ChresultTV.text = chronometre.getText().toString()
                ChRefreshBT.setVisibility(View.GONE);
                ChQuitGameBT.setVisibility(View.VISIBLE)
                ChPositionBT.setVisibility(View.VISIBLE)
            } else {
                if (essais in 2..9) {


                    if (distance < lastDistance) {
                        ChresultTV.text = "Vous chauffez !"
                    }
                    if (distance == lastDistance) {
                        ChresultTV.text = "AFK ?"
                    }
                    if (distance > lastDistance) {
                        ChresultTV.text = "Vous refroidissez"
                    }
                }
            }
            lastDistance = distance
            essais--
            ChessaisTV.text = "Il vous reste " + essais + " essais"
            Toast.makeText(context, distance.toString(), Toast.LENGTH_SHORT).show()
        }
    }


