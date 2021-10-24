package com.example.akenasia

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.akenasia.databinding.ReglesGeneralesBinding
import kotlinx.android.synthetic.main.database.*
import kotlinx.android.synthetic.main.regles_generales.*


class ReglesGenerales : Fragment(){

    private var _binding: ReglesGeneralesBinding? = null
    private lateinit var pos: Position

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var thiscontext: Context? = null
    private lateinit var gamemode: String
    private lateinit var placeID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pos = Position(this.requireActivity())
        if (container != null) {
            thiscontext = container.getContext()
        }
        _binding = ReglesGeneralesBinding.inflate(inflater, container, false)
        gamemode= this.activity?.intent?.getStringExtra("mode").toString()
        placeID= this.activity?.intent?.getIntExtra("id",0).toString()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        begin_game.setOnClickListener {
            var bundle= Bundle()
            bundle.putInt("id",placeID.toInt())
            if(this.activity?.intent?.getStringExtra("mode").toString().equals("chronometre")){
                findNavController().navigate(R.id.action_Regles_to_Chrono,bundle)
            }
            else{
                findNavController().navigate(R.id.action_Regles_to_CL,bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}