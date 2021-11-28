package com.example.akenasia.openworld

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.akenasia.R
import com.example.akenasia.database.DatabaseHandler
import com.example.akenasia.database.Item
import com.example.akenasia.database.Place
import kotlinx.android.synthetic.main.poi_dialog.view.*


class PoiDialog : DialogFragment() {
    private lateinit var name : String
    private lateinit var latlong : String
    private lateinit var dbHandler: DatabaseHandler

    //On va récupérer le nom et la position du POI à partir de la classe Chronometre

    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, saveInstanceState : Bundle?) :
            View? {
        dbHandler = DatabaseHandler(context!!)
        var rootView: View = inflater.inflate(R.layout.poi_dialog, container, false)
        rootView.PoiName.text = name
        rootView.PoiLatLong.text = latlong
        rootView.PoiOk.setOnClickListener() {
            //Mettre l'action à réaliser ici
            val emp: List<Item> = dbHandler.viewItem()
            val id = emp.size
            dbHandler.addItem(Item(id, "Dialogue", "test PoiDialog"))
            //add item
            dismiss()

        }
        rootView.PoiCancel.setOnClickListener() {
            //Pour fermer le dialogue
            dismiss()
        }

        rootView.goBag.setOnClickListener{
            val intent = Intent(context, Bag::class.java)
            activity?.startActivity(intent)
        }

        dbHandler

        return rootView
    }

    fun setName (message : String) {
        name = message
    }
    fun setLatLong (message : String) {
        latlong = message
    }




}
