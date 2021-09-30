package com.example.akenasia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.akenasia.databinding.GeolocalisationBinding
import kotlinx.android.synthetic.main.geolocalisation.*




/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class Geolocalisation : Fragment() {



    private var _binding: GeolocalisationBinding? = null
    private lateinit var pos: Position

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pos = Position(this.requireActivity())
        _binding = GeolocalisationBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        current_X.text = "-1"
        current_Y.text= "-1"
        goal_X.text = "48.890900"
        goal_Y.text = "2.209300"

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.bRefresh.setOnClickListener {
            readLocation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun readLocation(){
        pos.refreshLocation()//appel de la méthode qui récupère les coordonnées GPS de l'appareil
        current_X.text =pos.getLatitude().toString()
        current_Y.text = pos.getLongitude().toString()
        val distance : Double = pos.calcul_distance(pos.getLatitude(), pos.getLongitude(), 48.890900, 2.209300)
        Toast.makeText(activity,"$distance", Toast.LENGTH_SHORT).show()

        if (distance<1000){
            Toast.makeText(activity,"<1000 bravo", Toast.LENGTH_SHORT).show()
        }
    }
}