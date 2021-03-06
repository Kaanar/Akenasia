package com.example.akenasia.openworld

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.akenasia.handler.ItemHandler
import com.example.akenasia.handler.PersonnageHandler
import com.example.akenasia.R
import com.example.akenasia.database.PersonnageTable
import kotlinx.android.synthetic.main.uplevel_dialog.*
import kotlinx.android.synthetic.main.uplevel_dialog.view.*

class UpLevelDialog : DialogFragment() {

    private lateinit var title : String
    private lateinit var info : String
    private var thiscontext: Context? = null
    private lateinit var personnageHandler : PersonnageHandler
    private lateinit var personnageTable : PersonnageTable
    private var pv_joueur : Double = 0.0
    private var att_joueur : Double = 0.0
    private var def_joueur : Double = 0.0
    private lateinit var itemHandler : ItemHandler


    /*
    On passe le titre et les infos présents de la classe Historique dans le dialog
    Le dialoque affiche le titre (n° de la position) et les infos (latitude, longitude)
    en fonction du marqueur sur lequel on clique sur la map de l'historique.
     */


    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View? {
        thiscontext = this.activity
        personnageHandler = PersonnageHandler(thiscontext!!)
        itemHandler = ItemHandler(thiscontext!!)
        personnageTable = personnageHandler.get(1)
        pv_joueur = personnageTable.persoHp
        att_joueur = personnageTable.persoAtt
        def_joueur = personnageTable.persoDef
        val rootView: View = inflater.inflate(R.layout.uplevel_dialog, container, false)


        rootView.btnUpLevelHp.setOnClickListener() {

            personnageHandler.UpHP(1,3.0)
            personnageHandler.upLevel(1)
            txtUpLevel2.visibility = View.VISIBLE
            Toast.makeText(context,"+3 HP",Toast.LENGTH_SHORT).show()
            dismiss()
        }

        rootView.btnUpLevelAttaque.setOnClickListener() {

            personnageHandler.UpATT(1,5.0)
            personnageHandler.upLevel(1)
            txtUpLevel2.visibility = View.VISIBLE
            Toast.makeText(context,"+5 ATT",Toast.LENGTH_SHORT).show()
            dismiss()
        }

        rootView.btnUpLevelDefense.setOnClickListener() {

            personnageHandler.UpDEF(1,5.0)
            personnageHandler.upLevel(1)
            txtUpLevel2.visibility = View.VISIBLE
            Toast.makeText(context,"+5 DEF",Toast.LENGTH_SHORT).show()
            dismiss()
        }

        return rootView
    }
}