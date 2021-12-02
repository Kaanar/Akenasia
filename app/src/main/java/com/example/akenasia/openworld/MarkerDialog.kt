package com.example.akenasia.openworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.akenasia.R
import kotlinx.android.synthetic.main.chronometre.*
import kotlinx.android.synthetic.main.marker_dialog.*
import kotlinx.android.synthetic.main.marker_dialog.view.*
import kotlin.properties.Delegates

class MarkerDialog : DialogFragment () {
    private lateinit var title : String
    private lateinit var info : String
    private var cbtval by Delegates.notNull<Int>()

    /*
    On passe le titre et les infos présents de la classe Historique dans le dialog
    Le dialoque affiche le titre (n° de la position) et les infos (latitude, longitude)
    en fonction du marqueur sur lequel on clique sur la map de l'historique.
     */

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View? {
        var rootView: View = inflater.inflate(R.layout.marker_dialog, container, false)
        rootView.MarkerTitle.text = title
        rootView.MarkerInfo.text = info
        rootView.BtnAttaque.setOnClickListener() {
            //On lance le combat
            BtnAttaque.setVisibility(View.INVISIBLE)
            BtnFuite.setVisibility(View.INVISIBLE)
            combatText.setVisibility(View.VISIBLE)

            cbtval = (0..1).random()

            if(cbtval == 0){
                defaiteText.setVisibility(View.VISIBLE)
                dismiss()
            }
            if(cbtval == 1)
            {
                victoireText.setVisibility(View.VISIBLE)
                dismiss()
            }
        }
        rootView.BtnFuite.setOnClickListener() {
            //On ferme le dialogue quand on clique sur le bouton (on ne récupère pas d'objet)
            dismiss()
        }
        return rootView
    }

    fun setTitle (message : String) {
        title = message
    }
    fun setInfo (message : String) {
        info = message
    }
}
