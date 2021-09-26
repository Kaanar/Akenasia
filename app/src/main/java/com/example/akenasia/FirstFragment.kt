package com.example.akenasia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.widget.TextView





/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.textview_XCoordonnees_Current_Value).text = "0"
        view.findViewById<TextView>(R.id.textview_YCoordonnees_Current_Value).text = "0"
        view.findViewById<TextView>(R.id.textview_XCoordonnees_Goals_Value).text = "48.890900"
        view.findViewById<TextView>(R.id.textview_YCoordonnees_Goals_Value).text = "2.209300"

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            //https://www.webrankinfo.com/forum/t/calcul-des-coordonnees-gps-dans-un-rayon-de-x-km.74024/
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)


        }

    }
}