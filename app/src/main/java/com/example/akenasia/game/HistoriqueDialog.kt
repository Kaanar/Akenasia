package com.example.akenasia.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.akenasia.R
import kotlinx.android.synthetic.main.historique_dialog.view.*


class HistoriqueDialog : DialogFragment () {
    private lateinit var title : String
    private lateinit var info : String

    /*
    On passe le titre et les infos présents de la classe Historique dans le dialog
    Le dialoque affiche le titre (n° de la position) et les infos (latitude, longitude)
    en fonction du marqueur sur lequel on clique sur la map de l'historique.
     */

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View? {
        val rootView: View = inflater.inflate(R.layout.historique_dialog, container, false)
        rootView.HistoriqueTitle.text = title
        rootView.HistoriqueInfo.text = info
        rootView.BtnOk.setOnClickListener() {
            //On ferme le dialogue quand on clique sur le bouton
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
