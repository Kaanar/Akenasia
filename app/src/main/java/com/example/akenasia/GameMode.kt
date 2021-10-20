package com.example.akenasia

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.akenasia.databinding.DatabaseBinding
import com.example.akenasia.databinding.GameModeBinding
import kotlinx.android.synthetic.main.database.*

class GameMode : Fragment(){
    private var _binding: GameModeBinding? = null
    private lateinit var pos: Position

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var thiscontext: Context? = null
    private lateinit var dbHandler : DatabaseHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pos = Position(this.requireActivity())
        if (container != null) {
            thiscontext = container.getContext()
            dbHandler = DatabaseHandler(thiscontext!!)
        }
        _binding = GameModeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id= this.arguments?.getInt("id")
        //On récupère les infos de la place
        val place = dbHandler.getPlace(id!!)
        val tv = view.findViewById<TextView>(R.id.GameModeTitleTV)
        tv.text = "Mode de jeu: Go to "+place!!.placeName

        //Si le joueur clique sur chronomètre, la partie se lance selon son choix
        binding.GameModeChronoBT.setOnClickListener {

            //On envoie dans l'activité de jeu le mode choisi + l'id de la place//
            val intent = Intent(context, Game::class.java).apply {
                putExtra("mode","chronometre")
                putExtra("id",id)
            }
            activity?.startActivity(intent)
        }

        //Si le joueur clique sur coups limités, la partie se lance selon son choix
        binding.GameModeCountBT.setOnClickListener {
            val intent = Intent(context, Game::class.java).apply {
                putExtra("mode","coups")
                putExtra("id",id)
            }
            activity?.startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}